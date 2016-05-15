package com.mendale.app.ui.mycenter.setting;

import java.io.File;

import com.mendale.app.R;
import com.mendale.app.constants.URLS;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.mycenter.MyCenterActivity;
import com.mendale.app.utils.Utils;
import com.mendale.app.utils.imageUtils.ImageOpera;
import com.mendale.app.utils.imageUtils.RoundImageView;
import com.umeng.socialize.utils.Log;

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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 个人资料详情
 * 
 * @author zhangxue
 * @date 2016-1-26
 */
public class MarkManActivity extends BaseActivity implements OnClickListener {

	protected static final String TAG = "MarkManActivity";
	private RoundImageView iv_changeHead;
	private EditText sign;
	private EditText birthy;
	private EditText sex;
	private EditText city;
	private static String iconpath;
	private Handler mHandler = new Handler();
	private String path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mark_man);
		initHeaderView();
		initView();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		iv_changeHead = (RoundImageView) findViewById(R.id.iv_changeHead);
		sign = (EditText) findViewById(R.id.et_mark_sign);
		birthy = (EditText) findViewById(R.id.et_mark_birthy);
		sex = (EditText) findViewById(R.id.et_mark_phone);
		city = (EditText) findViewById(R.id.et_mark_city);
		iv_changeHead.setOnClickListener(this);
		
		//
		MyUser user=BmobUser.getCurrentUser(this,MyUser.class);
		sign.setText(user.getSign());
		birthy.setText(user.getBirthy());
		sex.setText(user.getSex());
		city.setText(user.getCity());
		if (null!=user.getUrl()) {
			ImageOpera.getInstance(this).loadImage(user.getUrl(), iv_changeHead);
		}
		else {
			iv_changeHead.setImageResource(R.drawable.defult_avator);
		}
		
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
				//更新用户信息
				updateInfo();
			}

		}, 2000);
	}
	
	/**
	 * 修改个人数据
	 */
	protected void updateInfo() {
		final MyUser oldUser=BmobUser.getCurrentUser(this,MyUser.class);
		if(null!=path){
			final BmobFile file=new BmobFile(new File(path));
			file.upload(this, new UploadFileListener() {//上传图片
				
				@Override
				public void onSuccess() {
					final MyUser user=new MyUser();
					user.setUrl(file.getFileUrl(MarkManActivity.this));//保存图片路径
					user.setImg(file);//保存图片
					uploadOtherInfo(oldUser,user);
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					Log.e(TAG,arg0+arg1);
				}
			});
		}else{
			MyUser user=new MyUser();
			uploadOtherInfo(oldUser,user);
		}
		
		
	}

	protected void uploadOtherInfo(MyUser oldUser,final MyUser user) {
		user.setSign(sign.getText().toString());
		user.setBirthy(birthy.getText().toString());
		user.setCity(city.getText().toString());
		user.setSex(sex.getText().toString());
		
		user.update(MarkManActivity.this,oldUser.getObjectId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				closeLoadDialog();// 关闭弹框
				Intent data=new Intent(MarkManActivity.this,MyCenterActivity.class);
				Bundle extras=new Bundle();
				extras.putSerializable("userinfo", user);
				data.putExtras(extras);
				setResult(2, data);
				MarkManActivity.this.finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				if(arg0==301){
					showToast("更新失败   email Must be a valid email address");
				}
				closeLoadDialog();
				Log.e("tag",arg0+arg1);
			}
		});
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
					setViewData();
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
