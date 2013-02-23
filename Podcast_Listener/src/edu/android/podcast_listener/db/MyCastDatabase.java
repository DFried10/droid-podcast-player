package edu.android.podcast_listener.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.android.podcast_listener.util.PodcastConstants;
import edu.android.podcast_listener.util.PodcastSQL;

public class MyCastDatabase extends SQLiteOpenHelper {
	final static int DB_VERSION = 5;
	final static String DB_NAME = "MyCast";
	
	Context context;
	
	public MyCastDatabase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(PodcastSQL.CREATE_PODCAST_TABLE.toString());
		db.execSQL(PodcastSQL.CREATE_CATEGORY_TABLE.toString());
//		db.execSQL("INSERT INTO PODCAST (NAME, URL, IMAGE, CATEGORY, SUBSCRIBED) VALUES ('Comedy Bang Bang', 'http://feeds.feedburner.com/comedydeathrayradio', null, 'Entertainment', 1)");
//		db.execSQL("INSERT INTO PODCAST (NAME, URL, IMAGE, CATEGORY, SUBSCRIBED) VALUES ('This American Life', 'http://feeds.thisamericanlife.org/talpodcast', null, 'News', 1)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int orig_ver, int new_ver) {
		if (orig_ver != new_ver) 
			db.execSQL("DROP TABLE IF EXISTS " + PodcastConstants.TABLE);
	}

}
