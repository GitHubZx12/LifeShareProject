package com.mendale.app.ui.record;

import java.util.List;

import com.mendale.app.R;
import com.mendale.app.adapters.RecordCommentLvAdapter;
import com.mendale.app.pojo.Comment;
import com.mendale.app.pojo.MyUser;
import com.mendale.app.pojo.Record;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.utils.pullToRefreshUtils.view.XListView;
import com.umeng.socialize.utils.Log;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 评论页面
 * @author zx
 *
 */
public class RecordCommentActivity extends BaseActivity implements OnClickListener{

	protected static final String TAG = "RecordCommentActivity";
	private LinearLayout ll_tips;
	private TextView tv_tips;
	private ImageView iv_loading;
	private XListView mListView;
	private RecordCommentLvAdapter mAdapter;
	private EditText etCommentTxt;
	private Button btnCommentSend;
	private String objectId;
	/**评论列表*/
	private List<Comment> commentList;
	

	@SuppressLint("HandlerLeak")
	private Handler mhandler=new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			if(msg.obj!=null){
				commentList=(List<Comment>) msg.obj;
				mAdapter=new RecordCommentLvAdapter(RecordCommentActivity.this, commentList);
				mListView.setAdapter(mAdapter);
			}
		};
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_charts);
		initHeaderView();
		initView();
		initData();
	}

	/**
	 * 初始化数据
	 * 查询出某个记录（objectId为）的所有评论，同时将该评论的坐着的信息也查询出来
	 */
	private void initData() {
		BmobQuery<Comment>query=new BmobQuery<Comment>();
		//用此方法可以构造一个BmobPointer对象。只需要设计objectiId就行
		Record record=new Record();
		record.setObjectId(objectId);
		query.addWhereEqualTo("record", new BmobPointer(record));
		//希望同时查询该评论的发布者的信息，以及该记录的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
		query.include("user,record.author");
		query.findObjects(this, new FindListener<Comment>() {
			
			@Override
			public void onSuccess(List<Comment> arg0) {
				Message message=new Message();
				message.obj=arg0;
				mhandler.sendMessage(message);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.e(TAG,arg0+arg1);
			}
		});
		
		
		
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		mListView=(XListView) findViewById(R.id.listview_comments);
		ll_tips=(LinearLayout) findViewById(R.id.ll_comments_loading);
		iv_loading=(ImageView) findViewById(R.id.iv_comments_loading);
		tv_tips=(TextView) findViewById(R.id.tv_comments_tips);
		etCommentTxt=(EditText) findViewById(R.id.et_comment);
		btnCommentSend=(Button) findViewById(R.id.btn_comment_send);
		mListView.setPullRefreshEnable(false);// 设置是否可以下拉刷新
		mListView.setPullLoadEnable(false);// 设置是否可以上拉加载
		btnCommentSend.setOnClickListener(this);
		
		getObjectId();
	}

	/**
	 * 得到要评论的记录的objectId
	 */
	private void getObjectId() {
		objectId=getIntent().getStringExtra("objectId");
		
	}

	/**
	 * 初始化标题
	 */
	private void initHeaderView() {
		setNavigationTitle("评论列表");
		setNavigationLeftBtnText("");
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	/**r
	 * 添加一对多关联
	 * 将评论和记录进行关联，并同时和当前用户进行关联表明是当前用户对改记录进行评论
	 */
	@Override
	public void onClick(View v) {
		MyUser user=BmobUser.getCurrentUser(this, MyUser.class);
		Record record=new Record();
		record.setObjectId(objectId);
		Comment comment=new Comment();
		comment.setContent(etCommentTxt.getText().toString());
		comment.setRecord(record);
		comment.setUser(user);
		comment.save(this,new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Log.e(TAG,arg0+arg1);
			}
		});
		
	}
	
}
