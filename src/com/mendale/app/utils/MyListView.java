package com.mendale.app.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决listview嵌套在scrollview只显示一行的问题
 * 
 * @author ASSU_X
 *
 */
public class MyListView extends ListView{
	public MyListView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 
 
    public MyListView(Context context) { 
        super(context); 
    } 
 
    public MyListView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
    } 
 
    @Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, 
                MeasureSpec.AT_MOST); 
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 

}
