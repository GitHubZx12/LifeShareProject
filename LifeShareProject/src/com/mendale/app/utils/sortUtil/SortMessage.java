package com.mendale.app.utils.sortUtil;

import java.util.Comparator;

import com.mendale.app.pojo.MessageInfo;

/**
 * 消息排序逻辑类 倒序
 * 
 * @author wenjian
 * 
 */
public class SortMessage implements Comparator<Object> {

	public int compare(Object arg0, Object arg1) {
		MessageInfo messageInfo0 = (MessageInfo) arg0;
		MessageInfo messageInfo1 = (MessageInfo) arg1;
		// 比较时间 支持String比较 默认从小到大
		// 返回1表示大于，返回-1表示小于，返回0表示相等
		int flag = messageInfo0.getMsgDate().compareTo(messageInfo1.getMsgDate());
		if (flag == 0) {
			// 相同 或不用改变顺序
			return flag;
		}
		else if (flag == 1) {
			// 1要改变顺序 因为要从大到小
			return -flag;
		}
		else {
			return -flag;
		}
	}
}
