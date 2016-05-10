package com.mendale.app.ui.record;

import java.io.File;

import com.mendale.app.R;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.pojo.Record;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.Utils;
import com.mendale.app.utils.imageUtils.ImageOpera;
import com.umeng.socialize.utils.Log;

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
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 上传教程分类
 * @author zx
 *
 */
public class UpLoadRecordActivity extends BaseActivity{
	
	private EditText etTitle;
	private ImageView ivImgContent;
	private TextView tvImgContent;
	private EditText etExperience;
	
	private String title;
	private String content;
	private String experience;
	//相册选中图片路径
		private static String iconpath;
		private String path ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_up_load_record);
		initHeadView();
		initView();
	}
	
	/**
	 * 初始化数据
	 */
	private void upload() {
		Log.e("tag","iconPaht"+iconpath+"---Path"+path);
		title=etTitle.getText().toString();
		experience=etExperience.getText().toString();
		final BmobFile file=new BmobFile(new File(path));
		//添加一对一关联
		MyUser user=BmobUser.getCurrentUser(this,MyUser.class);
		//创建记录信息
		final Record record=new Record();
		record.setContent(experience);
		record.setAuthor(user);
		file.upload(this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				record.setImage(file);
				
				record.save(UpLoadRecordActivity.this, new SaveListener() {
					
					@Override
					public void onSuccess() {
						UpLoadRecordActivity.this.finish();
					}
					@Override
					public void onFailure(int arg0, String arg1) {
						Log.e("tag",arg0+arg1);
					}
				});
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e("tag",arg0+arg1);
			}
		});
		
	}

	/**
	 * 页面控件赋值
	 */
	private void setViewData() {
		ImageOpera opera = ImageOpera.getInstance(this);
		if (iconpath != "") {
			tvImgContent.setVisibility(View.GONE);
			ivImgContent.setVisibility(View.VISIBLE);
			ImageOpera.getInstance(this).loadImage(iconpath, ivImgContent);
		} else {
			tvImgContent.setVisibility(View.VISIBLE);
			ivImgContent.setVisibility(View.GONE);
			ivImgContent.setImageResource(R.drawable.defult_avator);
		}
	}
	/**
	 * 初始化view
	 */
	private void initView() {
		etTitle=(EditText) findViewById(R.id.et_upload_record_title);
		ivImgContent=(ImageView) findViewById(R.id.iv_upload_record_pic);
		tvImgContent=(TextView) findViewById(R.id.tv_upload_recordy_pic);
		etExperience=(EditText) findViewById(R.id.et_upload_record_tip);
		
		tvImgContent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addmage();
			}
		});
	}

	/**
	 * 初始化头部view
	 */
	private void initHeadView() {
		setNavigationTitle("上传记录");
		setNavigationLeftBtnText(" ");
		setNavigationRightBtnText("上传");
	}
	
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}
	@Override
	public void rightButtonOnClick() {
		super.rightButtonOnClick();
		upload();
	}
	
	/**
	 * 更换头像 弹出选择相册的dialog
	 */
	private void addmage() {
		showDigLog("相册", "取消", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.openSysPhone(5, UpLoadRecordActivity.this);
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
//				ImageOpera.getInstance(this).loadImage("file://" + path, ivImgContent);
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
		path = null;
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
