package com.mendale.app.ui.home.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mendale.app.R;
import com.mendale.app.adapters.CourseGvAdapter;
import com.mendale.app.constants.DataURL;
import com.mendale.app.constants.Datas;
import com.mendale.app.pojo.CoursePoJo;
import com.mendale.app.tasks.CourseTask;

/**
 * 教程
 *
 * @author Administrator
 */
public class CourseFragment extends Fragment {
    /**
     * 动态设置gridview的高度
     */
    private CourseGvAdapter mAdapter;
    private GridView gv_Course;
    private ImageView iv_loading;
    private LinearLayout ll_loading;


    private Handler mhandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.obj != null) {
                        iv_loading.clearAnimation();
                        ll_loading.setVisibility(View.INVISIBLE);
                        gv_Course.setVisibility(View.VISIBLE);
                        Datas.courseList = (List<CoursePoJo>) msg.obj;
                        mAdapter = new CourseGvAdapter(getActivity(), formatList(Datas.courseList));
                        gv_Course.setAdapter(mAdapter);
                    }
                    break;

                default:
                    break;
            }
        }


    };

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

    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        intData();
        initView(view);
        initAnim();
        return view;
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.loading);
        iv_loading.startAnimation(animation);
    }

    /**
     * 初始化界面
     */
    private void initView(View view) {
        gv_Course = (GridView) view.findViewById(R.id.gv_course_classify);
        iv_loading = (ImageView) view.findViewById(R.id.iv_loading);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_course_loading);
    }

    /**
     * 初始化数据
     */
    private void intData() {

        new Thread() {
            public void run() {

                new CourseTask(getActivity(), mhandler).send(1, "utf-8", DataURL.COURSE_URL);
            }

            ;
        }.start();
    }
}
