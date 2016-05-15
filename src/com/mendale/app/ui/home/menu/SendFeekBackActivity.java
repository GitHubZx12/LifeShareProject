package com.mendale.app.ui.home.menu;

import com.mendale.app.R;
import com.mendale.app.pojo.Feedback;
import com.mendale.app.ui.base.BaseActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SaveListener;

public class SendFeekBackActivity extends BaseActivity implements OnClickListener {
	
	private EditText et_content;
	private static String msg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendfeedback);
		setNavigationTitle("反馈信息");
		setNavigationLeftBtnText("");
		
		et_content = (EditText) findViewById(R.id.et_content);
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	@Override
	public void onClick(View v) {
		String content = et_content.getText().toString();
		if(!TextUtils.isEmpty(content)){
			if(content.equals(msg)){
				Toast.makeText(this, "请勿重复提交反馈", Toast.LENGTH_SHORT).show();
			}else {
				msg = content;
				// 发送反馈信息
				sendMessage(content);
				Toast.makeText(this, "您的反馈信息已发送", Toast.LENGTH_SHORT).show();
				SendFeekBackActivity.this.finish();
			}
		}else {
			Toast.makeText(this, "请填写反馈内容", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 发送反馈信息给开发者
	 * @param message 反馈信息
	 */
	private void sendMessage(String message){
		BmobPushManager bmobPush = new BmobPushManager(this);
		BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
		query.addWhereEqualTo("isDeveloper", true);
		bmobPush.setQuery(query);
		bmobPush.pushMessage(message);
		
		saveFeedbackMsg(message);
	}
	
	/**
	 * 保存反馈信息到服务器
	 * @param msg 反馈信息
	 */
	private void saveFeedbackMsg(String msg){
		Feedback feedback = new Feedback();
		feedback.setContent(msg);
		feedback.save(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				Log.i("bmob", "反馈信息已保存到服务器");
			}
			
			@Override
			public void onFailure(int code, String arg0) {
				Log.e("bmob", "保存反馈信息失败："+arg0);
			}
		});
	}

}
