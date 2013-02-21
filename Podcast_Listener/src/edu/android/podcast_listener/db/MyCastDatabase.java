package edu.android.podcast_listener.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyCastDatabase extends SQLiteOpenHelper {
	final static int DB_VERSION = 4;
	final static String DB_NAME = "MyCast";
	public final static String TABLE = "PODCAST";
	public final static String ID = "ID";
	public final static String NAME = "NAME";
	public final static String URL = "URL";
	public final static String IMAGE = "IMAGE";
	public final static String CATEGORY = "CATEGORY";
	public final static String SUBSCRIBED = "SUBSCRIBED";
	
	Context context;
	
	public MyCastDatabase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE +
				" ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				NAME + " TEXT NOT NULL, " +
				URL + " TEXT NOT NULL, " +
				IMAGE + " TEXT, " +
				CATEGORY + " TEXT, " +
				SUBSCRIBED + " INTEGER)");
//		db.execSQL("INSERT INTO PODCAST (NAME, URL, IMAGE, CATEGORY, SUBSCRIBED) VALUES ('Comedy Bang Bang', 'http://feeds.feedburner.com/comedydeathrayradio', null, 'Entertainment', 1)");
//		db.execSQL("INSERT INTO PODCAST (NAME, URL, IMAGE, CATEGORY, SUBSCRIBED) VALUES ('This American Life', 'http://feeds.thisamericanlife.org/talpodcast', null, 'News', 1)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int orig_ver, int new_ver) {
		if (orig_ver != new_ver) 
			db.execSQL("DROP TABLE IF EXISTS " + TABLE);
	}

}
