package com.ifeng_tech.spotmall.jiandanrili;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
	/**
	   * 根据 年、月 获取对应的月份 的 天数
	   */
    public static int getMonthDays(int year, int month) {

		 Calendar a = Calendar.getInstance();
		 a.set(Calendar.YEAR, year);
		 a.set(Calendar.MONTH, month - 1);
		 a.set(Calendar.DATE, 1);
		 a.roll(Calendar.DATE, -1);
		 int maxDate = a.get(Calendar.DATE);
		 return maxDate;
	 }
    /**
     * 返回当前月份1号位于周几
	 *
     * 传入系统获取的，不需要正常的
     * @return
     * 	一：1		二：2		三：3		四：4		五：5		六：6    日：7
     */

	public static int getFirstDayWeek(int year, int month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		Log.d("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int getWeek(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			return 7;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			return 1;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			return 2;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			return 3;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			return 4;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			return 5;
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			return 6;
		}

		return 0;
	}

}
