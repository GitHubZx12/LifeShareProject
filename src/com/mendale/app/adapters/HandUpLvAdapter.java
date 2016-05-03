package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.pojo.HandUpMorePoJo;
import com.mendale.app.pojo.HotCoursePoJo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 手工达人
 * 
 * @author zhangxue
 * @date 2016年4月27日
 */
public class HandUpLvAdapter extends BaseAdapter {

	public List<HandUpMorePoJo> mDatas;
	private Context context;
	private DisplayImageOptions options;

	public HandUpLvAdapter(Context context, List<HandUpMorePoJo> mDatas,DisplayImageOptions options) {
		this.mDatas = mDatas;
		this.context = context;
		this.options=options;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mDatas.get(arg0);
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
			convertView = View.inflate(context, R.layout.item_hand_up, null);
			holder.pos = (TextView) convertView.findViewById(R.id.tv_hand_up_pos);
			holder.username = (TextView) convertView.findViewById(R.id.tv_hand_up_name);
			holder.host_pic = (ImageView) convertView.findViewById(R.id.iv_hand_up_pic);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		//
		holder.pos.setText("" + (position + 1)); 
		holder.username.setText(mDatas.get(position).getUser_name());
		ImageLoader imageLoader=ImageLoader.getInstance();
		imageLoader.displayImage(mDatas.get(position).getFace_pic(), holder.host_pic,options);
		return convertView;
	}

	class ViewHolder {

		TextView pos, username;
		ImageView host_pic;
	}
}
