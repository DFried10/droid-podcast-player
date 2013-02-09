package edu.android.podcast_listener.adapters;

import java.util.List;
import java.util.Map;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private static final class ViewHolder {
		TextView textLabel;
	}
	
	private final Map.Entry<Object, List<Object>> entries[];
	private final LayoutInflater inflater;
	
	@SuppressWarnings("unchecked")
	public ExpandableListAdapter(Context context, Map<Object, List<Object>> map) {
		inflater = LayoutInflater.from(context);
		entries = map.entrySet().toArray(new Map.Entry[0]);
	}
	
	@Override
	public Object getChild(int groupPos, int childPos) { 
		final List<Object> childList = entries[groupPos].getValue();
		return childList.get(childPos);
	}
	
	@Override
	public long getChildId(int groupPos, int childPos) {
		return childPos;
	}
	
	@Override
	public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView,
			ViewGroup parent) {
		View resultView = convertView;
		ViewHolder holder;
		
		if (resultView == null) {
			resultView = inflater.inflate(R.layout.test_list_item, null);
			holder = new ViewHolder();
			holder.textLabel = (TextView) resultView.findViewById(android.R.id.title);
		} else {
			holder = (ViewHolder) resultView.getTag();
		}
		return resultView;
	}

	@Override
	public int getChildrenCount(int groupPos) {
		final List<Object> childList = entries[groupPos].getValue();
		final int childCount = childList.size();
		return childCount;
	}

	@Override
	public Object getGroup(int groupPos) {
		return entries[groupPos].getKey();
	}

	@Override
	public int getGroupCount() {
		final int groupCount = entries.length;
		return groupCount;
	}

	@Override
	public long getGroupId(int groupPos) {
		return groupPos;
	}

	@Override
	public View getGroupView(int groupPos, boolean isExpanded, View theConvertView, ViewGroup parent) {
		View resultView = theConvertView;
		ViewHolder holder;
		
		if (resultView == null) {
			resultView = inflater.inflate(R.layout.test_list_item, null);
			holder = new ViewHolder();
			holder.textLabel = (TextView) resultView.findViewById(R.id.title);
			resultView.setTag(holder);
		} else {
			holder = (ViewHolder) resultView.getTag();
		}
		
		final Object item = getGroup(groupPos);
		holder.textLabel.setText(item.toString());
		
		return resultView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}
