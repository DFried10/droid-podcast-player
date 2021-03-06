package edu.android.podcast_listener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;
import edu.android.podcast_listener.util.PodcastConstants;

public class PlayerActivity extends Activity implements MediaPlayerControl, OnPreparedListener {

	private String channelTitle, episodeTitle, episodeDesc, imageUrl, audioUrl;
	private MediaPlayer mediaPlayer;
	private MediaController mediaController;
	private Handler handler = new Handler();
		    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		configureTextDisplay(intent);
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnPreparedListener(this);
		mediaController = new MediaController(this);
		
		new AsyncImage().execute(imageUrl);
		try {
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(audioUrl);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IOException e) {
			Log.e(PodcastConstants.ERROR_TAG, "Issue opening url " + audioUrl);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_player, menu);
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mediaController.show();
		return false;
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
	
	private void configureTextDisplay(Intent intent) {
		audioUrl = intent.getStringExtra(PodcastConstants.EXTRA_MESSAGE);
		imageUrl = intent.getStringExtra(PodcastConstants.IMAGE_MESSAGE);
		channelTitle = intent.getStringExtra(PodcastConstants.TITLE_MESSAGE);
		episodeTitle = intent.getStringExtra(PodcastConstants.EPISODE_MESSAGE);
		episodeDesc = intent.getStringExtra(PodcastConstants.EPISODE_DESC);
		
		TextView channel = (TextView) findViewById(R.id.channelTitle);
		TextView episode = (TextView) findViewById(R.id.episodeTitle);
		TextView desc = (TextView) findViewById(R.id.episodeDesc);
		channel.setText(channelTitle);
		episode.setText(episodeTitle);
		desc.setText(episodeDesc);
	}
	
	public void onStop() {
		super.onStop();
		mediaPlayer.stop();
		mediaPlayer.release();		
	}

	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {
		return true;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return mediaPlayer.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return mediaPlayer.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public void pause() {
		mediaPlayer.pause();
	}

	@Override
	public void seekTo(int time) {
		mediaPlayer.seekTo(time);
	}

	@Override
	public void start() {
		mediaPlayer.start();
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		Log.d("Player", "onPrepared");
		mediaController.setMediaPlayer(this);
		mediaController.setAnchorView(findViewById(R.id.main_player_view));
		
		handler.post(new Runnable() {
			public void run() {
				mediaController.setEnabled(true);
				mediaController.show();
			}
		});
	}

	class AsyncImage extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			try {				  
			  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(params[0]).getContent());
			  return bitmap;				  
			} catch (MalformedURLException e) {
				Log.e(PodcastConstants.ERROR_TAG, "Issue with Image URL [" + imageUrl + "] : " + e.getMessage());
			} catch (IOException e) {
			  	Log.e(PodcastConstants.ERROR_TAG, "Issue decoding URL input stream [" + imageUrl +"] : " + e.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			ImageView i = (ImageView)findViewById(R.id.podcastImage);
			i.setImageBitmap(bitmap); 
		}
		
	}
}
