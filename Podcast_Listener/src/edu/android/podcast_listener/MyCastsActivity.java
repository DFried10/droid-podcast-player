package edu.android.podcast_listener;

import java.util.List;

import edu.android.podcast_listener.db.MyCastDatabase;

import android.app.ExpandableListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class MyCastsActivity extends ExpandableListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_casts);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		SimpleExpandableListAdapter expAdapter = new SimpleExpandableListAdapter(this,
				createGroupList(),
				R.layout.my_casts_group_row,
				new String[] {"Group Item"}, 
				new int[] {R.id.row_name}, 
				createChildList(), 
				R.layout.my_casts_child_row, 
				new String[] {"Child Item"}, 
				new int[] {R.id.grp_child});
		setListAdapter(expAdapter);
	}
	
	private List createGroupList() {
		MyCastDatabase database = new MyCastDatabase(this);
		SQLiteDatabase sqlDatabase = database.getReadableDatabase();
		Cursor records = sqlDatabase.rawQuery("", null);
		
		return null;
	}
	
	private List createChildList() {
		
		return null;
	}
	
	@Override
	public void onContentChanged() {
		super.onContentChanged();
	}
	
	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_my_casts, menu);
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
