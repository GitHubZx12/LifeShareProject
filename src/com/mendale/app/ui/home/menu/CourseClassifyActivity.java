package com.mendale.app.ui.home.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mendale.app.R;
import com.mendale.app.adapters.MyExpandableListAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.constants.Datas;
import com.mendale.app.pojo.CourseChildPojo;
import com.mendale.app.pojo.CoursePoJo;
import com.mendale.app.tasks.CourseTask;
import com.mendale.app.ui.base.BaseActivity;
import com.mendale.app.ui.course.CourseInfoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import android.widget.FrameLayout;

/**
 * 教程分类（侧滑菜单部分）
 * 有bug，待解决
 *
 * @author zx
 */
public class CourseClassifyActivity extends BaseActivity implements OnScrollListener, OnChildClickListener {

    private ExpandableListView listView;
    private MyExpandableListAdapter mAdapter;

    private FrameLayout indicatorGroup;
    private int indicatorGroupId = -1;
    private int indicatorGroupHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_classify);
        intData();
        initHeaderView();
        initView();
    }
    /**
	 * 初始化标题
	 */
	private void initHeaderView() {
		setNavigationTitle("课程分类");
		setNavigationLeftBtnText("");
	}
	@Override
	public void leftButtonOnClick() {
		super.leftButtonOnClick();
		this.finish();
	}

	/**
	 * 初始化数据
	 */
	private void intData() {
		BmobQuery<CoursePoJo>bmobQuery=new BmobQuery<CoursePoJo>();
		bmobQuery.findObjects(this, new FindListener<CoursePoJo>() {
			
			@Override
			public void onSuccess(List<CoursePoJo> arg0) {
				Datas.courseList=arg0;
				formatList(arg0);
				setListener();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Log.e("tag",arg1);
			}
		});
	}

    /**
     * 监听事件
     */
    private void setListener() {
        mAdapter = new MyExpandableListAdapter(this);
        listView.setAdapter(mAdapter);
        listView.setOnScrollListener(this);
        listView.setGroupIndicator(null);
        listView.setOnChildClickListener(this);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        listView = (ExpandableListView) findViewById(R.id.expand_lv_course_classify);
        indicatorGroup = (FrameLayout) findViewById(R.id.fl_course_classify_group);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


        final ExpandableListView listView = (ExpandableListView) view;
        /**
         * calculate point (0,0)
         */
        int npos = view.pointToPosition(0, 0);// 其实就是firstVisibleItem
        if (npos == AdapterView.INVALID_POSITION)// 如果第一个位置值无效
            return;

        long pos = listView.getExpandableListPosition(npos);
        int childPos = ExpandableListView.getPackedPositionChild(pos);// 获取第一行child的id
        int groupPos = ExpandableListView.getPackedPositionGroup(pos);// 获取第一行group的id
        if (childPos == AdapterView.INVALID_POSITION) {// 第一行不是显示child,就是group,此时没必要显示指示器
            View groupView = listView.getChildAt(npos
                    - listView.getFirstVisiblePosition());// 第一行的view
            indicatorGroupHeight = groupView.getHeight();// 获取group的高度
            indicatorGroup.setVisibility(View.GONE);// 隐藏指示器
        } else {
            indicatorGroup.setVisibility(View.VISIBLE);// 滚动到第一行是child，就显示指示器
        }
        // get an error data, so return now
        if (indicatorGroupHeight == 0) {
            return;
        }
        // update the data of indicator group view
        if (groupPos != indicatorGroupId) {// 如果指示器显示的不是当前group
            mAdapter.getGroupView(groupPos, listView.isGroupExpanded(groupPos),
                    indicatorGroup.getChildAt(0), null);// 将指示器更新为当前group
            indicatorGroupId = groupPos;
            Log.e("TAG", "==" + "bind to new group,group position = " + groupPos);
            // mAdapter.hideGroup(indicatorGroupId); // we set this group view
            // to be hided
            // 为此指示器增加点击事件
            indicatorGroup.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    listView.collapseGroup(indicatorGroupId);
                }
            });
        }
        if (indicatorGroupId == -1) // 如果此时grop的id无效，则返回
            return;

        /**
         * calculate point (0,indicatorGroupHeight) 下面是形成往上推出的效果
         */
        int showHeight = indicatorGroupHeight;
        int nEndPos = listView.pointToPosition(0, indicatorGroupHeight);// 第二个item的位置
        if (nEndPos == AdapterView.INVALID_POSITION)//如果无效直接返回
            return;
        long pos2 = listView.getExpandableListPosition(nEndPos);
        int groupPos2 = ExpandableListView.getPackedPositionGroup(pos2);//获取第二个group的id
        if (groupPos2 != indicatorGroupId) {//如果不等于指示器当前的group
            View viewNext = listView.getChildAt(nEndPos
                    - listView.getFirstVisiblePosition());
            showHeight = viewNext.getTop();
            Log.e("TAG", "--" + "update the show part height of indicator group:"
                    + showHeight);
        }

        // update group position
        MarginLayoutParams layoutParams = (MarginLayoutParams) indicatorGroup
                .getLayoutParams();
        layoutParams.topMargin = -(indicatorGroupHeight - showHeight);
        indicatorGroup.setLayoutParams(layoutParams);
    }

	/**
	 * 提取child的数据
	 * 
	 * @param courseList
	 * @return
	 */
	private List<CourseChildPojo> formatList(List<CoursePoJo> courseList) {
		Datas.list = new ArrayList<Map<String, String>>();
		Datas.cChildList=new ArrayList<CourseChildPojo>();
		CourseChildPojo item;
		for(int i=0;i<courseList.size();i++){
			for (int j = 0; j < courseList.get(i).getChild().size(); j++) {
				item=courseList.get(i).getChild().get(j);
				Datas.cChildList.add(item);
			}
		}
		return Datas.cChildList;
	};

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    //子item的点击事件，跳转到详情页
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
    	Intent intent = new Intent(this, CourseInfoActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("courseItem",Datas.courseList.get(groupPosition).getChild().get(childPosition) );
		intent.putExtras(bundle);
		startActivity(intent);
        return false;
    }

}
