package edu.android.podcast_listener.db;

import java.util.ArrayList;
import java.util.List;

import edu.android.podcast_listener.util.PodcastConstants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PodcastDAO {
	
	private SQLiteDatabase database;
	private MyCastDatabase myCastDatabase;
	private String[] allColumns = {PodcastConstants.ID, PodcastConstants.NAME, PodcastConstants.URL,
			PodcastConstants.IMAGE, PodcastConstants.CATEGORY};
	
	public PodcastDAO(Context context) {
		myCastDatabase = new MyCastDatabase(context);
	}
	
	public void open() throws SQLException {
		database = myCastDatabase.getWritableDatabase();
	}
	
	public void close() {
		database.close();
	}
	
	private Podcast createPodcast(String url, String name, String img, String category) {
		ContentValues values = new ContentValues();
		values.put(PodcastConstants.NAME, name);
		values.put(PodcastConstants.URL, url);
		values.put(PodcastConstants.IMAGE, img);
		values.put(PodcastConstants.CATEGORY, category);
		values.put(PodcastConstants.SUBSCRIBED, 1);
		
		long insertId = database.insert(PodcastConstants.TABLE, null, values);
		Cursor cursor = database.query(PodcastConstants.TABLE, allColumns, PodcastConstants.ID + " = " + insertId, null, 
				null, null, null);
		cursor.moveToFirst();
		Podcast podcast = cursorToPodcast(cursor);
		cursor.close();
		return podcast;
	}
	
	private void deletePodcast(String rssUrl) {
		database.delete(PodcastConstants.TABLE, PodcastConstants.URL + "=?", new String[] {rssUrl});
	}
	
	public List<Podcast> getAllPodcasts() {
		List<Podcast> podcasts = new ArrayList<Podcast>();
		Cursor cursor = database.query(PodcastConstants.TABLE, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Podcast podcast = cursorToPodcast(cursor);
			podcasts.add(podcast);
			cursor.moveToNext();
		}
		cursor.close();
		return podcasts;
	}

	public List<String> getCategories() {
		List<String> categories = new ArrayList<String>();
		Cursor cursor = database.rawQuery("SELECT DISTINCT CATEGORY FROM " + PodcastConstants.TABLE, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String category = cursor.getString(0);
			categories.add(category);
			cursor.moveToNext();
		}
		cursor.close();
		return categories;
	}
	
	public String getPodcastUrl(String name) {
		String url;
		Cursor cursor = database.rawQuery("SELECT URL FROM " + PodcastConstants.TABLE + " WHERE NAME = '" + name + "'", null);
		cursor.moveToFirst();
		url = cursor.getString(0);
		cursor.close();
		return url;
	}
	
	public boolean isSubscribedToPodcast(String rssUrl) {
		int num = (int) DatabaseUtils.queryNumEntries(database, PodcastConstants.TABLE, PodcastConstants.URL + "=?", new String[] {rssUrl});
		if (num == 0) {
			return false;
		}
		return true;
	}
	
	public void subscribeToPodcast(String name, String rssUrl, String image, String category) {
		createPodcast(rssUrl, name, image, category);
	}
	
	public void unsubscribeFromPodcast(String rssUrl) {
		deletePodcast(rssUrl);
	}
	
	public Podcast cursorToPodcast(Cursor cursor) {
		Podcast podcast = new Podcast();
		podcast.setId(cursor.getLong(0));
		podcast.setName(cursor.getString(1));
		podcast.setUrl(cursor.getString(2));
		podcast.setImg(cursor.getString(3));
		podcast.setCategory(cursor.getString(4));
		return podcast;
	}
}
