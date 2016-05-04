package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.ChartsLvAdapter.ViewHolder;
import com.mendale.app.pojo.ChartsPoJo;
import com.mendale.app.vo.CommentList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *评论listview
 * 
 * @author zhangxue
 * @date 2016年4月17日
 */
public class CommentLvAdapter extends BaseAdapter {

	private Context context;
	private List<CommentList> mDatas;

	public CommentLvAdapter(Context context, List<CommentList> mDatas) {
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
			convertView = View.inflate(context, R.layout.listview_item_comment, null);
			holder.userName = (TextView) convertView.findViewById(R.id.tv_comments_name);
			holder.content = (TextView) convertView.findViewById(R.id.tv_comments_cotent);
			holder.time = (TextView) convertView.findViewById(R.id.tv_comments_time);
			holder.facePic = (ImageView) convertView.findViewById(R.id.iv_comments_pic);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		//设置
	
		holder.userName.setText(mDatas.get(position).getUser_name());
		holder.content.setText(mDatas.get(position).getComment());
		holder.content.setText(mDatas.get(position).getAdd_time());
		return convertView;
	}

	class ViewHolder {
		
		ImageView facePic;
		TextView userName;
		TextView content;
		TextView time;

	}
}
