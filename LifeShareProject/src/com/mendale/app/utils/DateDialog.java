package com.mendale.app.utils;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mendale.app.R;

/**
 * 日期选择控件
 * @author wenjian
 *
 */
@SuppressLint("InflateParams")
public class DateDialog {
	private Dialog mDialog;
	private Context mContext;

	public DateDialog(Context context) {
		mContext = context;
	}

	private int year;
	private int month;
	private int date;

	/**
	 * 显示选择日期的对话框
	 * 
	 * @param dateText
	 */
	public void showDateDialog(final TextView dateText,
			OnClickListener listener, OnClickListener clearListener) {
		View diaView = LayoutInflater.from(mContext).inflate(
				R.layout.date_dialog, null);
		mDialog = new Dialog(mContext, R.style.dialog_default);
		mDialog.setContentView(diaView);
		Button mConfirmButton = (Button) diaView.findViewById(R.id.btn_confirm);
		Button mCancelButton = (Button) diaView.findViewById(R.id.btn_cancel);
		Button mClearButton = (Button) diaView.findViewById(R.id.btn_clear);

		yearView = (WheelView) diaView.findViewById(R.id.year);
		monthView = (WheelView) diaView.findViewById(R.id.month);
		dateView = (WheelView) diaView.findViewById(R.id.date);

		yearView.setAdapter(new NumericWheelAdapter(1900, 2099));
		yearView.setLabel("年");
		yearView.setCyclic(true);

		monthView.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
		monthView.setLabel("月");
		monthView.setCyclic(true);

		dateView.setAdapter(new NumericWheelAdapter(1, 31, "%02d"));//modified by ibm_wenjian
		dateView.setLabel("日");
		dateView.setCyclic(true);

		Calendar c = Calendar.getInstance();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH);
		int curDate = c.get(Calendar.DATE);

		// 设置当天的年月日信息
		yearView.setCurrentItem(curYear - 1900);
		monthView.setCurrentItem(curMonth);
		dateView.setCurrentItem(curDate-1);

		setDateWheel(curYear, monthView.getCurrentItem()+1, curDate);//modified by ibm_wenjian 解决日期无法初始31日
		// 添加年与月的侦听事件
		addChangingListener(yearView, "年");
		addChangingListener(monthView, "月");
		addChangingListener(dateView, "日");

		// 点击确定按钮事件
		if (listener == null) {
			mConfirmButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					year = yearView.getCurrentItem() + 1900;
					month = monthView.getCurrentItem() + 1;
					date = dateView.getCurrentItem() + 1;

					String m = month > 9 ? "" + month : "0" + month;
					String d = date > 9 ? "" + date : "0" + date;
					String s = year + "-" + m + "-" + d;

					dateText.setText(s);
					dismiss();
				}
			});
		} else {
			mConfirmButton.setOnClickListener(listener);
		}

		mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		if (clearListener == null) {
			mClearButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dateText.setText("");
					dismiss();
				}
			});
		} else {
			mClearButton.setOnClickListener(clearListener);
		}

		mDialog.show();
	}

	private WheelView yearView; // 年
	private WheelView monthView; // 月
	private WheelView dateView; // 日

	/**
	 * 当选择发生变化的侦听器
	 * 
	 * @param wheel
	 * @param label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				setDateWheel(yearView.getCurrentItem() + 1900,
						monthView.getCurrentItem() + 1,
						dateView.getCurrentItem() + 1);
			}
		});
	}

	/**
	 * 根据年、月设置该显示多少天
	 * 
	 * @param y
	 * @param m
	 * @param d
	 */
	private void setDateWheel(int y, int m, int d) {
		switch (m) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			dateView.setAdapter(new NumericWheelAdapter(1, 31, "%02d"));
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (d == 31) {
				dateView.setCurrentItem(29);
			}
			dateView.setAdapter(new NumericWheelAdapter(1, 30, "%02d"));
			break;
		case 2:
			if (d > 28) {
				if (isLeapYear(y)) {
					dateView.setCurrentItem(28);
				} else {
					dateView.setCurrentItem(27);
				}
			}

			if (isLeapYear(y)) {
				dateView.setAdapter(new NumericWheelAdapter(1, 29, "%02d"));
			} else {
				dateView.setAdapter(new NumericWheelAdapter(1, 28, "%02d"));
			}
			break;

		default:

			break;
		}
	}

	/**
	 * 判断是否闰年
	 * 
	 * @param year
	 * @return
	 */
	private boolean isLeapYear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return true;
		}
		return false;
	}

	/**
	 * yyyy-MM-dd
	 * @return
	 */
	public String getDate() {

		year = yearView.getCurrentItem() + 1900;
		month = monthView.getCurrentItem() + 1;
		date = dateView.getCurrentItem() + 1;

		String m = month > 9 ? "" + month : "0" + month;
		String d = date > 9 ? "" + date : "0" + date;
		String s = year + "-" + m + "-" + d;

		return s;
	}
	
	/**
	 * yyyyMM
	 * @return
	 */
	public String getDate2() {

		year = yearView.getCurrentItem() + 1900;
		month = monthView.getCurrentItem() + 1;
		date = dateView.getCurrentItem() + 1;

		String m = month > 9 ? "" + month : "0" + month;
		String s = year +  m;

		return s;
	}

	/***
	 * 销毁对话框
	 */
	public void dismiss() {
		mDialog.dismiss();
	}
	
}
