package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.pojo.HotCoursePoJo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 热门教程
 * @author zhangxue 
   @date 2016年4月27日
 */
public class HotCourseLvAdapter extends BaseAdapter{

	public List<HotCoursePoJo> mDatas;
	private Context context;
	public HotCourseLvAdapter(Context context,List<HotCoursePoJo> mDatas){
		this.mDatas=mDatas;
		this.context=context;
		
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
		if(convertView==null){
			holder=new ViewHolder();
			convertView=View.inflate(context, R.layout.item_hot_course,null);
			
			holder.pos=(TextView) convertView.findViewById(R.id.tv_pos_news_top100);
			holder.subject=(TextView) convertView.findViewById(R.id.tv_subject_news_top100);
			holder.username=(TextView) convertView.findViewById(R.id.tv_username_news_top100);
			holder.view=(TextView) convertView.findViewById(R.id.tv_view_news_top100);
			holder.host_pic=(ImageView) convertView.findViewById(R.id.iv_pic_news_top100);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		//
		holder.pos.setText(""+(position+1));
		return convertView;
	}
	class ViewHolder {
		TextView pos,subject,username,view;
		ImageView host_pic;
	}
}
