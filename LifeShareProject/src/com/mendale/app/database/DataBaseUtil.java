package com.mendale.app.database;

import android.content.Context;

/**
 * 单例模式
 * @author wenjian
 *
 */
public class DataBaseUtil implements DataBaseConstDefine {

	private static SQLiteDataBaseHelper dataBaseHelper;
    
	/**
	 * 获得SQLiteDataBaseHelper的单例
	 * @param context 上下方对象
	 */
	public static SQLiteDataBaseHelper getDataBaseHelper(Context context){
		if (null==dataBaseHelper){//如果没有实例化，则实例化
			dataBaseHelper=new SQLiteDataBaseHelper(context);
		}
		return dataBaseHelper;
	}
	
}
