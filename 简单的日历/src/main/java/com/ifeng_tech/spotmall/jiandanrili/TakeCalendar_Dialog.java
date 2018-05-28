package com.ifeng_tech.spotmall.jiandanrili;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzt on 2018/5/28.
 */

public class TakeCalendar_Dialog extends Dialog {

    Context context;
    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_today;
    private TextView date_text;
    private TextView week_text;
    private LinearLayout date_operator_ll;
    private WeekDayView weekDayView;
    private MonthDateView monthDateView;

    public TakeCalendar_Dialog(@NonNull Context context) {
        super(context);
        this.context=context;
        this.show();
    }

    public TakeCalendar_Dialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context=context;
        this.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takecalendar_dialog);
        initData();
        setOnlistener();
    }

    private void setOnlistener(){

        // 点击日历表的监听事件
        monthDateView.setDateClick(new MonthDateView.DateClick() {

            @Override
            public void onClickOnDate() {
                Toast.makeText(context, "点击了：" + monthDateView.getmSelDay(), Toast.LENGTH_SHORT).show();
            }
        });

        // 点击左箭头  获取前一个月
        iv_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onLeftClick();
            }
        });
        // 点击右箭头  获取下一个月
        iv_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onRightClick();
            }
        });
        // 点击“今天” 直接跳到今天的时间
        tv_today.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.setTodayToView();
            }
        });
    }
    public void initData(){
        weekDayView = (WeekDayView) findViewById(R.id.weekDayView);
        monthDateView = (MonthDateView) findViewById(R.id.monthDateView);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        tv_today = (TextView) findViewById(R.id.tv_today);
        date_text = (TextView) findViewById(R.id.date_text);
        week_text = (TextView) findViewById(R.id.week_text);
        date_operator_ll = (LinearLayout) findViewById(R.id.date_operator_ll);

        monthDateView.setTextView(date_text);  // 将weekDayView与monthDateView 进行绑定    绑定有两种方式 通过重载的方式  看源码就知道了

        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(12);
        list.add(15);
        list.add(16);

//        monthDateView.setDaysHasThingList(list);  //  绑定事务 日程
    }
}
