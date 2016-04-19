package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.pojo.ChartsPoJo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 排行榜listview
 * 
 * @author zhangxue
 * @date 2016年4月17日
 */
public class ChartsLvAdapter extends BaseAdapter {

	private Context context;
	private List<ChartsPoJo> mDatas;

	public ChartsLvAdapter(Context context, List<ChartsPoJo> mDatas) {
		this.mDatas = mDatas;
		this.context = context;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.listview_charts, null);
			holder.pos = (TextView) convertView.findViewById(R.id.tv_listview_charts_pos);
			holder.title = (TextView) convertView.findViewById(R.id.tv_listview_charts_title);
			holder.name = (TextView) convertView.findViewById(R.id.tv_listview_charts_name);
			holder.view = (TextView) convertView.findViewById(R.id.tv_listview_charts_view);
			holder.pic = (ImageView) convertView.findViewById(R.id.iv_listview_charts_pic);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		//
		holder.pos.setText("" + (position + 1));
//		holder.name.setText();
		return convertView;
	}

	class ViewHolder {

		TextView pos;
		TextView title;
		TextView name;
		TextView view;
		ImageView pic;
	}
}
