package com.hua.dev;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.hua.dev.base.po.BaseActivity;
import com.hua.librarytools.calendar.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarActivity extends BaseActivity {
    private CalendarView calendarView;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_calendar);
    }

    @Override
    protected void findViewById() {
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnNowDateListener(new CalendarView.OnNowDateListener() {
            @Override
            public void onItemLongClick(Date date) {
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(CalendarActivity.this, "当前日期：" + df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {

    }
}
