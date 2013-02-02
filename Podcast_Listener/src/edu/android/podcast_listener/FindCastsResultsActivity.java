package edu.android.podcast_listener;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Intent;
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
		
		try {
			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
			SAXParser parser = saxFactory.newSAXParser();
			XMLReader xmlReader = parser.getXMLReader();
			
			RSSHandler rssHandler = new RSSHandler();
			xmlReader.setContentHandler(rssHandler);
			xmlReader.parse(new InputSource(rssUrl));
			
			Channel channelInfo = rssHandler.getParsedChannelData();
			
			ListView listView = (ListView) findViewById(R.id.podcastsList);
			ItemsAdapter items = new ItemsAdapter(this, R.id.txtTitle, channelInfo.getItems());
			listView.setAdapter(items);
		} catch (ParserConfigurationException e) {
			Log.e(ERROR_TAG, "Error in the parser config: " + e.getMessage());
		} catch (SAXException e) {
			Log.e(ERROR_TAG, "Error in the XML parsing: " + e.getMessage());
		} catch (IOException e) {
			Log.e(ERROR_TAG, "Error in the Input parsing: " + e.getMessage());
		}
		
		
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

}