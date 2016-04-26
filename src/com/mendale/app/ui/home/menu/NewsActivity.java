package com.mendale.app.ui.home.menu;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mendale.app.R;
import com.mendale.app.adapters.ViewPageAdapter;
import com.mendale.app.ui.home.menu.fragment.CommentFragment;
import com.mendale.app.ui.home.menu.fragment.PrivateLettersFragment;
import com.mendale.app.ui.home.menu.fragment.ReplyActivity;
import com.mendale.app.utils.ExitApplication;

/**
 * 消息
 * 
 * @author Administrator
 * 
 */
public class NewsActivity extends FragmentActivity implements
		OnClickListener, OnCheckedChangeListener, OnPageChangeListener {

	private RadioGroup rg_message;
	private RadioButton rb_message_comment;
	private RadioButton rb_message_reply;
	private RadioButton rb_message_private_letter;
	private ViewPager viewpage;
	private ImageView iv_back;
	private TextView tv_title;
	private List<Fragment> fragmentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_news);
		ExitApplication.getInstance().addActivity(this);
		initView();
		initViewPage();
		setListener();
	}

	/**
	 * 初始化ViewPage
	 */
	private void initViewPage() {
		fragmentList = new ArrayList<Fragment>();
		CommentFragment commentFragment = new CommentFragment();
		ReplyActivity replyFragment = new ReplyActivity();
		PrivateLettersFragment pLettersFragment = new PrivateLettersFragment();
		fragmentList.add(commentFragment);
//		fragmentList.add(replyFragment);
		fragmentList.add(pLettersFragment);

		changTextColr(rb_message_comment, "评论");
		viewpage.setAdapter(new ViewPageAdapter(getSupportFragmentManager(),
				fragmentList));
	}

	/**
	 * 事件
	 */
	private void setListener() {
		iv_back.setOnClickListener(this);
		rg_message.setOnCheckedChangeListener(this);
		viewpage.setOnPageChangeListener(this);
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		rg_message = (RadioGroup) findViewById(R.id.radiogroup);
		rb_message_comment = (RadioButton) findViewById(R.id.rb_message_comment);
		rb_message_reply = (RadioButton) findViewById(R.id.rb_message_reply);
		rb_message_private_letter = (RadioButton) findViewById(R.id.rb_message_private_letter);
		viewpage = (ViewPager) findViewById(R.id.viewpage);
		iv_back = (ImageView) findViewById(R.id.iv_message_back);
		tv_title = (TextView) findViewById(R.id.tv_message_title);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		resertTextColor();
		switch (checkedId) {
		case R.id.rb_message_comment:// 评论
			changTextColr(rb_message_comment, "评论");
			viewpage.setCurrentItem(0);
			break;
			
		case R.id.rb_message_private_letter:// 私信
			changTextColr(rb_message_private_letter, "私信");
			viewpage.setCurrentItem(2);
			break;
		case R.id.rb_message_reply:// 回复
			changTextColr(rb_message_reply, "回复");
			viewpage.setCurrentItem(1);
			break;

		default:
			break;
		}

	}

	/**
	 * 修改选中后的字体的颜色
	 * 
	 * @param rb_message_comment2
	 */
	private void changTextColr(RadioButton rb, String str) {
		rb.setTextColor(Color.GREEN);
		tv_title.setText(str);
	}

	/**
	 * 标题恢复到默认颜色
	 */
	private void resertTextColor() {
		rb_message_comment.setTextColor(Color.WHITE);
		rb_message_private_letter.setTextColor(Color.WHITE);
		rb_message_reply.setTextColor(Color.WHITE);
	}

	@Override
	public void onClick(View v) {
		this.finish();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int pos) {
		resertTextColor();
		resertRadioBtnChecked();
		switch (pos) {
		case 0:
			rb_message_comment.setChecked(true);
			changTextColr(rb_message_comment, "评论");
			break;

		case 1:
			rb_message_reply.setChecked(true);
			changTextColr(rb_message_reply, "回复");
			break;
		case 2:
			rb_message_private_letter.setChecked(true);
			changTextColr(rb_message_private_letter, "私信");
			break;
		}

	}
	
	/**
	 * 头部导航，的选中状态
	 */
	private void resertRadioBtnChecked() {
		rb_message_comment.setChecked(false);
		rb_message_reply.setChecked(false);
		rb_message_private_letter.setChecked(false);

	}

}
