package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.pojo.RecordPojo;
import com.mendale.app.utils.imageUtils.RoundImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 记录xiangListView
 * 
 * @author Administrator
 * 
 */
public class RecordLvAdapter extends BaseAdapter {

	private Context context;
	private List<RecordPojo> recordList;

	public RecordLvAdapter(Context context, List<RecordPojo> recordList) {
		this.context = context;
		this.recordList = recordList;
	}

	@Override
	public int getCount() {
		return recordList.size();
	}

	@Override
	public Object getItem(int position) {
		return recordList.get(position);
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
			convertView = View.inflate(context, R.layout.listview_item_record, null);
			holder.name = (TextView) convertView.findViewById(R.id.tv_record_username);
			holder.headImage = (RoundImageView) convertView.findViewById(R.id.iv_record_head_image);
			holder.content = (TextView) convertView.findViewById(R.id.tv_record_des);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		//
		holder.name.setText(recordList.get(position).getUsername());
		holder.content.setText(recordList.get(position).getDesc());
		// ImageLoader imageLoader = ImageLoader.getInstance();
		// imageLoader.displayImage(recordList.get(position).getFace_pic(),
		// holder.headImage, options);
		// BitmapUtils bitMapUtils=new BitmapUtils(context);
		//
		// bitMapUtils.display(holder.headImage,
		// recordList.get(position).getFace_pic());
		// bitMapUtils.display(holder.contentImage,
		// recordList.get(position).getHost_pic());
		return convertView;
	}

	class ViewHolder {

		TextView name;
		RoundImageView headImage;
		TextView content;
	}
}
