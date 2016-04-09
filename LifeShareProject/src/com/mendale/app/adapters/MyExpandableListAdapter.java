package com.mendale.app.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.constants.Datas;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
	 * A simple adapter which maintains an ArrayList of photo resource Ids. Each
	 * photo is displayed as an image. This adapter supports clearing the list
	 * of photos and adding a new photo.
	 * 
	 */
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		
		private LayoutInflater mInflater;
		private Context context;
		private FrameLayout indicatorGroup;
		public MyExpandableListAdapter(Context context){
			this.context=context;
			mInflater=LayoutInflater.from(context);
			// copy group view to indicator Group
			mInflater.inflate(R.layout.item_expandable_listview, indicatorGroup,true);
			
		}
		
		public Object getChild(int groupPosition, int childPosition) {
			return Datas.courseList.get(groupPosition).getChild().get(childPosition).getName();
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return Datas.courseList.get(groupPosition).getChild().size();
		}

		public TextView getGenericView() {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 64);

			TextView textView = new TextView(context);
			textView.setLayoutParams(lp);
			// Center the text vertically
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			// Set the text starting position
			textView.setPadding(36, 0, 0, 0);
			textView.setTextSize(14);
			textView.setTextColor(Color.BLACK);
			return textView;
		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			return textView;
		}

		public Object getGroup(int groupPosition) {
			return Datas.courseList.get(groupPosition).getName();
		}

		public int getGroupCount() {
			return Datas.courseList.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		/**
		 * create group view and bind data to view
		 */
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			View v;
			if (convertView == null) {
				v = mInflater.inflate(R.layout.item_expandable_listview, null);
			} else {
				v = convertView;
			}
			TextView textView = (TextView) v.findViewById(R.id.textView);

			textView.setText(getGroup(groupPosition).toString());
			return v;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}

	}
