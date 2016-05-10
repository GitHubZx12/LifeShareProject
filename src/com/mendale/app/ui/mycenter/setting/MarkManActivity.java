package com.mendale.app.ui.mycenter.setting;

import com.mendale.app.R;
import com.mendale.app.application.MobileApplication;
import com.mendale.app.constants.URLS;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.mycenter.MyCenterActivity;
import com.mendale.app.utils.Utils;
import com.mendale.app.utils.imageUtils.ImageOpera;
import com.mendale.app.utils.imageUtils.RoundImageView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 个人资料详情
 * 
 * @author zhangxue
 * @date 2016-1-26
 */
public class MarkManActivity extends BaseActivity implements OnClickListener {

	private RoundImageView iv_changeHead;
	private EditText name;
	private EditText birthy;
	private EditText phone;
	private EditText email;
	private static String iconpath;
	private MyUser loginUser;
	private Handler mHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mark_man);
		initHeaderView();
		initView();
		setViewData();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		iv_changeHead = (RoundImageView) findViewById(R.id.iv_changeHead);
		name = (EditText) findViewById(R.id.et_mark_username);
		birthy = (EditText) findViewById(R.id.et_mark_birthy);
		phone = (EditText) findViewById(R.id.et_mark_phone);
		email = (EditText) findViewById(R.id.et_mark_email);
		iv_changeHead.setOnClickListener(this);
	}

	/**
	 * 初始化头部
	 */
	private void initHeaderView() {
		setNavigationTitle("编辑");
		setNavigationLeftBtnText("");
		setNavigationLeftBtnImage(R.drawable.crafter_cguide_lastarrow_yes);
		setNavigationRightBtnText("提交");
	}

	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}
	/**
	 * 提交
	 * 
	 * @param v
	 */
	@Override
	public void rightButtonOnClick() {
		super.rightButtonOnClick();
		showLoadDialog("正在更新资料");
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				updateInfo();
				closeLoadDialog();// 关闭弹框
				Intent data=new Intent(MarkManActivity.this,MyCenterActivity.class);
				Bundle extras=new Bundle();
				extras.putSerializable("userinfo", loginUser);
				data.putExtras(extras);
				setResult(2, data);
			}
		}, 2000);
	}

	/**
	 * 修改个人数据
	 */
	protected boolean updateInfo() {
		loginUser=new MyUser();
		if (!Utils.isEmpty(name.getText().toString())) {
			loginUser.setUsername(name.getText().toString());
		}
		if (!Utils.isEmpty(birthy.getText().toString())) {
			loginUser.setBirthy(birthy.getText().toString());
		}
		if (!Utils.isEmpty(email.getText().toString())) {
			loginUser.setEmail(email.getText().toString());
		}
		if (!Utils.isEmpty(phone.getText().toString())) {// 电话号
			if (this.phone.getText().toString().length() < 11) {
				showToast("电话号码必须是11位的");
				closeLoadDialog();
				return false;
			}
			loginUser.setMobilePhoneNumber(phone.getText().toString());
		}
		MobileApplication application=(MobileApplication) getApplication();
		loginUser.update(this,application.getmUserInfo().getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				showToast("更新成功");
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				showToast("更新失败");
				// TODO Auto-generated method stub
			}
		});
		
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_changeHead:// 更换头像
				changeAvatorImage(5);
				break;
			default:
				break;
		}
	}

	/**
	 * 更换头像 弹出选择相册的dialog
	 */
	private void changeAvatorImage(final int flag) {
		showDigLog("相册", "取消", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.openSysPhone(flag, MarkManActivity.this);
				closeDialog();
			}
		}, new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeDialog();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				showToast("请确认已经插入SD卡");
				return;
			}
			if (data.getData() == null) {
				showToast("返回路径为空");
				return;
			}
			Uri originalUri = data.getData(); // 在系统中图片的路径
			if (originalUri == null) {
				showToast("图片不存在");
				return;
			}
			String path = getrealPath(originalUri);
			if (Utils.isEmpty(path)) {
				showToast("图片不存在，请重新选择");
				return;
			}
			switch (requestCode) {
				case 5:
					ImageOpera.getInstance(this).loadImage("file://" + path, iv_changeHead);
					iconpath = "file://" + path;
					String path_avator = URLS.SDCARD_DIR + "ButlerImage" + "/avator_temp.jpg";
					// new ImageCompressTask(path, path_avator, avator,
					// true).execute(4);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 得到图片所在的sd卡路径
	 * 
	 * @param mImageCaptureUri
	 *            系统路径
	 */
	@SuppressWarnings("finally")
	private String getrealPath(Uri mImageCaptureUri) {
		String path = null;
		Cursor cursor = null;
		try {
			cursor = this.getContentResolver().query(mImageCaptureUri, null, null, null, null);
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndex("_data"));// 获取绝对路径
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			return path;
		}
	}

	/**
	 * 页面控件赋值
	 */
	private void setViewData() {
		ImageOpera opera = ImageOpera.getInstance(this);
		if (iconpath != "") {
			ImageOpera.getInstance(this).loadImage(iconpath, iv_changeHead);
		}
		else {
			iv_changeHead.setImageResource(R.drawable.defult_avator);
		}
	}
}
