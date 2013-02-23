package edu.android.podcast_listener.util;

public enum PodcastSQL {
	CREATE_PODCAST_TABLE("CREATE TABLE " + PodcastConstants.TABLE +
			" ( " + PodcastConstants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			PodcastConstants.NAME + " TEXT NOT NULL, " +
			PodcastConstants.URL + " TEXT NOT NULL, " +
			PodcastConstants.IMAGE + " TEXT, " +
			PodcastConstants.CATEGORY + " TEXT, " +
			PodcastConstants.SUBSCRIBED + " INTEGER)"),
	CREATE_CATEGORY_TABLE("CREATE TABLE " + PodcastConstants.CAT_TABLE + 
			" ( " + PodcastConstants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			PodcastConstants.NAME + " TEXT NOT NULL) "),
	INSERT_CAT_ENTERTAINMENT("INSERT INTO " + PodcastConstants.CAT_TABLE + " (" + PodcastConstants.NAME + ") VALUES ('Entertainment');"),
	INSERT_CAT_NEWS("INSERT INTO " + PodcastConstants.CAT_TABLE + " (" + PodcastConstants.NAME + ") VALUES ('News');"),
	INSERT_CAT_COMEDY("INSERT INTO " + PodcastConstants.CAT_TABLE + " (" + PodcastConstants.NAME + ") VALUES ('Comedy');");
	
	
	private final String text;
	private PodcastSQL(final String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
}
