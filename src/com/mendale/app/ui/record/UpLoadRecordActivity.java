package com.mendale.app.ui.record;

import com.mendale.app.R;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.menu.ClassifyActivity;
import com.mendale.app.utils.Utils;
import com.mendale.app.utils.imageUtils.ImageOpera;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 上传记录。。
 * @author zx
 *
 */
public class UpLoadRecordActivity extends BaseActivity implements OnClickListener{
	
	private EditText title;
	private ImageView pic;
	private EditText share;
	//相册选中图片路径
	private static String iconpath;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_record);
		
		initHeaderView();
		initView();
	}

	/**
	 * 初始化view
	 */
	private void initHeaderView() {
		setNavigationTitle("上传记录");
		setNavigationLeftBtnText("");
		setNavigationRightBtnText("完成");
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		title=(EditText) findViewById(R.id.et_uprecord_title);
		share=(EditText) findViewById(R.id.et_uprecord_share);
		pic=(ImageView) findViewById(R.id.iv_uprecord_show_pic);
		
		pic.setOnClickListener(this);
		
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}
	@Override
	public void rightButtonOnClick() {
		super.rightButtonOnClick();
		showToast("完成");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_uprecord_show_pic:
			addmage(5);
			break;

		default:
			break;
		}
	}

	/**
	 * 更换头像 弹出选择相册的dialog
	 */
	private void addmage(final int flag) {
		showDigLog("相册", "取消", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.openSysPhone(flag, UpLoadRecordActivity.this);
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
				ImageOpera.getInstance(this).loadImage("file://" + path, pic);
				iconpath = "file://" + path;
				setViewData();
				break;
			default:
				break;
			}
		}
	}
	/**
	 * 页面控件赋值
	 */
	private void setViewData() {
		ImageOpera opera = ImageOpera.getInstance(this);
		if (iconpath != "") {
			pic.setVisibility(View.VISIBLE);
			ImageOpera.getInstance(this).loadImage(iconpath, pic);
		} else {
			pic.setImageResource(R.drawable.defult_avator);
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
}
