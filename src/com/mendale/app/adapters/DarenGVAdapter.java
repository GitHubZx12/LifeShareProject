package com.mendale.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mendale.app.R;
import com.mendale.app.vo.HomeDarenPoJo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 首页——手工达人——GridView
 * @author Administrator
 *
 */
public class DarenGVAdapter extends BaseAdapter {

	private List<HomeDarenPoJo> mDatas;
	private Context context;
	private DisplayImageOptions options;

	public DarenGVAdapter(Context context, List<HomeDarenPoJo> mDatas,DisplayImageOptions options) {
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
		HomeDarenPoJo mItem = mDatas.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.gridview_home_daren,
					null);
			holder.user_name = (TextView) convertView
					.findViewById(R.id.tv_home_daren_username);
			holder.face_pic = (ImageView) convertView
					.findViewById(R.id.iv_home_daren_face_pic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//
		holder.user_name.setText(mItem.getUser_name());
		ImageLoader imageLoader=ImageLoader.getInstance();
		imageLoader.displayImage(mItem.getFace_pic(), holder.face_pic, options);
		return convertView;
	}

	private class ViewHolder {
		TextView user_name;
		ImageView face_pic;

	}

}
