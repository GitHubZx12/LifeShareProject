package com.mendale.app.ui.home.menu.upload;

import com.mendale.app.R;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.pojo.Titles;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.ExitApplication;
import com.mendale.app.vo.CourseDetailsBean;
import com.umeng.socialize.utils.Log;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * 1
 * 上传教程的标题
 * @author zx
 *
 */
public class TitleActivity extends BaseActivity {
	
	protected static final String TAG = "TitleActivity";
	public static String objectId;
	/**
	 * 是否填写完毕
	 */
	public boolean finished = false;
	/**
	 * 教程名称
	 */
	private EditText et_title;
	/**
	 * 教程简介
	 */
	private EditText et_title_descript;

	/**
	 * 数据
	 */
	private CourseDetailsBean bean=new CourseDetailsBean();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);
		ExitApplication.getInstance().addActivity(this);
		initHeaderView();
		initView();
	}

	/**
	 * 初始化头部view
	 */
	private void initHeaderView() {
		setNavigationTitle("标题");
		setNavigationLeftBtnText("");
		setNavigationRightBtnImage(R.drawable.crafter_cguide_lastarrow_yes_selected);
		setNavigationLeftBtnImage(R.drawable.crafter_cguide_lastarrow_yes);
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
		isEmpty();
	}
	
	
	/**
	 * 上传
	 */
	public void updateTitleData(){
		final MyUser user=BmobUser.getCurrentUser(this,MyUser.class);
		final Titles title = new Titles();
//		title.setObjectId("1234");
		title.setUser(user);
		title.setTitles_name(et_title.getText().toString());
		title.setTitle_description(et_title_descript.getText().toString());
//		bean.setTitle(title);
//		bean.setAuthor(user);
		title.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				bean.setTitle(title);
				bean.setAuthor(user);
				bean.save(TitleActivity.this, new SaveListener() {
					
					@Override
					public void onSuccess() {
						objectId=bean.getObjectId();
						closeLoadDialog();
						startActivity(MaterialActivity.class);
						TitleActivity.this.finish();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						closeLoadDialog();
						Log.e(TAG,arg0+arg1);
						showToast("添加失败："+arg1);
					}
				});
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e(TAG,arg0+arg1);
			}
		});
			
	}
	
	
	/**
	 * 判断输入的是否为空
	 */
	private void isEmpty() {
		String s = String.valueOf(et_title.getText());
		String s1 = String.valueOf(et_title_descript.getText());
		if (s.equals("") || s1.equals("")) {
			finished = false;
			Toast.makeText(this, "请填写内容", Toast.LENGTH_LONG).show();
		}else{
			showLoadDialog("正在保存信息，请稍后");
			updateTitleData();
		}

	}

	/**
	 * 初始化view
	 */
	private void initView() {
		et_title = (EditText) findViewById(R.id.et_title);
		et_title_descript = (EditText) findViewById(R.id.et_title_descript);
	}

}
