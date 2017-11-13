package com.hua.dev;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hua.dev.base.BaseActivity;
import com.hua.librarytools.picker.time.DatePicker;
import com.hua.librarytools.utils.UIToast;

import java.util.Calendar;

public class AboutTimeActivity extends BaseActivity implements View.OnClickListener {
    private Button specific_date_btn;  //年月日

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_about_time);
    }

    @Override
    protected void findViewById() {
        specific_date_btn = (Button) findViewById(R.id.specific_date_btn);
    }

    @Override
    protected void setListener() {
        specific_date_btn.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.specific_date_btn:
                specificDateMethod();
                break;
        }
    }

    //年 月 日
    private void specificDateMethod() {
        //获取当前系统的时间
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);  //年
        int month = now.get(Calendar.MONTH); // 月
        int day = now.get(Calendar.DAY_OF_MONTH);  //日
//        int hour = now.get(Calendar.HOUR); //时  以最大12形式显示
        int hour = now.get(Calendar.HOUR_OF_DAY); //时   以最大24形式显示
        int minute = now.get(Calendar.MINUTE); //分
        int second = now.get(Calendar.SECOND); //秒

        Log.v("hjz",year + "年"+month+"月"+day+"日"+hour+"时"+minute+"分"+second+"秒");

        final DatePicker picker = new DatePicker(this);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(year - 10, 1, 1);  //前10年

        //未来时间
        Calendar futureTime = Calendar.getInstance();
        futureTime.set(Calendar.YEAR,year+20);
        futureTime.roll(Calendar.DAY_OF_YEAR, -1);
        int futureDay = futureTime.getActualMaximum(Calendar.DATE);
        Log.v("hjz","未来20年最后那个月有"+futureDay + "天");

        picker.setRangeEnd(year + 20, 12, futureDay);  //未来20年
        picker.setSelectedItem(year, month, day);
        picker.setWeightEnable(true);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                UIToast.showBaseToast(AboutTimeActivity.this, year + "-" + month + "-" + day, R.style.AnimationToast);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }
}
