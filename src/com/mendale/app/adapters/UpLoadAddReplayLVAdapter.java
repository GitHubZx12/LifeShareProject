package com.mendale.app.adapters;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.pojo.MaterialPoJo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 上传--材料
 * @author Administrator
 *
 */
public class UpLoadAddReplayLVAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MaterialPoJo> text;
	ViewHolder holder;

	public UpLoadAddReplayLVAdapter(Context context, List<MaterialPoJo> text) {
		this.mInflater = LayoutInflater.from(context);
		this.text = text;
	}

	public int getCount() {
		return text.size();
	}

	public MaterialPoJo getItem(int position) {
		return text.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		 holder = new ViewHolder();
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listview_item_material, null);
			holder.del = (ImageButton) convertView
					.findViewById(R.id.iv_material_del);
			holder.name = (TextView)convertView
					.findViewById(R.id.et_material_name);
			holder.amount = (TextView) convertView
					.findViewById(R.id.et_material_amount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//
		holder.name.setText(text.get(position).getName());
		holder.amount.setText(text.get(position).getDesc());
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
		public TextView name;
		public ImageButton del;
		public TextView amount;

	}
}
