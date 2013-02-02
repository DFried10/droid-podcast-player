package edu.android.podcast_listener;

import java.util.ArrayList;

import edu.android.podcast_listener.rss.Item;
import edu.android.podcast_listener.rss.Items;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemsAdapter extends ArrayAdapter<Item> {
	
	private ArrayList<Item> items;
    private Context context;

    public ItemsAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_row_item, null);
        }

        Item item = items.get(position);
        if (item!= null) {
            // My layout has only one TextView
            TextView itemView = (TextView) view.findViewById(R.id.txtTitle);
            if (itemView != null) {
                // do whatever you want with your string and long
                itemView.setText(String.format("%s %d", item.getTitle(), item.getDescription()));
            }
         }

        return view;
    }
}