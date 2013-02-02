package edu.android.podcast_listener;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.android.podcast_listener.rss.Channel;
import edu.android.podcast_listener.rss.Items;
import edu.android.podcast_listener.rss.RSSHandler;

public class FindCastsResultsActivity extends Activity {
	private static String ERROR_TAG = "ERROR";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_casts_results);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		String rssUrl = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
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
			// TODO Auto-generated method stub
		try {			
//			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
//			SAXParser parser = saxFactory.newSAXParser();
//			XMLReader xmlReader = parser.getXMLReader();
			
			URL url = new URL(urls[0]);
			InputSource input = new InputSource(url.openStream());
			
			SyndFeedInput syndPut = new SyndFeedInput();
			SyndFeed feed = syndPut.build(new XmlReader(url));
			List entires = feed.getEntries();
			
//			RSSHandler rssHandler = new RSSHandler();
//			xmlReader.setContentHandler(rssHandler);
//			xmlReader.parse(input);
			
//			Channel channelInfo = rssHandler.getParsedChannelData();
			
			ListView listView = (ListView) findViewById(R.id.podcastsList);
//			ItemsAdapter items = new ItemsAdapter(getApplicationContext(), R.id.txtTitle, channelInfo.getItems());
			ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_find_casts_results, R.id.txtTitle);
			listView.setAdapter(adapter);
			
			return null;
		} catch (IOException e) {
			Log.e(ERROR_TAG, "Error in the Input parsing: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		}
			return null;
		}
		
	}

}
