package com.mendale.app.ui.home.menu;

import com.mendale.app.R;
import com.mendale.app.adapters.CourseGvAdapter;
import com.mendale.app.adapters.MyExpandableListAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.constants.Datas;
import com.mendale.app.pojo.CoursePoJo;
import com.mendale.app.tasks.CourseTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教程分类（侧滑菜单部分）
 * 有bug，待解决
 *
 * @author zx
 */
public class CourseClassifyActivity extends Activity implements OnScrollListener, OnChildClickListener {

    private ImageView back;
    private ExpandableListView listView;
    private MyExpandableListAdapter mAdapter;

    private FrameLayout indicatorGroup;
    private int indicatorGroupId = -1;
    private int indicatorGroupHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_classify);
        intData();
        initView();
        setListener();
    }

    /**
     * 临时
     * 获取数据
     */
    private Handler mhandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.obj != null) {
                        Datas.courseList = (List<CoursePoJo>) msg.obj;
                        List<Map<String, String>> courseList = formatList(Datas.courseList);
                    }
                    break;
                default:
                    break;
            }
        }


    };

    /**
     * 初始化数据
     */
    private void intData() {

        new Thread() {
            public void run() {
                new CourseTask(CourseClassifyActivity.this, mhandler).send(1, "utf-8", DataURL.COURSE_URL);
            }
        }.start();
    }

    /**
     * 提取child的数据
     *
     * @param courseList
     * @return
     */
    private List<Map<String, String>> formatList(List<CoursePoJo> courseList) {
//		List<Map<String,String>>list=new ArrayList<Map<String,String>>();
        Datas.list = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        for (int i = 0; i < courseList.size(); i++) {
            for (int j = 0; j < courseList.get(i).getChild().size(); j++) {
                map = new HashMap<String, String>();
                map.put("childName", courseList.get(i).getChild().get(j).getName());
                Datas.list.add(map);
            }
        }
        return Datas.list;
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
        back.setOnClickListener(new OnClickListener() {//返回

            @Override
            public void onClick(View v) {
                CourseClassifyActivity.this.finish();
            }
        });

    }

    /**
     * 初始化界面
     */
    private void initView() {
        listView = (ExpandableListView) findViewById(R.id.expand_lv_course_classify);
        indicatorGroup = (FrameLayout) findViewById(R.id.fl_course_classify_group);
        back = (ImageView) findViewById(R.id.iv_classify_back);

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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    //子item的点击事件，跳转到详情页
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Toast.makeText(this, "pos==" + childPosition, Toast.LENGTH_LONG).show();
        return false;
    }

}
