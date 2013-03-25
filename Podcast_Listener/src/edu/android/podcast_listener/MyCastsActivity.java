package edu.android.podcast_listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import edu.android.podcast_listener.db.Podcast;
import edu.android.podcast_listener.db.PodcastDAO;
import edu.android.podcast_listener.util.PodcastConstants;

@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class MyCastsActivity extends ExpandableListActivity {
	PodcastDAO podcastDb;
	ExpandableListView expList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_casts);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		podcastDb = new PodcastDAO(this);
		
		setListAdapter(configListAdapter());
		getExpandableListView().setLongClickable(true);
		getExpandableListView().setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				SimpleExpandableListAdapter adapter = (SimpleExpandableListAdapter) parent.getExpandableListAdapter();
				Intent intent = new Intent(getApplicationContext(), FindCastsResultsActivity.class);
				Bundle bundle = new Bundle();
				HashMap map = (HashMap)adapter.getChild(groupPosition, childPosition);
				Podcast pod = (Podcast) map.get("Obj");
				intent.putExtra(PodcastConstants.EXTRA_MESSAGE, pod.getUrl());
				startActivity(intent);
				return true;
			}
		});
		getExpandableListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
				ListView list = null;
				final AdapterView passSelectedItem = av;
				final int positionToPass = pos;
				final Dialog dialog = new Dialog(v.getContext());
				dialog.setContentView(R.layout.category_choice);
				dialog.setTitle("Options");
				dialog.setCancelable(true);
				final List<String> ops = new ArrayList<String>();
				ops.add("Delete");				
				list = (ListView) dialog.findViewById(R.id.category_list);
				list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.category_list_item, ops));
				
				list.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> av, View v, int pos,	long id) {
						Object test = passSelectedItem;
						HashMap p = (HashMap) passSelectedItem.getItemAtPosition(positionToPass);
						Podcast pod = (Podcast) p.get("Obj");
						podcastDb.open();
						podcastDb.unsubscribeFromPodcast(pod.getUrl());						
						podcastDb.close();
						dialog.dismiss();
					}
				});
				
				dialog.show();
				return true;
			}
			
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();		
		// Refresh the list		
		setListAdapter(configListAdapter());
	}
	
	private SimpleExpandableListAdapter configListAdapter() {
		SimpleExpandableListAdapter expAdapter = new SimpleExpandableListAdapter(this,
				createGroupList(),
				R.layout.my_casts_group_row,
				new String[] {PodcastConstants.CATEGORY}, 
				new int[] {R.id.row_name}, 
				createChildList(), 
				R.layout.my_casts_child_row, 
				new String[] {PodcastConstants.NAME}, 
				new int[] {R.id.grp_child});
		return expAdapter;
	}
	
	private List createGroupList() {
		podcastDb.open();
		List results = new ArrayList();
		List<String> groups = podcastDb.getCategories();
		for (String g : groups) {
			HashMap map = new HashMap();
			map.put(PodcastConstants.CATEGORY, g);
			results.add(map);
		}
		podcastDb.close();
		return results;
	}
	
	private List createChildList() {
		List catList = new ArrayList();
		podcastDb.open();
		List<String> groups = podcastDb.getCategories();
		List<Podcast> children = podcastDb.getAllPodcasts();
		
		for (String cat : groups) {
			List selectList = new ArrayList();
			for (Podcast p : children) {
				if (p.getCategory().equals(cat)) {
					HashMap child = new HashMap();
					child.put(PodcastConstants.NAME, p.getName());
					child.put("Obj", p);
					selectList.add(child);
				}
			}
			catList.add(selectList);
		}		
		podcastDb.close();
		return catList;
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
