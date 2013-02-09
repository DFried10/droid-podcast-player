package edu.android.podcast_listener.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyCastDatabase extends SQLiteOpenHelper {
	final static int DB_VERSION = 1;
	final static String DB_NAME = "MyCast";
	Context context;
	
	public MyCastDatabase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE PODCAST " +
				"(PODCAST_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"PODCAST_NAME TEXT NOT NULL, " +
				"PODCAST_URL TEXT NOT NULL, " +
				"PODCAST_IMG TEXT, " +
				"CATEGORY TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int orig_ver, int new_ver) {
		db.execSQL("DROP TABLE IF EXISTS PODCAST");
	}

}
