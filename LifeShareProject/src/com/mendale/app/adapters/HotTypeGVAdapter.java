package com.mendale.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mendale.app.R;
import com.mendale.app.vo.TypeItemBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 首页——热门类别——ListView
 * @author Administrator
 *
 */
public class HotTypeGVAdapter extends BaseAdapter {

	private List<TypeItemBean> mDatas;
	private Context context;
	private DisplayImageOptions options;

	public HotTypeGVAdapter(Context context, List<TypeItemBean> mDatas,DisplayImageOptions options) {
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
		TypeItemBean mItem = mDatas.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.listview_home_type,
					null);
			holder.cate_name = (TextView) convertView
					.findViewById(R.id.tv_home_type_username);
			holder.pic1 = (ImageView) convertView
					.findViewById(R.id.iv_home_type_pic1);
			holder.pic2 = (ImageView) convertView
					.findViewById(R.id.iv_home_type_pic2);
			holder.pic3 = (ImageView) convertView
					.findViewById(R.id.iv_home_type_pic3);
			holder.pic4 = (ImageView) convertView
					.findViewById(R.id.iv_home_type_pic4);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//
		holder.cate_name.setText(mItem.getCate_name());
		ImageLoader imageLoader=ImageLoader.getInstance();
		imageLoader.displayImage(mItem.getPic().get(0), holder.pic1, options);
		imageLoader.displayImage(mItem.getPic().get(1), holder.pic2, options);
		imageLoader.displayImage(mItem.getPic().get(2), holder.pic3, options);
		imageLoader.displayImage(mItem.getPic().get(3), holder.pic4, options);
		return convertView;
	}

	private class ViewHolder {
		TextView cate_name;
		ImageView pic1;
		ImageView pic2;
		ImageView pic3;
		ImageView pic4;

	}
}
