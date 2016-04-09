package com.mendale.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mendale.app.R;
import com.mendale.app.utils.imageUtils.RoundImageView;
import com.mendale.app.vo.RecordItemBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 教程
 * 
 * @author Administrator
 * 
 */
public class RecordLVAdapter extends BaseAdapter {

	private Context context;
	private List<RecordItemBean> recordList;
	private DisplayImageOptions options;

	public RecordLVAdapter(Context context, List<RecordItemBean> recordList,
			DisplayImageOptions options) {
		this.context = context;
		this.recordList = recordList;
		this.options = options;
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
			convertView = View.inflate(context, R.layout.listview_item_record,
					null);
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_record_username);
			holder.headImage = (RoundImageView) convertView
					.findViewById(R.id.iv_record_head_image);
			holder.contentImage = (ImageView) convertView
					.findViewById(R.id.iv_record_image);
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_record_content);
			holder.recordAll = (TextView) convertView
					.findViewById(R.id.tv_record_all);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//
		holder.name.setText(recordList.get(position).getUser_name());
		holder.content.setText(recordList.get(position).getContent());
		holder.recordAll.setText(recordList.get(position).getView() + "人气/"
				+ recordList.get(position).getLaud() + "赞/"
				+ recordList.get(position).getCollect() + "收藏/"
				+ recordList.get(position).getComment() + "评论");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(recordList.get(position).getFace_pic(),
				holder.headImage, options);
		imageLoader.displayImage(recordList.get(position).getHost_pic(),
				holder.contentImage, options);
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
		ImageView contentImage;
		TextView content;
		TextView recordAll;
	}

}
