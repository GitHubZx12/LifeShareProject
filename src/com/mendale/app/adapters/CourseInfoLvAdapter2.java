package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.constants.DataURL;
import com.mendale.app.pojo.CourseListPojo;
import com.mendale.app.pojo.Record;
import com.mendale.app.pojo.RecordItemBean;
import com.mendale.app.ui.home.ShowDetailsActivity;
import com.mendale.app.ui.record.RecordDetailsActivity;
import com.mendale.app.utils.imageUtils.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.utils.Log;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 课程详情xiangListView
 * 
 * @author Administrator
 * 
 */
public class CourseInfoLvAdapter2 extends BaseAdapter {

	private Context context;
	private List<Record> recordList;
	private DisplayImageOptions options;

	public CourseInfoLvAdapter2(Context context, List<Record> recordList, DisplayImageOptions options) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.listview_item_course_info, null);
			holder.name = (TextView) convertView.findViewById(R.id.tv_course_info_username);
			holder.headImage = (RoundImageView) convertView.findViewById(R.id.iv_course_info_head_image);
			holder.contentImage = (ImageView) convertView.findViewById(R.id.iv_course_info_image);
			holder.content = (TextView) convertView.findViewById(R.id.tv_course_info_content);
			holder.recordAll = (TextView) convertView.findViewById(R.id.tv_course_info_all);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		//
		holder.name.setText(recordList.get(position).getAuthor().getUsername());
		holder.content.setText(recordList.get(position).getContent());
//		holder.recordAll.setText(recordList.get(position).getView() + "人气/" + recordList.get(position).getLaud() + "赞/"
//				+ recordList.get(position).getCollect() + "收藏/" + recordList.get(position).getComment() + "评论");
		ImageLoader imageLoader = ImageLoader.getInstance();
//		imageLoader.displayImage(recordList.get(position).getFace_pic(), holder.headImage, options);
//		imageLoader.displayImage(recordList.get(position).getImage(), holder.contentImage, options);
		// BitmapUtils bitMapUtils=new BitmapUtils(context);
		//
		// bitMapUtils.display(holder.headImage,
		// recordList.get(position).getFace_pic());
		// bitMapUtils.display(holder.contentImage,
		// recordList.get(position).getHost_pic());
		
		holder.contentImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {//进入详情页
				Intent intent = new Intent(context, RecordDetailsActivity.class);
				RecordItemBean recordItem=new RecordItemBean();
				recordItem.setSubject(recordList.get(position).getTitle());
				recordItem.setContent(recordList.get(position).getContent());
				recordItem.setUser_name(recordList.get(position).getAuthor().getUsername());
				Bundle bundle=new Bundle();
				bundle.putSerializable("recordItem", recordItem);
				intent.putExtras(bundle);
				intent.putExtra("objectId", value)
				context.startActivity(intent);
			}
		});
		
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
