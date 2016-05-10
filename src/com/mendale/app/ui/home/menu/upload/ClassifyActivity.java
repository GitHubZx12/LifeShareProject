package com.mendale.app.ui.home.menu.upload;

import java.io.File;

import com.mendale.app.R;
import com.mendale.app.pojo.Classifications;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.MainPageActivity;
import com.mendale.app.ui.home.menu.ChooseClassify;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 上传教程-分类
 * 
 * @author zx
 *
 */
public class ClassifyActivity extends BaseActivity implements OnClickListener {

	/** 上传图片 */
	private TextView tvLauchPic;
	/** 图片 */
	private ImageView ivPic;
	/** 分类按钮 */
	private Button btnClassify;
	/** 注意的地方 */
	private EditText etTips;
	
	private String path;
	
	//相册选中图片路径
	private static String iconpath;

	Classifications classification;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classify);
		initHeaderView();
		initView();
		setListener();
	}

	/**
	 * 设置点击事件
	 */
	private void setListener() {
		tvLauchPic.setOnClickListener(this);
		btnClassify.setOnClickListener(this);
	}

	/**
	 * 初始化头部
	 */
	private void initHeaderView() {
		setNavigationTitle("分类");
		setNavigationLeftBtnText("");
		setNavigationRightBtnText("发布");
	}

	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	@Override
	public void rightButtonOnClick() {
		super.rightButtonOnClick();
		submit();
	}

	/**
	 * 创建数据
	 */
	
	private void submit() {
        //上传图片
        final BmobFile file=new BmobFile(new File(path)); //创建BmobFile对象，转换为Bmob对象
        file.upload(ClassifyActivity.this, new UploadFileListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				classification=new Classifications();
				classification.setClassify(btnClassify.getText().toString());
				classification.setCoverage(file);  //设置图片
				classification.setTips(etTips.getText().toString());
				classification.save(ClassifyActivity.this, new SaveListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						showToast("上传成功");
						startActivity(MainPageActivity.class);
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						showToast("上传失败");
					}
				});
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				showToast("上传失败"+arg1);
			}
		});
     }
	
	
	
	/**
	 * 初始化view
	 */
	private void initView() {
		tvLauchPic=(TextView) findViewById(R.id.tv_classify_pic);
		ivPic=(ImageView) findViewById(R.id.iv_classify_pic);
		btnClassify=(Button) findViewById(R.id.btn_classify_choose_classify);
		
		
		etTips=(EditText) findViewById(R.id.et_classify_tip);

	}
	String classify;
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
		classify=intent.getStringExtra("classify");
//		showToast("执行了onNewIntent"+classify);
		getIntent().putExtras(intent);
		if(classify!=null){			
			btnClassify.setText(classify);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_classify_pic:// 上传封面
			addmage(5);
			break;

		case R.id.btn_classify_choose_classify:// 选择分类
			startActivity(ChooseClassify.class);
			break;
		}
	}
	
	/**
	 * 页面控件赋值
	 */
	private void setViewData() {
		ImageOpera opera = ImageOpera.getInstance(this);
		if (iconpath != "") {
			tvLauchPic.setVisibility(View.GONE);
			ivPic.setVisibility(View.VISIBLE);
			ImageOpera.getInstance(this).loadImage(iconpath, ivPic);
		} else {
			tvLauchPic.setVisibility(View.VISIBLE);
			ivPic.setVisibility(View.GONE);
			ivPic.setImageResource(R.drawable.defult_avator);
		}
	}
	/**
	 * 更换头像 弹出选择相册的dialog
	 */
	private void addmage(final int flag) {
		showDigLog("相册", "取消", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.openSysPhone(flag, ClassifyActivity.this);
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
			path = getrealPath(originalUri);
			if (Utils.isEmpty(path)) {
				showToast("图片不存在，请重新选择");
				return;
			}
			switch (requestCode) {
			case 5:
				ImageOpera.getInstance(this).loadImage("file://" + path, ivPic);
				iconpath ="file://" +  path;
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
