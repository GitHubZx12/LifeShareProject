package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.pojo.MaterialPoJo;
import com.mendale.app.pojo.ReplayPoJo;
import com.mendale.app.utils.imageUtils.ImageOpera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 上传--步骤
 * @author Administrator
 *
 */
public class UpLoadAddReplayLVAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ReplayPoJo> text;
	ViewHolder holder;
	private Context context;

	public UpLoadAddReplayLVAdapter(Context context, List<ReplayPoJo> text) {
		this.context=context;
		this.mInflater = LayoutInflater.from(context);
		this.text = text;
	}

	public int getCount() {
		return text.size();
	}

	public ReplayPoJo getItem(int position) {
		return text.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		 holder = new ViewHolder();
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listview_item_replay, null);
			holder.del = (ImageButton) convertView
					.findViewById(R.id.iv_replay_del);
			holder.pic = (ImageView)convertView
					.findViewById(R.id.iv_replay_pic);
			holder.desc = (TextView) convertView
					.findViewById(R.id.et_replay_desc);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//
		ImageOpera opera = ImageOpera.getInstance(context);
		ImageOpera.getInstance(context).loadImage(text.get(position).getPath(), holder.pic);
		holder.desc.setText(text.get(position).getDesc());
		holder.del.setOnClickListener(new View.OnClickListener() {// 添加按钮

					public void onClick(View v) {
//						text.remove(text.size() - 1);// 删除按钮
						text.remove(position);// 删除按钮
						notifyDataSetChanged();
					}
				});
	

		return convertView;
	}

	public final class ViewHolder {
		public TextView desc;
		public ImageButton del;
		public ImageView pic;

	}
}
