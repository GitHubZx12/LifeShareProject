package com.mendale.app.services;

import android.content.Context;

import com.mendale.app.database.DataBaseConstDefine;
import com.mendale.app.database.DataBaseUtil;
import com.mendale.app.database.SQLiteDataBaseHelper;

/**
 * 通用操作service
 * 
 * @author wenjian
 * 
 */
public class CommonService implements DataBaseConstDefine {

	private static CommonService service;
	private static Context mContext;

	private CommonService() {
	}

	public static CommonService getInstance(Context context) {
		mContext = context;
		if (null == service) {
			service = new CommonService();
		}
		return service;
	}

	/**
	 * 通用根据条件删除 helper
	 * 
	 * @param list
	 */
	public boolean delete(String tabName, String whereClause, String[] whereArgs) {
		boolean htag = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.delete(tabName, whereClause, whereArgs);
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

	/**
	 * 通用更新数据库方法(更新某些字段)
	 * 
	 * @param tabName
	 *            表明
	 * @param colums
	 *            字段
	 * @param objects
	 *            字段对应的值
	 * @param whereClause
	 *            where条件
	 * @param whereArgs
	 *            where条件的值
	 * @return
	 */
	public boolean updateByClause(String tabName, String[] colums, Object[] objects, String whereClause,
			String[] whereArgs) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.update(tabName, colums, objects, whereClause, whereArgs);
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
}
