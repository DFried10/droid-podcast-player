package edu.android.podcast_listener.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.android.podcast_listener.R;
import edu.android.podcast_listener.db.Category;

public class CategoryAdapter extends ArrayAdapter<Category> {

	private List<Category> categories;
    private Context context;
    
    public CategoryAdapter(Context context, int textViewResourceId, List<Category> categories) {
        super(context, textViewResourceId, categories);
        this.context = context;
        this.categories = categories;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_row_item, null);
        }
        
        Category category = categories.get(position);
        if (category != null) {
        	TextView categoryTitle = (TextView) view.findViewById(R.id.txtCategory);
        	if (categoryTitle != null) {
        		categoryTitle.setText(String.format("%s", category.getName()));
        	}
        }
    	return view;
    }
}
