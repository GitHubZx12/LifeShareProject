package com.mendale.app.services;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.mendale.app.database.DataBaseConstDefine;
import com.mendale.app.database.DataBaseUtil;
import com.mendale.app.database.SQLiteDataBaseHelper;
import com.mendale.app.pojo.Logs;

/**
 * 24,日志信息表service
 * 
 * @author wenjian
 * 
 */
public class LogsService implements DataBaseConstDefine {

	private static LogsService service;
	private static Context mContext;

	private LogsService() {
	}

	public static LogsService getInstance(Context context) {
		mContext = context;
		if (null == service) {
			service = new LogsService();
		}
		return service;
	}

	/**
	 * 根据ID查询日志信息
	 * 
	 * @return
	 */
	public ArrayList<Logs> query(String werks) {
		ArrayList<Logs> list = new ArrayList<Logs>();
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		Cursor cursor = helper.query(TABLE_LOGS, LOGS_COLUMNS, LOGS_ID + "=? ", new String[] { werks });
		if (null != cursor) {
			int size = cursor.getCount();
			if (0 != size) {
				cursor.moveToFirst();
				for (int i = 0; i < size; i++) {
					Logs logs = new Logs();
					logs.setId(cursor.getString(cursor.getColumnIndex(LOGS_ID)));// id
					logs.setSapAccount(cursor.getString(cursor.getColumnIndex(LOGS_SAPACCOUNT)));// 调用者账号
					logs.setSendsys(cursor.getString(cursor.getColumnIndex(LOGS_SENDSYS)));// 发送系统
					logs.setReceivesys(cursor.getString(cursor.getColumnIndex(LOGS_RECEIVESYS)));// 接收系统
					logs.setApiname(cursor.getString(cursor.getColumnIndex(LOGS_APINAME)));// 接口名称
					logs.setStarttime(cursor.getString(cursor.getColumnIndex(LOGS_STARTTIME)));// 开始时间
					logs.setEndtime(cursor.getString(cursor.getColumnIndex(LOGS_ENDTIME)));// 结束时间
					logs.setData(cursor.getString(cursor.getColumnIndex(LOGS_DATA)));// 传输数据
					logs.setRes(cursor.getString(cursor.getColumnIndex(LOGS_RES)));// 处理结果
					logs.setErrorinfo(cursor.getString(cursor.getColumnIndex(LOGS_ERRORINFO)));// 异常信息
					logs.setUploadStatus(cursor.getString(cursor.getColumnIndex(LOGS_UPLOADSTATUS)));// 提交状态
					cursor.moveToNext();
					list.add(logs);
				}
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 插入
	 * 
	 * @param Logs
	 * @return
	 */
	public boolean insert(Logs logs) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		//开启事务
		try {
			helper.insert(TABLE_LOGS, LOGS_COLUMNS_SHORT,
					new Object[] { logs.getSapAccount(), logs.getSendsys(), logs.getReceivesys(), logs.getApiname(),
							logs.getStarttime(), logs.getEndtime(), logs.getData(), logs.getRes(), logs.getErrorinfo(),
							logs.getUploadStatus() });
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
	 * 更新
	 * 
	 * @param Logs
	 * @return
	 */
	public boolean update(Logs logs) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.update(
					TABLE_LOGS,
					LOGS_COLUMNS,
					new Object[] { logs.getId(), logs.getSapAccount(), logs.getSendsys(), logs.getReceivesys(),
							logs.getApiname(), logs.getStarttime(), logs.getEndtime(), logs.getData(), logs.getRes(),
							logs.getErrorinfo(), logs.getUploadStatus() }, LOGS_ID + "=? ",
					new String[] { logs.getId() });
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
			helper.delete(TABLE_LOGS, whereClause, whereArgs);
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
