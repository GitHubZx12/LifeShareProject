package com.mendale.app.services;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.mendale.app.database.DataBaseConstDefine;
import com.mendale.app.database.DataBaseUtil;
import com.mendale.app.database.SQLiteDataBaseHelper;
import com.mendale.app.pojo.ProfileInfo;

/**
 * 25,配置记录表
 * 
 * @author zhangxc
 * 
 *         2014-6-25
 */
public class ProfileInfoService implements DataBaseConstDefine {

	private static ProfileInfoService service;
	private static Context mContext;

	public ProfileInfoService() {
	}

	public static ProfileInfoService getInstance(Context context) {
		mContext = context;
		if (null == service) {
			service = new ProfileInfoService();
		}
		return service;
	}

	/**
	 * 根据sap账号配置记录
	 * 
	 * @return
	 */
	public ArrayList<ProfileInfo> query(String SAPACCOUNT) {
		ArrayList<ProfileInfo> list = new ArrayList<ProfileInfo>();
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		Cursor cursor = helper.query(TABLE_PROFILEINFO, PROFILEINFO_COLUMNS, PROFILEINFO_SAPACCOUNT + "=? ",
				new String[] { SAPACCOUNT });
		if (null != cursor) {
			int size = cursor.getCount();
			if (0 != size) {
				cursor.moveToFirst();
				for (int i = 0; i < size; i++) {
					ProfileInfo profileInfo = new ProfileInfo();
					profileInfo.setId(cursor.getString(cursor.getColumnIndex(PROFILEINFO_ID)));// id
					profileInfo.setMainPage(cursor.getString(cursor.getColumnIndex(PROFILEINFO_MAINPAGE)));// 初始页配置
					profileInfo.setAuart(cursor.getString(cursor.getColumnIndex(PROFILEINFO_AUART)));// 优先业务配置
					profileInfo.setCurrentVer(cursor.getString(cursor.getColumnIndex(PROFILEINFO_CURRENTVER)));// 当前APP版本
					profileInfo.setNewVer(cursor.getString(cursor.getColumnIndex(PROFILEINFO_NEWVER)));// 最新版本号
					profileInfo
							.setPlatformAccount(cursor.getString(cursor.getColumnIndex(PROFILEINFO_PLATFORMACCOUNT)));// 平台账号
					profileInfo.setPlatformPass(cursor.getString(cursor.getColumnIndex(PROFILEINFO_PLATFORMPASS)));// 平台密码
					profileInfo.setSapAccount(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SAPACCOUNT)));// sap账号
					profileInfo.setSapPass(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SAPPASS)));// sap密码
					profileInfo.setPatternsPass(cursor.getString(cursor.getColumnIndex(PROFILEINFO_PATTERNSPASS)));// 图案密码
					profileInfo.setUserName(cursor.getString(cursor.getColumnIndex(PROFILEINFO_USERNAME)));// 用户姓名
					profileInfo.setUserTel(cursor.getString(cursor.getColumnIndex(PROFILEINFO_USERTEL)));// 用户电话
					profileInfo.setSkins(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SKINS)));// 皮肤选择
					profileInfo.setSearTime(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SEARTIME)));// 轮询时间1
					profileInfo.setPoolTime(cursor.getString(cursor.getColumnIndex(PROFILEINFO_POOLTIME)));// 消息保留时间2
					profileInfo.setScheduleTime(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SCHEDULETIME)));// 定时时间3
					profileInfo.setLastSyncDate(cursor.getString(cursor.getColumnIndex(PROFILEINFO_LASTSYNCDATE)));// 消息上一次获取时间
					profileInfo.setExt1(cursor.getString(cursor.getColumnIndex(PROFILEINFO_EXT1)));// 预留字段1
					profileInfo.setExt2(cursor.getString(cursor.getColumnIndex(PROFILEINFO_EXT2)));// 预留字段2
					profileInfo.setExt3(cursor.getString(cursor.getColumnIndex(PROFILEINFO_EXT3)));// 预留字段3
					cursor.moveToNext();
					list.add(profileInfo);
				}
			}
			cursor.close();
		}
		return list;
	}
	
	/**
	 * 根据快捷码查询sap账号  能查出数据说明快捷码正确 
	 * 
	 * @return
	 */
	public ArrayList<ProfileInfo> queryByPass(String partenPass) {
		ArrayList<ProfileInfo> list = new ArrayList<ProfileInfo>();
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		Cursor cursor = helper.query(TABLE_PROFILEINFO, PROFILEINFO_COLUMNS, PROFILEINFO_PATTERNSPASS + "=? ",
				new String[] { partenPass });
		if (null != cursor) {
			int size = cursor.getCount();
			if (0 != size) {
				cursor.moveToFirst();
				for (int i = 0; i < size; i++) {
					ProfileInfo profileInfo = new ProfileInfo();
					profileInfo.setId(cursor.getString(cursor.getColumnIndex(PROFILEINFO_ID)));// id
					profileInfo.setMainPage(cursor.getString(cursor.getColumnIndex(PROFILEINFO_MAINPAGE)));// 初始页配置
					profileInfo.setAuart(cursor.getString(cursor.getColumnIndex(PROFILEINFO_AUART)));// 优先业务配置
					profileInfo.setCurrentVer(cursor.getString(cursor.getColumnIndex(PROFILEINFO_CURRENTVER)));// 当前APP版本
					profileInfo.setNewVer(cursor.getString(cursor.getColumnIndex(PROFILEINFO_NEWVER)));// 最新版本号
					profileInfo
							.setPlatformAccount(cursor.getString(cursor.getColumnIndex(PROFILEINFO_PLATFORMACCOUNT)));// 平台账号
					profileInfo.setPlatformPass(cursor.getString(cursor.getColumnIndex(PROFILEINFO_PLATFORMPASS)));// 平台密码
					profileInfo.setSapAccount(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SAPACCOUNT)));// sap账号
					profileInfo.setSapPass(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SAPPASS)));// sap密码
					profileInfo.setPatternsPass(cursor.getString(cursor.getColumnIndex(PROFILEINFO_PATTERNSPASS)));// 图案密码
					profileInfo.setUserName(cursor.getString(cursor.getColumnIndex(PROFILEINFO_USERNAME)));// 用户姓名
					profileInfo.setUserTel(cursor.getString(cursor.getColumnIndex(PROFILEINFO_USERTEL)));// 用户电话
					profileInfo.setSkins(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SKINS)));// 皮肤选择
					profileInfo.setSearTime(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SEARTIME)));// 轮询时间1
					profileInfo.setPoolTime(cursor.getString(cursor.getColumnIndex(PROFILEINFO_POOLTIME)));// 消息保留时间2
					profileInfo.setScheduleTime(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SCHEDULETIME)));// 定时时间3
					profileInfo.setLastSyncDate(cursor.getString(cursor.getColumnIndex(PROFILEINFO_LASTSYNCDATE)));// 消息上一次获取时间
					profileInfo.setExt1(cursor.getString(cursor.getColumnIndex(PROFILEINFO_EXT1)));// 预留字段1
					profileInfo.setExt2(cursor.getString(cursor.getColumnIndex(PROFILEINFO_EXT2)));// 预留字段2
					profileInfo.setExt3(cursor.getString(cursor.getColumnIndex(PROFILEINFO_EXT3)));// 预留字段3
					cursor.moveToNext();
					list.add(profileInfo);
				}
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	public ArrayList<ProfileInfo> queryAll() {
		ArrayList<ProfileInfo> list = new ArrayList<ProfileInfo>();
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		Cursor cursor = helper.query(TABLE_PROFILEINFO, PROFILEINFO_COLUMNS, null, null);
		if (null != cursor) {
			int size = cursor.getCount();
			if (0 != size) {
				cursor.moveToFirst();
				for (int i = 0; i < size; i++) {
					ProfileInfo profileInfo = new ProfileInfo();
					profileInfo.setId(cursor.getString(cursor.getColumnIndex(PROFILEINFO_ID)));// id
					profileInfo.setMainPage(cursor.getString(cursor.getColumnIndex(PROFILEINFO_MAINPAGE)));// 初始页配置
					profileInfo.setAuart(cursor.getString(cursor.getColumnIndex(PROFILEINFO_AUART)));// 优先业务配置
					profileInfo.setCurrentVer(cursor.getString(cursor.getColumnIndex(PROFILEINFO_CURRENTVER)));// 当前APP版本
					profileInfo.setNewVer(cursor.getString(cursor.getColumnIndex(PROFILEINFO_NEWVER)));// 最新版本号
					profileInfo
							.setPlatformAccount(cursor.getString(cursor.getColumnIndex(PROFILEINFO_PLATFORMACCOUNT)));// 平台账号
					profileInfo.setPlatformPass(cursor.getString(cursor.getColumnIndex(PROFILEINFO_PLATFORMPASS)));// 平台密码
					profileInfo.setSapAccount(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SAPACCOUNT)));// sap账号
					profileInfo.setSapPass(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SAPPASS)));// sap密码
					profileInfo.setPatternsPass(cursor.getString(cursor.getColumnIndex(PROFILEINFO_PATTERNSPASS)));// 图案密码
					profileInfo.setUserName(cursor.getString(cursor.getColumnIndex(PROFILEINFO_USERNAME)));// 用户姓名
					profileInfo.setUserTel(cursor.getString(cursor.getColumnIndex(PROFILEINFO_USERTEL)));// 用户电话
					profileInfo.setSkins(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SKINS)));// 皮肤选择
					profileInfo.setSearTime(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SEARTIME)));// 轮询时间1
					profileInfo.setPoolTime(cursor.getString(cursor.getColumnIndex(PROFILEINFO_POOLTIME)));// 消息保留时间2
					profileInfo.setScheduleTime(cursor.getString(cursor.getColumnIndex(PROFILEINFO_SCHEDULETIME)));// 定时时间3
					profileInfo.setLastSyncDate(cursor.getString(cursor.getColumnIndex(PROFILEINFO_LASTSYNCDATE)));// 消息上一次获取时间
					profileInfo.setExt1(cursor.getString(cursor.getColumnIndex(PROFILEINFO_EXT1)));// 预留字段1
					profileInfo.setExt2(cursor.getString(cursor.getColumnIndex(PROFILEINFO_EXT2)));// 预留字段2
					profileInfo.setExt3(cursor.getString(cursor.getColumnIndex(PROFILEINFO_EXT3)));// 预留字段3
					cursor.moveToNext();
					list.add(profileInfo);
				}
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 插入
	 * 
	 * @param ProfileInfo
	 * @return
	 */
	public boolean insert(ProfileInfo profileInfo) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.insert(
					TABLE_PROFILEINFO,
					PROFILEINFO_COLUMNS_SHORT,
					new Object[] { profileInfo.getMainPage(), profileInfo.getAuart(), profileInfo.getCurrentVer(),
							profileInfo.getNewVer(), profileInfo.getPlatformAccount(), profileInfo.getPlatformPass(),
							profileInfo.getSapAccount(), profileInfo.getSapPass(), profileInfo.getPatternsPass(),
							profileInfo.getUserName(), profileInfo.getUserTel(), profileInfo.getSkins(),
							profileInfo.getSearTime(), profileInfo.getPoolTime(), profileInfo.getScheduleTime(),
							profileInfo.getLastSyncDate(), profileInfo.getExt1(), profileInfo.getExt2(),
							profileInfo.getExt3() });
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
	 * 更新(all)
	 * 
	 * @param ProfileInfo
	 * @return
	 */
	public boolean update(ProfileInfo profileInfo) {
		// 处理结果标志
		boolean dealFalg = false;
		SQLiteDataBaseHelper helper = DataBaseUtil.getDataBaseHelper(mContext);
		helper.beginTransaction();
		try {
			helper.update(
					TABLE_PROFILEINFO,
					PROFILEINFO_COLUMNS,
					new Object[] { profileInfo.getId(), profileInfo.getMainPage(), profileInfo.getAuart(),
							profileInfo.getCurrentVer(), profileInfo.getNewVer(), profileInfo.getPlatformAccount(),
							profileInfo.getPlatformPass(), profileInfo.getSapAccount(), profileInfo.getSapPass(),
							profileInfo.getPatternsPass(), profileInfo.getUserName(), profileInfo.getUserTel(),
							profileInfo.getSkins(), profileInfo.getSearTime(), profileInfo.getPoolTime(),
							profileInfo.getScheduleTime(), profileInfo.getLastSyncDate(), profileInfo.getExt1(),
							profileInfo.getExt2(), profileInfo.getExt3() }, PROFILEINFO_SAPACCOUNT + "=? ",
					new String[] { profileInfo.getSapAccount() });
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
	 * 通用更新数据库方法
	 * 
	 * @param tabName 表明
	 * @param colums 字段
	 * @param objects 字段对应的值
	 * @param whereClause where条件
	 * @param whereArgs where条件的值
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
			helper.delete(TABLE_PROFILEINFO, whereClause, whereArgs);
			// 设置事务处理成功标志
			helper.transactionSuccessful();
			return true;
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
