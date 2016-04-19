package com.mendale.app.services;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;

import com.mendale.app.database.DataBaseConstDefine;
import com.mendale.app.database.DataBaseUtil;
import com.mendale.app.database.SQLiteDataBaseHelper;
import com.mendale.app.pojo.MessageInfo;
import com.mendale.app.utils.StringDateTool;

/**
 * 26,消息记录表
 * 
 * @author wenjian
 * 
 */
public class MessageInfoService implements DataBaseConstDefine {

	private static MessageInfoService service;
	private static Context mContext;

	private MessageInfoService() {
	}

	public static MessageInfoService getInstance(Context context) {
		mContext = context;
		if (null == service) {
			service = new MessageInfoService();
		}
		return service;
	}

	/**
	 * 根据员工编号查询消息
	 * 
	 * @return
	 */
	public ArrayList<MessageInfo> query(String msgreceiver) {
		ArrayList<MessageInfo> list = new ArrayList<MessageInfo>();
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		Cursor cursor = helper.query(TABLE_MESSAGEINFO, MESSAGEINFO_COLUMNS, MESSAGEINFO_MSGRECEIVER + "=? ",
				new String[] { msgreceiver });
		if (null != cursor) {
			int size = cursor.getCount();
			if (0 != size) {
				cursor.moveToFirst();
				for (int i = 0; i < size; i++) {
					MessageInfo messageInfo = new MessageInfo();
					messageInfo.setId(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_ID)));// id
					messageInfo.setMsgCode(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_MSGCODE)));// 消息编号
					messageInfo.setMsgDate(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_MSGDATE)));// 消息日期
					messageInfo.setMsgContent(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_MSGCONTENT)));// 消息内容
					messageInfo.setMsgReceiver(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_MSGRECEIVER)));// 接收者
					messageInfo.setMsgTypeDesc(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_MSGTYPEDESC)));// 消息类型描述
					messageInfo.setMsgType(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_MSGTYPE)));// 消息类型
					messageInfo.setViewStatus(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_VIEWSTATUS)));// 查看状态
					messageInfo.setTaskNo(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_TASKNO)));// 单号
					messageInfo.setTaskType(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_TASKTYPE)));// 单据类型
					messageInfo.setPernr(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_PERNR)));// 用户编码
					messageInfo.setUpdateTable(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_UPDATETABLE)));// (基础下载)更新的表名
					cursor.moveToNext();
					list.add(messageInfo);
				}
			}
			cursor.close();
		}
		return list;
	}

	

	/**
	 * 根据消息编号查询消息
	 * 
	 * @return
	 */
	public ArrayList<MessageInfo> queryByNum(String num) {
		ArrayList<MessageInfo> list = new ArrayList<MessageInfo>();
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		Cursor cursor = helper.query(TABLE_MESSAGEINFO, MESSAGEINFO_COLUMNS, MESSAGEINFO_MSGCODE + "=? ",
				new String[] { num });
		if (null != cursor) {
			int size = cursor.getCount();
			if (0 != size) {
				cursor.moveToFirst();
				for (int i = 0; i < size; i++) {
					MessageInfo messageInfo = new MessageInfo();
					messageInfo.setId(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_ID)));// id
					messageInfo.setMsgCode(cursor.getString(cursor.getColumnIndex(MESSAGEINFO_MSGCODE)));// 消息编号
					cursor.moveToNext();
					list.add(messageInfo);
				}
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 插入
	 * 
	 * @param MessageInfo
	 * @return
	 */
	public boolean insert(MessageInfo messageInfo) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.insert(TABLE_MESSAGEINFO, MESSAGEINFO_COLUMNS_SHORT,
					new Object[] { messageInfo.getMsgCode(), messageInfo.getMsgDate(), messageInfo.getMsgContent(),
							messageInfo.getMsgReceiver(), messageInfo.getMsgTypeDesc(), messageInfo.getMsgType(),
							messageInfo.getViewStatus(), messageInfo.getTaskNo(), messageInfo.getTaskType(),
							messageInfo.getPernr(), messageInfo.getUpdateTable() });
			dealFalg = true;
			// 设置事物处理成功标志
			helper.transactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			dealFalg = false;
		} finally {
			// 提交事物/回滚事物
			helper.endTransaction();
		}
		return dealFalg;
	}

	/**
	 * 插入或者更新
	 * 
	 * @param MessageInfo
	 * @return
	 */
	public String insertOrUpdate(MessageInfo messageInfo) {
		// 处理结果标志
		String dealFalg = "";
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			if (queryByNum(messageInfo.getMsgCode()).size() == 0) {
				helper.insert(
						TABLE_MESSAGEINFO,
						MESSAGEINFO_COLUMNS_SHORT,
						new Object[] { messageInfo.getMsgCode(), StringDateTool.getDateStr3(new Date()),
								messageInfo.getMsgContent(), messageInfo.getMsgReceiver(),
								messageInfo.getMsgTypeDesc(), messageInfo.getMsgType(), "0", messageInfo.getTaskNo(),
								messageInfo.getTaskType(), messageInfo.getPernr(), messageInfo.getUpdateTable() });
				dealFalg = "I";
			}
			else {
				helper.update(
						TABLE_MESSAGEINFO,
						MESSAGEINFO_COLUMNS,
						new Object[] { queryByNum(messageInfo.getMsgCode()).get(0).getId(), messageInfo.getMsgCode(),
								StringDateTool.getDateStr3(new Date()), messageInfo.getMsgContent(),
								messageInfo.getMsgReceiver(), messageInfo.getMsgTypeDesc(), messageInfo.getMsgType(),
								"1", messageInfo.getTaskNo(), messageInfo.getTaskType(), messageInfo.getPernr(),
								messageInfo.getUpdateTable() }, MESSAGEINFO_MSGCODE + "=? ",
						new String[] { messageInfo.getMsgCode() });
				dealFalg = "U";
			}
			// 设置事物处理成功标志
			helper.transactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			dealFalg = "E";
		} finally {
			// 提交事物/回滚事物
			helper.endTransaction();
		}
		return dealFalg;
	}

	/**
	 * 更新
	 * 
	 * @param MessageInfo
	 * @return
	 */
	public boolean update(MessageInfo messageInfo) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.update(
					TABLE_MESSAGEINFO,
					MESSAGEINFO_COLUMNS,
					new Object[] { messageInfo.getId(), messageInfo.getMsgCode(), messageInfo.getMsgDate(),
							messageInfo.getMsgContent(), messageInfo.getMsgReceiver(), messageInfo.getMsgTypeDesc(),
							messageInfo.getMsgType(), messageInfo.getViewStatus(), messageInfo.getTaskNo(),
							messageInfo.getTaskType(), messageInfo.getPernr(), messageInfo.getUpdateTable() },
					MESSAGEINFO_ID + "=? ", new String[] { messageInfo.getId() });
			dealFalg = true;
			// 设置事物处理成功标志
			helper.transactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			dealFalg = false;
		} finally {
			// 提交事物/回滚事物
			helper.endTransaction();
		}
		return dealFalg;
	}


	/**
	 * 通用根据条件删除 helper
	 * 
	 * @param list
	 */
	public boolean delete(String whereClause, String[] whereArgs) {
		boolean htag = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.delete(TABLE_MESSAGEINFO, whereClause, whereArgs);
			// 设置事务处理成功标志
			helper.transactionSuccessful();
			htag = true;
		} catch (Exception e) {
			e.printStackTrace();
			htag = false;
		} finally {
			// 提交事务/回滚事务
			helper.endTransaction();
		}
		return htag;
	}
}
