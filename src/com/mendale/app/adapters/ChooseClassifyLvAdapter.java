package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.pojo.CourseChildPojo;
import com.mendale.app.pojo.MainMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseClassifyLvAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context context;
	/** 数据 */
	private List<MainMenu> list;
	private LayoutInflater inflater;

	public ChooseClassifyLvAdapter(Context context, List<MainMenu> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_second_list, null);
			holder.imageViewSecondListIcon = (ImageView) convertView.findViewById(R.id.imageViewSecondListIcon);
			holder.textViewSecondListName = (TextView) convertView.findViewById(R.id.textViewSecondListName);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textViewSecondListName.setText(list.get(position).getName());
//		holder.imageViewSecondListIcon.setBackgroundResource(list.get(position).getBg());
		return convertView;
	}

	class ViewHolder {

		ImageView imageViewSecondListIcon;
		TextView textViewSecondListName;
	}
}
