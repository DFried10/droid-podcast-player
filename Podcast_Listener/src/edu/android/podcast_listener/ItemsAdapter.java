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
            TextView itemDesc = (TextView) view.findViewById(R.id.textDesc);
            if (itemView != null) {
                itemView.setText(String.format("%s%n", item.getTitle()));
            }
            if (itemDesc != null) {
            	itemDesc.setText(String.format("%s : %dMB", item.getDescription(), item.getSize()/1000000));
            }
         }

        return view;
    }
}
