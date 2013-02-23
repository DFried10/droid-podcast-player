package edu.android.podcast_listener.db;

import java.util.ArrayList;
import java.util.List;

import edu.android.podcast_listener.util.PodcastConstants;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CategoryDAO {

	private SQLiteDatabase database;
	private MyCastDatabase myCastDatabase;
	private String[] allColumns = new String[] {PodcastConstants.ID, PodcastConstants.NAME};
	
	public CategoryDAO(Context context) {
		this.myCastDatabase = new MyCastDatabase(context);
	}
	
	public void open() throws SQLException {
		database = myCastDatabase.getWritableDatabase();
	}
	
	public void close() {
		database.close();
	}
	
	public Category createCategory(String name) {
		ContentValues values = new ContentValues();
		values.put(PodcastConstants.NAME, name);
		
		long insertId = database.insert(PodcastConstants.CAT_TABLE, null, values);
		Cursor cursor = database.query(PodcastConstants.CAT_TABLE, allColumns, PodcastConstants.ID + " = " + insertId, null, 
				null, null, null);
		cursor.moveToFirst();
		Category category = cursorToCategory(cursor);
		cursor.close();
		return category;
	}
	
	public void deleteCategory(long id) {
		database.delete(PodcastConstants.CAT_TABLE, PodcastConstants.ID + "=" + id, null);
	}
	
	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<Category>();
		Cursor cursor = database.query(PodcastConstants.CAT_TABLE, allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Category cat = cursorToCategory(cursor);
			categories.add(cat);
			cursor.moveToNext();
		}
		cursor.close();
		
		return categories;
	}
	
	public List<String> getCategoriesAsString() {
		List<String> catNames = new ArrayList<String>();
		List<Category> cats = getAllCategories();
		for (Category cat : cats) {
			catNames.add(cat.toString());
		}		
		return catNames;
	}
	
	private Category cursorToCategory(Cursor cursor) {
		Category category = new Category();
		category.setId(cursor.getLong(0));
		category.setName(cursor.getString(1));
		return category;
	}
}
