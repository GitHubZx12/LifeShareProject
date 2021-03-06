package com.mendale.app.ui.home.menu.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.UpLoadAddStepLVAdapter;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.Utils;
import com.mendale.app.utils.imageUtils.ImageOpera;
import com.mendale.app.vo.CourseDetailsBean;
import com.mendale.app.vo.Step;
import com.umeng.socialize.utils.Log;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 上传教程--步骤
 * 
 * @author zx
 *
 */
public class StepActivity extends BaseActivity {
	
	protected static final String TAG = "StepActivity";

	private ListView listView;
	/** 添加 */
	private ImageView btn_add;
	private UpLoadAddStepLVAdapter mAdapter;
	private static String iconpath;
	private ImageView ivPic = null;
	private TextView tvPic;
	private EditText desc;
	private String path ;
	private List<Step>stepList=new ArrayList<Step>();
	private Step step;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_material);
		initHeadView();
		initView();
		addListener();
	}

	/**
	 * 初始化頭部
	 */
	private void initHeadView() {
		setNavigationTitle("步驟");
		setNavigationRightBtnImage(R.drawable.crafter_cguide_lastarrow_yes_selected);
		setNavigationLeftBtnText("");
	}

	/**
	 * 返回
	 */
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	/**
	 * 下一步
	 */
	@Override
	public void rightImageButtonOnClick() {
		super.rightImageButtonOnClick();
		showLoadDialog("正在保存");
		submit();
	}

	/**
	 * 保存數據
	 */
	private void submit() {
		CourseDetailsBean bean=new CourseDetailsBean();
		bean.setStep(stepList);
        bean.update(this, TitleActivity.objectId,new UpdateListener() {
			
			@Override
			public void onSuccess() {
				closeLoadDialog();
				startActivity(ClassifyActivity.class);
				StepActivity.this.finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				closeDialog();
				showToast("添加失败："+arg1);
				Log.e(TAG,arg0+arg1);
			}
		});
     }
	
	
	/**
	 * 点击事件
	 */
	private void addListener() {
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 增加
				showPopWindow(StepActivity.this, v);
			}
		});
	}

	/**
	 * 增加的popwindow
	 * 
	 * @param context
	 * @param parent
	 */
	private void showPopWindow(Context context, View parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vPopWindow = inflater.inflate(R.layout.popwindow_add_replay_item, null, false);
		final PopupWindow popWindow = new PopupWindow(vPopWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
				true);
		tvPic = (TextView) vPopWindow.findViewById(R.id.tv_additem_pic);// 添加图片
		ivPic = (ImageView) vPopWindow.findViewById(R.id.iv_additem_pic);// 添加的图片
		desc = (EditText) vPopWindow.findViewById(R.id.et_additem_desc);// 步骤描述
		Button add = (Button) vPopWindow.findViewById(R.id.btn_additem_add);
		Button cacel = (Button) vPopWindow.findViewById(R.id.btn_additem_cacel);
		popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

		tvPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				addmage(5);
			}
		});

		add.setOnClickListener(new OnClickListener() {// 增加

			@Override
			public void onClick(View v) {
				final BmobFile file=new BmobFile(new File(path));
				file.upload(StepActivity.this,new UploadFileListener() {
					
					@Override
					public void onSuccess() {
						step = new Step();
						step.setUrl(file.getFileUrl(StepActivity.this));
						step.setContent(desc.getText().toString());
						step.setImg(file);
						stepList.add(step);
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						Log.e(TAG,arg0+arg1);
					}
				});
				mAdapter.notifyDataSetChanged();
				popWindow.dismiss();
			}
		});
		cacel.setOnClickListener(new OnClickListener() {// 取消

			@Override
			public void onClick(View v) {
				if (popWindow.isShowing()) {
					popWindow.dismiss();
				}
			}
		});

	}

	/**
	 * 页面控件赋值
	 */
	private void setViewData() {
		ImageOpera opera = ImageOpera.getInstance(this);
		if (iconpath != "") {
			tvPic.setVisibility(View.GONE);
			ivPic.setVisibility(View.VISIBLE);
			ImageOpera.getInstance(this).loadImage(iconpath, ivPic);
		} else {
			tvPic.setVisibility(View.VISIBLE);
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
				Utils.openSysPhone(flag, StepActivity.this);
				closeDialog();
			}
		}, new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeDialog();
			}
		});
	}

	/**
	 * 初始化
	 */
	private void initView() {
		listView = (ListView) findViewById(R.id.listview_material);
		btn_add = (ImageView) findViewById(R.id.btn_add);
		mAdapter = new UpLoadAddStepLVAdapter(this, stepList);
		listView.setAdapter(mAdapter);
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
				ImageOpera.getInstance(this).loadImage("file://" + path, ivPic);
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
