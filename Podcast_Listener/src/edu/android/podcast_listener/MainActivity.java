package edu.android.podcast_listener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public static final String EXTRA_MESSAGE = "edu.android.podcast_listener.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void sendMessage(View view) {
//    	Intent intent = new Intent(this, DisplayMessageActivity.class);
//    	EditText editText = (EditText) findViewById(R.id.editText1);
//    	String message = editText.getText().toString();
//    	intent.putExtra(EXTRA_MESSAGE, message);
//    	startActivity(intent);
    }
    
    public void toMyCasts(View view) {
    	// TODO - go to the MyCasts activity
    	Intent intent = new Intent(this, MyCastsActivity.class);
    	startActivity(intent);
    }
    
    public void toFindCasts(View view) {
    	// TODO - go to the FindCasts activity
    }
    
}
