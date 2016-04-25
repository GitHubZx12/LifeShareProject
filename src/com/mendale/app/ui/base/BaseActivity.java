package com.mendale.app.ui.base;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mendale.app.R;
import com.mendale.app.ui.mycenter.MyCenterActivity;
import com.mendale.app.utils.ExitApplication;
import com.mendale.app.utils.Utils;

public class BaseActivity extends Activity implements NavigationOnClickListener {

	private final String TAG = "INFO";
	private TextView titleView;
	private String navigationTitle, leftBtnText, rightBtnText;
	private TextView leftBtn, rightBtn;
	private ImageButton leftImgBtn, rightImgBtn, centerImgBtn;
	private int leftImageId;
	private int rightImageId;
	private int centerImageId;
	public ProgressDialog dialog;
	protected Builder builder;
	public AlertDialog dialogBui;
	public long exitTime;// 储存点击退出时间

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏抬头
	}

	@Override
	protected void onStart() {
		super.onStart();
		initCommonView();
	}

	/**
	 * 通用toast方法
	 * 
	 * @param text
	 */
	public void showToast(String text) {
		Toast toast = new Toast(this);
		TextView textView = new TextView(this);
		textView.setText(text);
		textView.setTextSize(16);
		textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.toast_bg));
		textView.setTextColor(Color.WHITE);
		textView.setGravity(Gravity.CENTER);
		textView.setPadding(15, 15, 15, 15);
		toast.setView(textView);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(6000);
		toast.show();
	}

	/**
	 * 获取当前程序是否在运行
	 * 
	 * @return true-在运行 false-不在运行
	 */
	public boolean isAPPRunning(Context context) {
		String cnClassString = "";
		boolean isHas = false;
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = null;
		try {
			cn = am.getRunningTasks(1).get(0).topActivity;
			cnClassString = cn.getClassName() + "";
			isHas = cnClassString.equals(context.toString().split("@")[0]);
		} catch (SecurityException e) {
			cnClassString = "";
		}
		return isHas;
	}

	public void i(String msg) {
		Log.i(TAG, msg);
	}

	public void w(String msg) {
		Log.w(TAG, msg);
	}

	/**
	 * 错误弹出框
	 * 
	 * @param title
	 * @param msg
	 */
	@SuppressLint("NewApi")
	public void errorAlert(String title, String msg) {
		if (title.isEmpty() || msg.isEmpty()) {
			return;
		}
		if (isFinishing()) {
			return;
		}
		new AlertDialog.Builder(this).setTitle(title).setMessage(msg).setIcon(android.R.drawable.ic_dialog_info)
				.setNeutralButton("OK", null).show();
	}

	/**
	 * 信息显示框 点击消失
	 * 
	 * @param str
	 */
	public void showPopDialog(String str) {
		new AlertDialog.Builder(this).setTitle("提示").setMessage(str).setIcon(android.R.drawable.ic_dialog_info)
				.setNeutralButton("确定", null).show();
	}

	/**
	 * 跳转公共方法1
	 * 
	 * @param cls
	 */
	public void startActivity(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}

	/**
	 * 跳转公共方法2 带参数
	 * 
	 * @param cls
	 */
	public void startActivity(Class<?> cls, String[] keys, String[] values) {
		Intent intent = new Intent(this, cls);
		int size = keys.length;
		for (int i = 0; i < size; i++) {
			intent.putExtra(keys[i], values[i]);
		}
		startActivity(intent);
	}

	/**
	 * 下拉框通用方法 ArrayList
	 */
	public ArrayAdapter<String> getSpinnerAdapter(ArrayList<String> items) {
		// 街道adapter
		ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner, items);
		sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return sAdapter;
	}

	/**
	 * 下拉框通用方法 List
	 */
	public ArrayAdapter<String> getSpinnerAdapter(List<String> items) {
		// 街道adapter
		ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner, items);
		sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return sAdapter;
	}

	/**
	 * 下拉框通用方法 数组
	 */
	public ArrayAdapter<String> getSpinnerAdapter2(String[] items) {
		// 街道adapter
		ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner, items);
		sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return sAdapter;
	}

	/**
	 * 显示进度条
	 */
	public void showProgressDialog() {
		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * 显示不可取消的进度条
	 */
	public void showUnStopProgressDialog() {
		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * 显示带进度的进度条
	 */
	public void showNumberProgressDialog(String text, String title) {
		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setTitle(title + "");
		dialog.setMessage(text + "");
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setMax(100);
		dialog.show();
	}

	/**
	 * 显示带进度的进度条
	 */
	public void changeNumberProgressDialog(int num) {
		dialog.setProgress(num);
		dialog.incrementProgressBy(1);
	}

	/**
	 * 显示带标题进度条
	 * 
	 * @param text
	 * @param title
	 */
	public void showProgressDialog(String text, String title) {
		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setTitle(title + "");
		dialog.setMessage(text + "");
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * 显示不可停止的带标题进度条
	 * 
	 * @param text
	 * @param title
	 */
	public void showUnStopProgressDialog(String text, String title) {
		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		dialog.setTitle(title + "");
		dialog.setMessage(text + "");
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	/**
	 * 改变进度条显示内容
	 * 
	 * @param text
	 */
	public void changeProgressMessage(String text) {
		dialog.setMessage(text);
	}

	/**
	 * 关闭进度条
	 */
	public void dismissProgressDialog() {
		dialog.dismiss();
	}

	/**
	 * 弹出信息显示框
	 * 
	 * @param text
	 * @param title
	 */
	public void showAlertDialog(String text, String title) {
		builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
		builder.setMessage(text);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();
	}

	/**
	 * 弹出带确认 取消的 事件选择框
	 * 
	 * @param text
	 * @param title
	 * @param positiveListener
	 * @param negativeListener
	 */
	public void showYesOrNoDialog(String text, String title,
			android.content.DialogInterface.OnClickListener positiveListener,
			android.content.DialogInterface.OnClickListener negativeListener) {
		builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
		builder.setMessage(text);
		builder.setPositiveButton("确定", positiveListener);
		builder.setNegativeButton("取消", negativeListener);
		builder.show();
	}

	/************************************************************************
	 * 软键盘隐藏事件
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			// 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				hideSoftInput(v.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
	 * 
	 * @param v
	 * @param event
	 * @return
	 */
	private boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			}
			else {
				// 首页不用
				if (((String) this.getClass().toString()).contains("LoginActivity")) {
					return false;
				}
				else {
					return true;
				}
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
		return false;
	}

	/**
	 * 多种隐藏软件盘方法的其中一种
	 * 
	 * @param token
	 */
	private void hideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/******************************* menu键 ********************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_exit:
				ExitApplication.getInstance().exitAll();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 再按一次退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}
			else {
				/** 全局退出，相当于有些app的一键退出 */
				ExitApplication.getInstance().exitAll();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initCommonView() {
		leftImgBtn = (ImageButton) findViewById(R.id.navigationLeftImageBtn);
		if (leftImgBtn != null) {
			leftImgBtn.setVisibility(View.INVISIBLE);
			leftImgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					leftButtonOnClick();
				}
			});
		}
		rightImgBtn = (ImageButton) findViewById(R.id.navigationRightImageBtn);
		if (rightImgBtn != null) {
			rightImgBtn.setVisibility(View.INVISIBLE);
			rightImgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					rightImageButtonOnClick();
				}
			});
		}
		centerImgBtn = (ImageButton) findViewById(R.id.navigationCenterImageBtn);
		if (centerImgBtn != null) {
			centerImgBtn.setVisibility(View.GONE);
			centerImgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					centerButtonOnClick();
				}
			});
		}
		titleView = (TextView) findViewById(R.id.navigation_title);
		leftBtn = (TextView) findViewById(R.id.navigationLeftBtn);
		if (leftBtn != null) {
			leftBtn.setVisibility(View.GONE);
			leftBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					leftButtonOnClick();
				}
			});
		}
		rightBtn = (Button) findViewById(R.id.navigationRightBtn);
		if (rightBtn != null) {
			rightBtn.setVisibility(View.GONE);
			rightBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					rightButtonOnClick();
				}
			});
		}
		if (null != navigationTitle && null != titleView) {
			titleView.setText(navigationTitle);
		}
		if (leftImageId != 0 && null != leftImgBtn) {
			leftImgBtn.setImageResource(leftImageId);
			leftImgBtn.setVisibility(View.VISIBLE);
			leftBtn.setVisibility(View.VISIBLE);
		}
		if (rightImageId != 0 && null != rightImgBtn) {
			rightImgBtn.setImageResource(rightImageId);
			rightImgBtn.setVisibility(View.VISIBLE);
			rightBtn.setVisibility(View.GONE);
		}
		if (centerImageId != 0 && null != centerImgBtn) {
			centerImgBtn.setImageResource(centerImageId);
			centerImgBtn.setVisibility(View.VISIBLE);
		}
		if (null != leftBtnText && null != leftBtn) {
			leftBtn.setText(leftBtnText);
			leftBtn.setVisibility(View.VISIBLE);
			leftImgBtn.setVisibility(View.VISIBLE);
		}
		if (null != rightBtnText && null != rightBtn) {
			rightBtn.setText(rightBtnText);
			rightImgBtn.setVisibility(View.GONE);
			rightBtn.setVisibility(View.VISIBLE);
		}
		if (null == rightBtnText && null != rightBtn) {
			rightBtn.setText(rightBtnText);
			// rightImgBtn.setVisibility(View.GONE);
			rightBtn.setVisibility(View.GONE);
		}
		if (null != rightBtnText && null != rightBtn && rightImageId != 0 && null != rightImgBtn) {
			rightBtn.setText(rightBtnText);
			rightBtn.setVisibility(View.VISIBLE);
			rightImgBtn.setImageResource(rightImageId);
			rightImgBtn.setVisibility(View.VISIBLE);
		}
	}

	public void setNavigationTitle(String title) {
		navigationTitle = title;
		if (null != titleView) {
			titleView.setText(navigationTitle);
		}
	}

	public void setNavigationLeftBtnText(String text) {
		leftBtnText = text;
		if (null != leftBtn) {
			leftBtn.setVisibility(View.VISIBLE);
			leftImgBtn.setVisibility(View.GONE);
			leftBtn.setText(leftBtnText);
		}
	}

	public void setNavigationRightBtnText(String text) {
		rightBtnText = text;
		if (null != rightBtn) {
			rightBtn.setVisibility(View.VISIBLE);
			rightImgBtn.setVisibility(View.GONE);
			rightBtn.setText(rightBtnText);
		}
	}

	public void setNavigationRightBtnGone(String text) {
		rightBtnText = text;
		if (null != rightBtn) {
			rightBtn.setVisibility(View.GONE);
			rightImgBtn.setVisibility(View.GONE);
			rightBtn.setText(rightBtnText);
		}
	}

	public void setNavigationLeftBtnImage(int imageId) {
		leftImageId = imageId;
		if (null != leftImgBtn) {
			leftImgBtn.setImageResource(leftImageId);
			leftImgBtn.setVisibility(View.VISIBLE);
			leftBtn.setVisibility(View.GONE);
		}
	}

	public void setNavigationRightBtnImage(int imageId) {
		rightImageId = imageId;
		if (null != rightImgBtn) {
			rightImgBtn.setImageResource(rightImageId);
			rightImgBtn.setVisibility(View.VISIBLE);
			rightBtn.setVisibility(View.GONE);
		}
	}

	public void setNavigationCenterBtnShow(int imageId) {
		centerImageId = imageId;
		if (null != rightImgBtn) {
			centerImgBtn.setImageResource(centerImageId);
			centerImgBtn.setVisibility(View.VISIBLE);
		}
	}

	public void setLeftNavigatoinOnClick() {
	}

	public void leftButtonOnClick() {
		if (leftBtnText != null || leftImageId != 0) {
			finish();
		}
	}

	public void rightButtonOnClick() {
	}

	public void rightImageButtonOnClick() {
	}

	public void centerButtonOnClick() {
	}

	interface NavigationOnClickListener {

		public void leftButtonOnClick();

		public void rightButtonOnClick();

		public void centerButtonOnClick();

		public void rightImageButtonOnClick();
	}
	public Dialog myDialog;
	/**
	 * 
	 * @param firstText
	 *            第一个按钮的文字描述
	 * @param secondText第二个按钮的文字描述
	 * @param firstlisten第一个按钮的监听
	 * @param secondlisten第二个按钮的监听
	 */
	public void showDigLog(String firstText, String secondText, OnClickListener firstlisten,
			OnClickListener secondlisten) {
		// 是否已经打开了dialog了？(解决重复被点击问题)
		if (myDialog != null) {
			if (!myDialog.isShowing()) {
				myDialog.dismiss();
				myDialog = null;
			}
			return;
		}
		myDialog = new Dialog(this);
		// 监听dialog是否被隐藏了
		myDialog.show();
		myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		myDialog.getWindow().setContentView(R.layout.choicedialog);
		myDialog.getWindow().setGravity(Gravity.BOTTOM);
		myDialog.setCanceledOnTouchOutside(true);
		myDialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				myDialog.dismiss();
				myDialog = null;
			}
		});
		Button fbtn = (Button) myDialog.findViewById(R.id.dialog_firstbtn);
		Button sbtn = (Button) myDialog.findViewById(R.id.dialog_secondbtn);
		if (!Utils.isEmpty(firstText)) {
			fbtn.setText(firstText);
		}
		if (!Utils.isEmpty(secondText)) {
			sbtn.setText(secondText);
		}
		fbtn.setOnClickListener(firstlisten);
		sbtn.setOnClickListener(secondlisten);
	}

	/**
	 * 关闭
	 */
	public void closeDialog() {
		if (myDialog != null) {
			try {
				myDialog.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("之前的activity被销毁，dialog关闭异常。该报错可以忽略");
			}
			myDialog = null;
		}
	}
	private Dialog loadDialog;
	/**
	 * 加载数据时的Loading
	 * 
	 * @param msg
	 *            提示信息
	 * @return
	 */
	public void showLoadDialog(String msg) {
		if (isFinishing()) {
			closeLoadDialog();
			return;
		}
		if (loadDialog == null) {
			loadDialog = new Dialog(this, R.style.CustomProgressDialog);
			loadDialog.setContentView(R.layout.progress_dialog_customprogressdialog);
			loadDialog.setCancelable(false);
			ImageView notice_icon = (ImageView) loadDialog.getWindow().findViewById(R.id.loadingImageView);
			AnimationDrawable animationDrawable = (AnimationDrawable) notice_icon.getBackground();
			animationDrawable.start();
		}
		TextView notice_msg = (TextView) loadDialog.getWindow().findViewById(R.id.id_tv_loadingmsg);
		notice_msg.setText(msg);
		try {
			loadDialog.show();
		} catch (Exception e) {
			System.out.println("loading框show方法出错，异常被捕获");
			e.printStackTrace();
		}
	}

	/**
	 * 关闭加载dialog
	 */
	public void closeLoadDialog() {
		if (loadDialog != null) {
			try {
				loadDialog.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("之前的activity被销毁，dialog关闭异常。该报错可以忽略");
			}
			loadDialog = null;
		}

	}

}
