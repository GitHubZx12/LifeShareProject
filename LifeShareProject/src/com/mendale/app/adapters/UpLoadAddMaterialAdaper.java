package com.mendale.app.adapters;

import java.util.ArrayList;

import com.mendale.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * 上传--材料
 * @author Administrator
 *
 */
public class UpLoadAddMaterialAdaper extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<String> text;

	public UpLoadAddMaterialAdaper(Context context, ArrayList<String> text) {
		this.mInflater = LayoutInflater.from(context);
		this.text = text;
	}

	public int getCount() {
		return text.size();
	}

	public Object getItem(int position) {
		return text.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listview_item_material, null);
			holder.del = (ImageButton) convertView
					.findViewById(R.id.iv_material_del);
			holder.name = (EditText) convertView
					.findViewById(R.id.et_material_name);
			holder.amount = (EditText) convertView
					.findViewById(R.id.et_material_amount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
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
		public EditText name;
		public ImageButton del;
		public EditText amount;

	}
}
