package com.mendale.app.ui.record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mendale.app.R;
import com.mendale.app.constants.DataURL;
import com.mendale.app.pojo.HotCoursePoJo;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Bundle;
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
	private List<HotCoursePoJo> mDatas = null;
	private DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_details);
		initData();
		initHeaderView();
		initView();
		initImageOptions();
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
	 * 初始化数据
	 */
	private void initData() {
		doGet(DataURL.Z_NEW_JIAOCHENG);
	}
	/**
	 * 获取数据
	 * @param url
	 */
	private void doGet(final String url) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				OkHttpClient okHttpClient = new OkHttpClient();
				// 创建一个request
				Request request = new Request.Builder().url(url).build();
				Call call = okHttpClient.newCall(request);
				call.enqueue(new Callback() {

					@Override
					public void onResponse(Response arg0) throws IOException {
						parserData(arg0.body().string());
					}

					@Override
					public void onFailure(Request arg0, IOException arg1) {
					}
				});
			}
		}).start();
		
	}
	/**
	 * 解析json数据
	 * 
	 * @param string
	 */
	private void parserData(String string) {
		Gson gson = new Gson();
		if (!Utils.isEmpty(string)) {
			try {
				JSONObject jsonObject = new JSONObject(string);
				JSONArray top100 = jsonObject.getJSONArray("list");
				mDatas = new ArrayList<HotCoursePoJo>();
				for (int i = 0; i < top100.length(); i++) {
					HotCoursePoJo item = gson.fromJson(top100.get(i).toString(), new TypeToken<HotCoursePoJo>() {
					}.getType());
					mDatas.add(item);
				}
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						//绑定数据，更改ui
						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage(mDatas.get(0).getFace_pic(), face_pic, options);
						imageLoader.displayImage(mDatas.get(0).getHost_pic(), host_pic, options);
						
						title.setText(mDatas.get(0).getSubject());
						user_name.setText(mDatas.get(0).getUser_name());
					}
				});
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
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
	}

}
