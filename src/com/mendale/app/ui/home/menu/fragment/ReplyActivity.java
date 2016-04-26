package com.mendale.app.ui.home.menu.fragment;

import java.util.ArrayList;
import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.UpLoadAddMaterialAdaper;
import com.mendale.app.adapters.UpLoadAddReplayLVAdapter;
import com.mendale.app.pojo.MaterialPoJo;
import com.mendale.app.ui.base.BaseActivity;

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
import android.widget.TextView;
/**
 * 上传教程--步骤
 * @author zx
 *
 */
public class ReplyActivity extends BaseActivity{
	
	private ListView listView;
	/** 添加 */
	private ImageView btn_add;
	private List<MaterialPoJo> text = new ArrayList<MaterialPoJo>();
	private UpLoadAddReplayLVAdapter mAdapter;
	
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
	}

	/**
	 * 下一步
	 */
	@Override
	public void rightImageButtonOnClick() {
		super.rightImageButtonOnClick();
		// TODO 保存到数据库中
	}

	/**
	 * 点击事件
	 */
	private void addListener() {
		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// 增加
				showPopWindow(ReplyActivity.this, v);
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
		View vPopWindow = inflater.inflate(R.layout.popwindow_add_replay_item, null, false);
		final PopupWindow popWindow = new PopupWindow(vPopWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		final TextView tvPic = (TextView) vPopWindow.findViewById(R.id.tv_additem_pic);//添加图片
		final ImageView etPic = (ImageView) vPopWindow.findViewById(R.id.iv_additem_pic);//添加的图片
		final EditText desc = (EditText) vPopWindow.findViewById(R.id.et_additem_desc);//步骤描述
		Button add = (Button) vPopWindow.findViewById(R.id.btn_additem_add);
		Button cacel = (Button) vPopWindow.findViewById(R.id.btn_additem_cacel);
		popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
		
		tvPic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});

		add.setOnClickListener(new OnClickListener() {//增加

			@Override
			public void onClick(View v) {
				MaterialPoJo mp=new MaterialPoJo();
				mp.setDesc(desc.getText().toString());
				mp.setFlag(false);
				text.add(mp);
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
		mAdapter=new UpLoadAddReplayLVAdapter(this, text);
		listView.setAdapter(mAdapter);
	}

}
