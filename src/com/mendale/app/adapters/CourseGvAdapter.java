package com.mendale.app.adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.mendale.app.R;
import com.mendale.app.pojo.CourseChildPojo;

/**
 * 教程
 * 
 * @author Administrator
 * 
 */
public class CourseGvAdapter extends BaseAdapter {

	private Context context;
	private List<CourseChildPojo> courseList;

	public CourseGvAdapter(Context context,List<CourseChildPojo> courseList) {
		this.context = context;
		this.courseList = courseList;
	}

	@Override
	public int getCount() {
		return courseList.size();
	}

	@Override
	public Object getItem(int position) {
		return courseList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.gridview_course, null);
			//设置宽度
			convertView.setLayoutParams(
					new GridView.LayoutParams((int) (parent.getWidth() / 4),  LayoutParams.WRAP_CONTENT));
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_course_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			convertView.setLayoutParams(
					new GridView.LayoutParams((int) (parent.getWidth() / 4), LayoutParams.WRAP_CONTENT));
		}
		//
		holder.name.setText(courseList.get(position).getName());
		return convertView;
	}

	class ViewHolder {
		TextView name;
	}

}
