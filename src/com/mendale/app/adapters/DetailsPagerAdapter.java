package com.mendale.app.adapters;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class DetailsPagerAdapter extends PagerAdapter{

	// 布局集合
	private List<View> mViewList;
	private int step;
	
	public DetailsPagerAdapter(List<View> mViewList,int step){
		this.mViewList=mViewList;
		this.step=step;
	}
	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(mViewList.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(mViewList.get(position));
		return mViewList.get(position);
	}

	@Override
	public int getCount() {
		return step;
	}
	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
