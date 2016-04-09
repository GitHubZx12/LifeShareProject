package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.vo.HotCourseItemBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.utils.BitmapUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 首页——热门教程——GridView
 * 
 * @author Administrator
 * 
 */
public class HotCourseGVAdapter extends BaseAdapter {

	private List<HotCourseItemBean> mDatas;
	private Context context;
	private DisplayImageOptions options;

	public HotCourseGVAdapter(Context context, List<HotCourseItemBean> mDatas,DisplayImageOptions options) {
		this.mDatas = mDatas;
		this.context = context;
		this.options=options;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		HotCourseItemBean mItem = mDatas.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.gridview_home_course,
					null);
//			convertView.setLayoutParams(
//					new GridView.LayoutParams((int) (parent.getWidth() / 2), LayoutParams.WRAP_CONTENT));
			holder.subject = (TextView) convertView
					.findViewById(R.id.tv_home_course_subject);
			holder.host_pic = (ImageView) convertView
					.findViewById(R.id.iv_home_course_host_pic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
//			convertView.setLayoutParams(
//					new GridView.LayoutParams((int) (parent.getWidth() / 2), LayoutParams.WRAP_CONTENT));
		}
		//
		holder.subject.setText(mItem.getSubject());
		ImageLoader imageLoader=ImageLoader.getInstance();
		imageLoader.displayImage(mItem.getHost_pic(), holder.host_pic, options);
		return convertView;
	}

	private class ViewHolder {
		TextView subject;
		ImageView host_pic;

	}
}
