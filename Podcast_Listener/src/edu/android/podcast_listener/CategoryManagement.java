package edu.android.podcast_listener;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import edu.android.podcast_listener.adapters.CategoryAdapter;
import edu.android.podcast_listener.db.Category;
import edu.android.podcast_listener.db.CategoryDAO;

public class CategoryManagement extends Activity {
	CategoryDAO categoryDb;
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_management);
		categoryDb = new CategoryDAO(this);
		categoryDb.open();
		List<Category> cats = categoryDb.getAllCategories();
		listView = (ListView) findViewById(R.id.current_categories);
		listView.setAdapter(new CategoryAdapter(getApplicationContext(), R.layout.category_list_item, cats));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_category_management, menu);
		return true;
	}

}
