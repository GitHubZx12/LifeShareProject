package com.mendale.app.ui.home.menu.fragment;

import com.mendale.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class TitleFragment extends Fragment {
    /**
     * 是否填写完毕
     */
    public boolean finished=false;
    /**
     * 教程名称
     */
    private EditText et_title;
    /**
     * 教程简介
     */
    private EditText et_title_descript;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_title, container, false);
        initView();
        isEmpty();
        return view;
    }

    private void isEmpty() {

    }

    private void initView() {
        et_title = (EditText) view.findViewById(R.id.et_title);
        et_title_descript = (EditText) view.findViewById(R.id.et_title_descript);
    }

    /**
     * 是否填写完整
     * @return
     */
    public boolean isFinished() {
        return finished;
    }
}
