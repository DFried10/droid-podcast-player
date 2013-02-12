package edu.android.podcast_listener.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PodcastDAO {
	
	private static final String DIV = ", ";
	private SQLiteDatabase database;
	private MyCastDatabase myCastDatabase;
	private String[] allColumns = {MyCastDatabase.ID, MyCastDatabase.NAME, MyCastDatabase.URL,
			MyCastDatabase.IMAGE, MyCastDatabase.CATEGORY};
	
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
		values.put(MyCastDatabase.NAME, name);
		values.put(MyCastDatabase.URL, url);
		values.put(MyCastDatabase.IMAGE, img);
		values.put(MyCastDatabase.CATEGORY, category);
		values.put(MyCastDatabase.SUBSCRIBED, 1);
		
		long insertId = database.insert(MyCastDatabase.TABLE, null, values);
		Cursor cursor = database.query(MyCastDatabase.TABLE, allColumns, MyCastDatabase.ID + " = " + insertId, null, 
				null, null, null);
		cursor.moveToFirst();
		Podcast podcast = cursorToPodcast(cursor);
		cursor.close();
		return podcast;
	}
	
	private void deletePodcast(Podcast podcast) {
		long id = podcast.getId();
		database.delete(MyCastDatabase.TABLE, MyCastDatabase.ID+" = "+id, null);
	}
	
	public List<Podcast> getAllPodcasts() {
		List<Podcast> podcasts = new ArrayList<Podcast>();
		Cursor cursor = database.query(MyCastDatabase.TABLE, allColumns, null, null, null, null, null);
		
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
		Cursor cursor = database.rawQuery("SELECT DISTINCT CATEGORY FROM " + MyCastDatabase.TABLE, null);
		
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
		Cursor cursor = database.rawQuery("SELECT URL FROM " + MyCastDatabase.TABLE + " WHERE NAME = '" + name + "'", null);
		cursor.moveToFirst();
		url = cursor.getString(0);
		cursor.close();
		return url;
	}
	
	public boolean isSubscribedToPodcast(String rssUrl) {
		int num = (int) DatabaseUtils.queryNumEntries(database, MyCastDatabase.TABLE, MyCastDatabase.URL + "=?", new String[] {rssUrl});
		if (num == 0) {
			return false;
		}
		return true;
	}
	
	public void subscribeToPodcast(String name, String rssUrl, String image, String category) {
		createPodcast(rssUrl, name, image, category);
	}
	
	public void unsubscribeFromPodcast(Podcast podcast) {
		deletePodcast(podcast);
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
