package com.mendale.app.ui.home.menu.upload;

import java.util.ArrayList;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.UpLoadAddMaterialAdaper;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.vo.CourseDetailsBean;
import com.mendale.app.vo.Material;
import com.umeng.socialize.utils.Log;

import android.content.Context;
import android.os.Bundle;
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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 上传教程--材料
 * 
 * @author Administrator
 * 
 */
public class MaterialActivity extends BaseActivity {

	protected static final String TAG = "MaterialActivity";
	private ListView listView;
	private UpLoadAddMaterialAdaper mAdapter;
	/** 添加 */
	private ImageView btn_add;
	private EditText name;
	private EditText amount;
	private List<Material> materialList = new ArrayList<Material>();
	private CourseDetailsBean bean=new CourseDetailsBean();
	
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
		setNavigationTitle("材料");
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
		showLoadDialog("正在保存材料，请等待");
		createData();
	}

	
	/**
	 * 上传
	 */
	private void createData() {
		bean.setMaterial(materialList);
		bean.update(this,TitleActivity.objectId,new UpdateListener() {
			
			@Override
			public void onSuccess() {
				closeLoadDialog();
				startActivity(ToolActivity.class);
				MaterialActivity.this.finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				closeLoadDialog();
				Log.e(TAG,arg0+arg1);
				showToast("添加失败："+arg1);
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
				showPopWindow(MaterialActivity.this, v);
			}
		});
	}

	/**
	 * 增加的popwindow
	 * @param context
	 * @param parent
	 */
	private void showPopWindow(Context context, View parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vPopWindow = inflater.inflate(R.layout.popwindow_add_material_item, null, false);
		final PopupWindow popWindow = new PopupWindow(vPopWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		name = (EditText) vPopWindow.findViewById(R.id.et_additem_name);
		amount = (EditText) vPopWindow.findViewById(R.id.et_additem_amount);
		Button add = (Button) vPopWindow.findViewById(R.id.btn_additem_add);
		Button cacel = (Button) vPopWindow.findViewById(R.id.btn_additem_cacel);
		popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

		add.setOnClickListener(new OnClickListener() {//增加

			@Override
			public void onClick(View v) {
				Material mp=new Material();
				mp.setDesc(amount.getText().toString());
				mp.setName(name.getText().toString());
				materialList.add(mp);
				mAdapter.notifyDataSetChanged();
				
				popWindow.dismiss();
			}
		});
		cacel.setOnClickListener(new OnClickListener() {//取消
			
			@Override
			public void onClick(View v) {
				if(popWindow.isShowing()){
					popWindow.dismiss();
				}
			}
		});
	}


	/**
	 * 初始化
	 */
	private void initView() {
		listView = (ListView) findViewById(R.id.listview_material);
		btn_add = (ImageView) findViewById(R.id.btn_add);
		mAdapter=new UpLoadAddMaterialAdaper(this, materialList);
		listView.setAdapter(mAdapter);
	}
}
