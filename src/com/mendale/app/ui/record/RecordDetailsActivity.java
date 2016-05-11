package com.mendale.app.ui.record;

import com.mendale.app.R;
import com.mendale.app.pojo.RecordItemBean;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.home.ShowDetailsActivity;
import com.mendale.app.ui.home.menu.CommentActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 记录详情
 * @author zx
 *
 */
public class RecordDetailsActivity extends BaseActivity{
	
	/**记录的图片*/
	private ImageView host_pic;
	/**用户头像*/
	private ImageView face_pic;
	/**记录的标题*/
	private TextView title;
	/**记录的描述*/
	private TextView desc;
	/**用户昵称*/
	private TextView user_name;
	/**评论*/
	private Button comment;
	/**数据*/
	private RecordItemBean item;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_details);
		getIntentData();
		initHeaderView();
		initView();
		initImageOptions();
		BindData();
	}
	
	/**
	 * 初始化intent
	 */
	private void getIntentData() {
		item=(RecordItemBean) getIntent().getSerializableExtra("recordItem");
	}

	/**
	 * 設置数据
	 */
	private void BindData() {
		//绑定数据，更改ui
		ImageLoader imageLoader = ImageLoader.getInstance();
		if(item.getHost_pic()!=null||"".equals(item.getHost_pic())){
			imageLoader.displayImage(item.getHost_pic(), host_pic, options);
			imageLoader.displayImage(item.getFace_pic(), face_pic, options);
		}
		title.setText(item.getSubject());
		user_name.setText(item.getUser_name());
	}

	/**
	 * 初始化图片的相关参数
	 */
	private void initImageOptions() {
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.image_guidestep_defult) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.image_guidestep_defult) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.image_guidestep_defult) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象
	}
	
	/**
	 * 初始化标题
	 */
	private void initHeaderView() {
		setNavigationTitle("记录详情");
		setNavigationLeftBtnText("");
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	/**
	 *初始化view
	 */
	private void initView() {
		face_pic=(ImageView) findViewById(R.id.iv_record_details_face_pic);
		host_pic=(ImageView) findViewById(R.id.iv_record_details_pic);
		title=(TextView) findViewById(R.id.tv_record_details_title);
		desc=(TextView) findViewById(R.id.tv_record_details_desc);
		user_name=(TextView) findViewById(R.id.tv_record_details_face_name);
		comment=(Button) findViewById(R.id.btn_record_details_comment);
		
		comment.setOnClickListener(new OnClickListener() {//评论
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RecordDetailsActivity.this, RecordCommentActivity.class);
				intent.putExtra("objectId", item.getObjectId());
				startActivity(intent);
			}
		});
	}

}
