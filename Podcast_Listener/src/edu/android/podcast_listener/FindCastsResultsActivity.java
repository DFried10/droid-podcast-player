package edu.android.podcast_listener;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_casts_results);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		String rssUrl = intent.getStringExtra(PodcastConstants.EXTRA_MESSAGE);
		listView = (ListView) findViewById(R.id.podcastsList);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
				Item listItem = (Item) listView.getItemAtPosition(position);
				String urlOfPodcast = listItem.getLink();
				intent.putExtra(PodcastConstants.EXTRA_MESSAGE, urlOfPodcast);
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
		protected Channel doInBackground(String... urls) {
			try {			
	//			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
	//			SAXParser parser = saxFactory.newSAXParser();
	//			XMLReader xmlReader = parser.getXMLReader();
				URL url = new URL(urls[0]);
	//			InputSource input = new InputSource(url.openStream());
				
				Log.d(PodcastConstants.DEBUG_TAG, "Entered the XML Parsing section: " + url.getPath());
				
				SyndFeedInput syndPut = new SyndFeedInput();
				XmlReader xmlReader = new XmlReader(url);
				SyndFeed feed = syndPut.build(xmlReader);
				List entries = feed.getEntries();
				
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
					item.setLink((String)enclosures.get(0));
					item.setSize(Integer.parseInt((String)enclosures.get(1)));
					items.add(item);
				}
				channel.setItems(items);
				Log.d(PodcastConstants.DEBUG_TAG, "Completed parsing RSS feed");
	//			RSSHandler rssHandler = new RSSHandler();
	//			xmlReader.setContentHandler(rssHandler);
	//			xmlReader.parse(input);			
	//			Channel channelInfo = rssHandler.getParsedChannelData();			
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
	        // TODO: check this.exception 
	        // TODO: do something with the feed
			try {
			ItemsAdapter adapter = new ItemsAdapter(getApplicationContext(), R.layout.listview_row_item, channel.getItems());
			listView.setAdapter(adapter);
			} catch (NullPointerException e) {
				Log.wtf(PodcastConstants.WTF_TAG, "Something bad happened while parsing the XML, so we got here");
			}
		}
		
	}

}
