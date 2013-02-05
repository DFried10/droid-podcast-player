package edu.android.podcast_listener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import edu.android.podcast_listener.util.PodcastConstants;

public class FindCastsActivity extends Activity {
	private EditText urlInput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_casts);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		urlInput = (EditText) findViewById(R.id.editText1);
		urlInput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (urlInput.getText() == null || urlInput.getText().equals(""))
					urlInput.append("http://");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_find_casts, menu);
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
	
	public void toResults(View view) {
		Intent intent = new Intent(this, FindCastsResultsActivity.class);
		String podcastUrl = urlInput.getText().toString();
		intent.putExtra(PodcastConstants.EXTRA_MESSAGE, podcastUrl);
		startActivity(intent);
	}

}
