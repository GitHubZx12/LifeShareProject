package com.mendale.app.ui.home.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mendale.app.R;
import com.mendale.app.ui.home.menu.fragment.ClassifyFragment;
import com.mendale.app.ui.home.menu.fragment.MaterialFragment;
import com.mendale.app.ui.home.menu.fragment.StepFragment;
import com.mendale.app.ui.home.menu.fragment.TitleFragment;
import com.mendale.app.ui.home.menu.fragment.ToolFragment;
import com.mendale.app.utils.ExitApplication;

/**
 * 上传教程
 *
 * @author Administrator
 */
public class UpLoadCourseActivity extends FragmentActivity implements
        OnClickListener {

    private LinearLayout ll_title;
    private LinearLayout ll_material;
    private LinearLayout ll_tool;
    private LinearLayout ll_step;
    private LinearLayout ll_classify;
    private ImageView iv_title;
    private ImageView iv_material;
    private ImageView iv_tool;
    private ImageView iv_step;
    private ImageView iv_classify;
    private TextView tv_title;
    private TextView tv_material;
    private TextView tv_tool;
    private TextView tv_step;
    private TextView tv_classify;
    /**
     * Fragment Manager
     */
    FragmentManager fm;
    FragmentTransaction transaction;
    TitleFragment titleFragment;
    MaterialFragment materialFragment;
    ToolFragment toolFragment;
    StepFragment stepFragment;
    ClassifyFragment classifyFragment;

    boolean finished;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_upload_course);
        ExitApplication.getInstance().addAllActivity(this);// 加入全局退出队列
        initView();
        initFragment();
        addListener();
    }

    /**
     * 設置监听事件
     */
    private void addListener() {
        ll_classify.setOnClickListener(this);
        ll_material.setOnClickListener(this);
        ll_step.setOnClickListener(this);
        ll_title.setOnClickListener(this);
        ll_tool.setOnClickListener(this);
    }

    /**
     * 初始化Fragment TitleFragment titleFragment; MaterialFragment
     * materialFragment; ToolFragment toolFragment; StepFragment stepFragment;
     * ClassifyFragment classifyFragment;
     */
    private void initFragment() {

        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        if (titleFragment == null) {
            titleFragment = new TitleFragment();
        }
        if (materialFragment == null) {
            materialFragment = new MaterialFragment();
        }
        if (toolFragment == null) {
            toolFragment = new ToolFragment();
        }
        if (stepFragment == null) {
            stepFragment = new StepFragment();
        }
        if (classifyFragment == null) {
            classifyFragment = new ClassifyFragment();
        }
        changeFragment(titleFragment, true,finished);
    }

    /**
     * 切换Fragmentment
     *
     * @param fragment
     * @param isInit
     */
    private void changeFragment(Fragment fragment, boolean isInit,boolean isFinished) {
        if (!fragment.isAdded()) {
            transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_upload, fragment);
            if (!isInit) {
                transaction.addToBackStack(null);
            }


            transaction.commit();
        }
    }

    /**
     * 初始化界面
     */
    private void initView() {
        ll_classify = (LinearLayout) findViewById(R.id.ll_classify);
        ll_material = (LinearLayout) findViewById(R.id.ll_material);
        ll_step = (LinearLayout) findViewById(R.id.ll_step);
        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        ll_tool = (LinearLayout) findViewById(R.id.ll_tool);

        tv_classify = (TextView) findViewById(R.id.tv_classify_txt);
        tv_material = (TextView) findViewById(R.id.tv_material_txt);
        tv_step = (TextView) findViewById(R.id.tv_step_txt);
        tv_title = (TextView) findViewById(R.id.tv_title_txt);
        tv_tool = (TextView) findViewById(R.id.tv_tool_txt);

        iv_classify = (ImageView) findViewById(R.id.iv_classify_img);
        iv_material = (ImageView) findViewById(R.id.iv_material_img);
        iv_step = (ImageView) findViewById(R.id.iv_step_img);
        iv_title = (ImageView) findViewById(R.id.iv_title_img);
        iv_tool = (ImageView) findViewById(R.id.iv_tool_img);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title:// 标题
                tv_title.setTextColor(Color.WHITE);
                iv_title.setImageResource(R.drawable.crafter_cguide_stepmark_select);
                changeFragment(titleFragment, true,finished);
                break;
            case R.id.ll_material:// 材料
                tv_material.setTextColor(Color.WHITE);
                changeFragment(materialFragment, true,finished);
                break;
            case R.id.ll_tool:// 工具
                tv_tool.setTextColor(Color.WHITE);
                changeFragment(toolFragment, true,finished);
                break;
            case R.id.ll_step:// 步骤
                tv_step.setTextColor(Color.WHITE);
                changeFragment(stepFragment, true,finished);
                break;
            case R.id.ll_classify:// 分类
                tv_classify.setTextColor(Color.WHITE);
                changeFragment(classifyFragment, true,finished);
                break;

        }

    }
}
