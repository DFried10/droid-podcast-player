package edu.android.podcast_listener;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import edu.android.podcast_listener.rss.Channel;
import edu.android.podcast_listener.rss.Item;
import edu.android.podcast_listener.rss.Items;
import edu.android.podcast_listener.util.PodcastConstants;

public class FindCastsResultsActivity extends Activity {
	ListView listView;
	String image;
	ProgressDialog progressDialog;
	String channelTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_casts_results);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		String rssUrl = intent.getStringExtra(PodcastConstants.EXTRA_MESSAGE);
		listView = (ListView) findViewById(R.id.podcastsList);
		progressDialog = new ProgressDialog(this);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
				Item listItem = (Item) listView.getItemAtPosition(position);
				intent.putExtra(PodcastConstants.EXTRA_MESSAGE, listItem.getLink());
				intent.putExtra(PodcastConstants.IMAGE_MESSAGE, image);
				intent.putExtra(PodcastConstants.TITLE_MESSAGE, channelTitle);
				intent.putExtra(PodcastConstants.EPISODE_MESSAGE, listItem.getTitle());
				intent.putExtra(PodcastConstants.EPISODE_DESC, listItem.getDescription());
				startActivity(intent);
			}
					
		});
		
		new RSSAsyncActivity().execute(rssUrl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_find_casts_results, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class RSSAsyncActivity extends AsyncTask<String, Void, Channel> {
		
		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(FindCastsResultsActivity.this,"","Just a second, getting your podcasts...", true,false);
			super.onPreExecute();
		}
		
		@Override
		protected Channel doInBackground(String... urls) {
			try {				
				URL url = new URL(urls[0]);
				Log.d(PodcastConstants.DEBUG_TAG, "Entered the XML Parsing section: " + url.getPath());
				SyndFeedInput syndPut = new SyndFeedInput();
				XmlReader xmlReader = new XmlReader(url);
				SyndFeed feed = syndPut.build(xmlReader);
				List entries = feed.getEntries();
				String imgUrl = feed.getImage().getUrl();
				String channelTitle = feed.getTitle();
				
				Iterator itr = entries.iterator();
				Channel channel = new Channel();
				Items items = new Items();
				while (itr.hasNext()) {
					SyndEntry entry = (SyndEntry) itr.next();
					String title = entry.getTitle();
					String description = entry.getDescription().getValue();
					String link = entry.getLink();
					List enclosures = entry.getEnclosures();
					Date pubDate = entry.getPublishedDate();
					Item item = new Item();
					item.setTitle(title);
					item.setDescription(description);
					item.setLink(((SyndEnclosure)enclosures.get(0)).getUrl());
					item.setSize(((SyndEnclosure)enclosures.get(0)).getLength());
					items.add(item);
				}
				channel.setItems(items);
				channel.setImage(imgUrl);
				channel.setTitle(channelTitle);
				
				Log.d(PodcastConstants.DEBUG_TAG, "Completed parsing RSS feed");
				
				return channel;
			} catch (IOException e) {
				Log.e(PodcastConstants.ERROR_TAG, "Error in the Input parsing: " + e.getMessage());
			} catch (IllegalArgumentException e) {
				Log.e(PodcastConstants.ERROR_TAG, "An error occured in the arguments: " + e.getMessage());
			} catch (FeedException e) {
				Log.e(PodcastConstants.ERROR_TAG, "An error occured in the feed: " + e.getMessage());
			}
			return null;
		}
		
		protected void onPostExecute(Channel channel) {
			try {
			ItemsAdapter adapter = new ItemsAdapter(getApplicationContext(), R.layout.listview_row_item, channel.getItems());
			listView.setAdapter(adapter);
			image = channel.getImage();
			channelTitle = channel.getTitle();
			progressDialog.dismiss();
			} catch (NullPointerException e) {
				Log.wtf(PodcastConstants.WTF_TAG, "Something bad happened while parsing the XML, so we got here");
			}
		}
		
	}

}
