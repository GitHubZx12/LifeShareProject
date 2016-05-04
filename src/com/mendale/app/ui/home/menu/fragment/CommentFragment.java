package com.mendale.app.ui.home.menu.fragment;

import com.mendale.app.R;
import com.umeng.socialize.bean.MultiStatus;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.UMComment;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.MulStatusListener;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CommentFragment extends Fragment {

	final UMSocialService controller = UMServiceFactory
			.getUMSocialService("com.umeng.share");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(
				R.layout.fragment_charts, container,
				false);
		final Context mContext = getActivity();
		controller.openComment(mContext, false);

		// 评论底层接口 如果没登录将匿名评论。
//		final UMComment socializeComment = new UMComment();
//		socializeComment.mText = "";
//		// check login
//		if (LoginInfoHelp.isPlatformLogin(getActivity())) {
//			sentComment(mContext, controller, socializeComment);
//		} else {// doLogin first
//			controller.showLoginDialog(mContext,
//					new LoginListener() {
//
//						@Override
//						public void loginSuccessed(
//								SHARE_MEDIA platform,
//								boolean noConnect) {
//							super.loginSuccessed(platform,
//									noConnect);
//							sentComment(mContext, controller,
//									socializeComment);
//						}
//
//					});
//		}
		// 获取评论列表底层接口
//		final List<UMComment> mComments = new ArrayList<UMComment>();
//		root.findViewById(R.id.interface_getComments).setOnClickListener(
//				new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						controller.getComments(getActivity(),
//								new FetchCommetsListener() {
//									@Override
//									public void onStart() {
//									}
//
//									@Override
//									public void onComplete(int status,
//											List<UMComment> comments,
//											SocializeEntity entity) {
//										if (status == 200 && comments != null) {
//											mComments.addAll(comments);
//
//											Toast.makeText(
//													mContext,
//													"已获取评论 count="
//															+ comments.size(),
//													1).show();
//										}
//									}
//								}, -1);// 翻页最有一个参数传入已获取的最后一条评论的时间（SocializeComment.dt）
//					}
//				});
//
		return root;
//	}
//
//	protected void sendComment(UMSocialService controller) {
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		UMSsoHandler ssoHandler = controller.getConfig().getSsoHandler(
//				requestCode);
//
//		if (ssoHandler != null) {
//			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//			Log.d("", "#### ssoHandler.authorizeCallBack");
//		}
	}

	private void sentComment(final Context mContext, UMSocialService controller2, UMComment socializeComment) {
		controller.postComment(mContext, socializeComment,
				new MulStatusListener() {
					@Override
					public void onStart() {

					}

					@Override
					public void onComplete(
							MultiStatus multiStatus,
							int status, SocializeEntity entity) {
						if (status == 200)
							Toast.makeText(mContext, "发送成功", 1)
									.show();
						else
							Toast.makeText(mContext, "发送失败", 1)
									.show();

					}
				});
		
	}
}
