package com.mendale.app.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mendale.app.R;
import com.mendale.app.pojo.MessageInfo;

public class MessageAdapter extends BaseAdapter {

	private List<MessageInfo> messages;// 要绑定的数据
	private int resource;// 绑定的条目界面
	private LayoutInflater inflater;

	public MessageAdapter(Context context, List<MessageInfo> messages, int resource) {
		this.messages = messages;
		this.resource = resource;
		// 布局填充器
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return messages.size();// 数据总数
	}

	@Override
	public Object getItem(int position) {
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView typeView = null;
		TextView timeView = null;
		TextView contentView = null;
		if (convertView == null) {
			convertView = inflater.inflate(resource, null);// 生成条目界面对象
			typeView = (TextView) convertView.findViewById(R.id.message_type);
			timeView = (TextView) convertView.findViewById(R.id.message_time);
			contentView = (TextView) convertView.findViewById(R.id.message_content);
			ViewCache cache = new ViewCache();
			cache.typeView = typeView;
			cache.timeView = timeView;
			cache.contentView = contentView;
			convertView.setTag(cache);
		}
		else {
			ViewCache cache = (ViewCache) convertView.getTag();
			typeView = cache.typeView;
			timeView = cache.timeView;
			contentView = cache.contentView;
		}
		MessageInfo message = messages.get(position);
		// 下面代码实现数据绑定
		typeView.setText(message.getMsgTypeDesc());
		// 日期为空处理
		if (message.getMsgDate().equals("")) {
			timeView.setText("");
		}
		else {
			timeView.setText(message.getMsgDate());
		}
		contentView.setText(message.getMsgContent());
		return convertView;
	}

	private final class ViewCache {

		public TextView typeView;
		public TextView timeView;
		public TextView contentView;
	}
}
