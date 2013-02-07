package edu.android.podcast_listener.rss;

import java.io.Serializable;

public class Item implements Serializable {
	
	private static final long serialVersionUID = 5391012242101623519L;
	private String title;
	private String description;
	private String pubDate;
	private String link;
	private long size;
	
	public Item() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public String getFormattedDescription() {
		String formattedDesc = null;
		if (description.length() > 195) {
			formattedDesc = description.substring(0, 196);
			formattedDesc += "...";
		} else {
			formattedDesc = description;
		}
		return formattedDesc;
	}
}
