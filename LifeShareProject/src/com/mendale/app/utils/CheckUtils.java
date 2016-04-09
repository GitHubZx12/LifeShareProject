package com.mendale.app.utils;

import android.content.Context;
import android.widget.EditText;

import com.mendale.app.R;

/**
 * 判断用户名密码等是否为空、合法等
 * 
 * @author zhangxue
 * @date 2015-12-14
 */
public class CheckUtils {

	/**
	 * 判断用户名是否为空、是否符合规范
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkUsername(Context context, String name, EditText username) {
		if (StringUtils.isNotNull(name)) {// 输入框是否为空
			if (!StringUtils.isuser(name)) {// 用户名是否为空
				DialogUtil.showDialog(context, context.getString(R.string.text_username), true, username);
				return false;
			}
			else {
				return true;
			}
		}
		else {
			DialogUtil.showDialog(context, "用户名不能为空", true, username);
			return false;
		}
	}

	/**
	 * 判断手机号是否符合规范
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkPhone(Context context, String phone, EditText etPhone) {
		if (StringUtils.isNotNull(phone)) {// 输入框是否为空
			return true;
		}
		else {
			DialogUtil.showDialog(context, "手机号不能为空", true, etPhone);
			return false;
		}
	}

	/**
	 * 判断密码是否为空、是否符合规范
	 * 
	 * @param pass
	 * @return
	 */
	public static boolean checkPwd(Context context, String pass, EditText password) {
		if (StringUtils.isNotNull(pass)) {// 输入框是否为空
			if (!StringUtils.ispwd(pass)) {// 密码是否为空
				DialogUtil.showDialog(context, context.getString(R.string.text_pwd), true, password);
				return false;
			}
			else {
				return true;
			}
		}
		else {
			DialogUtil.showDialog(context, "密码不能为空", true, password);
			return false;
		}
	}

	/**
	 * 检验输入的密码是否合法
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static boolean checkPwd2(Context context, String p1, String p2, EditText pwd1, EditText pwd2) {
		if (StringUtils.isNotNull(p2)) {// 输入框是否为空
			if (!StringUtils.ispwd(p2)) {// 密码是否为空
				DialogUtil.showDialog(context, context.getString(R.string.text_pwd), true, pwd2);
				return false;
			}
			else {
				if (StringUtils.isNotNull(p1)) {
					if (p1.equals(p2)) {// 两次输入的密码是否一致
						return true;
					}
					else {
						DialogUtil.showDialog(context, "密码和确认密码不一致", true, pwd2);
						return false;
					}
				}
				else {
					DialogUtil.showDialog(context, context.getString(R.string.text_pwd), true, pwd1);
					return false;
				}
			}
		}
		else {
			DialogUtil.showDialog(context, "密码不能为空", true, pwd2);
			return false;
		}
	}
}
