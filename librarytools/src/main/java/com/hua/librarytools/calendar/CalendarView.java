package com.hua.librarytools.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hua.librarytools.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class CalendarView extends LinearLayout{
    private Context context;
    private ImageView leftArrowIv;
    private ImageView rightArrowIv;
    private TextView tvDate;
    private GridView mGridView;
    private String dateFormat;

    /**
     * 获取系统日历
     */
    private Calendar mCalender = Calendar.getInstance();

    //用户选中日期
    private Date selectedDate = null;

    private OnNowDateListener onNowDateListener;

    public CalendarView(Context context) {
        this(context,null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        bindControl(attrs);
        bindEvent();
        renderCalendar();
    }

    /**
     * 绑定layout
     */
    private void bindControl(AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_calendar, this);
        leftArrowIv = (ImageView) view.findViewById(R.id.calendar_left_arrow_iv);
        rightArrowIv = (ImageView) view.findViewById(R.id.calendar_right_arrow_iv);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        mGridView = (GridView) view.findViewById(R.id.calendar_view);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
        dateFormat = typedArray.getString(R.styleable.CalendarView_dateFormat);
        if (dateFormat == null) {
            dateFormat = "MM yyyy";
        }

        typedArray.recycle();
    }

    private void bindEvent() {
        leftArrowIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //前一个月
                mCalender.add(Calendar.MONTH, -1);
                renderCalendar();
            }
        });
        rightArrowIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //后一个月
                mCalender.add(Calendar.MONTH, 1);
                renderCalendar();
            }
        });
    }

    /**
     * 渲染日历控件
     */
    private void renderCalendar() {
        //当前月份展示
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        tvDate.setText(simpleDateFormat.format(mCalender.getTime()));

        //gridView数据展示
        final ArrayList<Date> cells = new ArrayList<>();
        //克隆一份
        Calendar calendar = (Calendar) mCalender.clone();
        //设置当前时间为本月的一号
        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
//        Log.v("hjz","该月第一天是星期="+firstDay);
        //今天是星期几，这里是以星期天为一周的第一天
        //calendar.get(Calendar.DAY_OF_WEEK) 的值为1~7之间的整数，1代表周日，7代表周六，其余依次类推
        //减一是判断本月一号之前空几位，举个例子：假如今天是2017-12-8 星期五
        //那么calendar.get(Calendar.DAY_OF_WEEK) = 6，
        // prevDays = 6 - 1 = 5,即一号之前有五个位置是空的。
        int prevDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        //calendar.add(Calendar.DAY_OF_MONTH, -prevDays)代表上个月的最后 prevDays 天
        calendar.add(Calendar.DAY_OF_MONTH, -prevDays);

        //一周最多是七天，一个月最多占六行，举个例子
        //假如这个月的一号是在周六并且这个月是三十一天，那么一号就独占一行，那么剩下的三十天就在其他行了
        //就会有 4 * 7 = 28，占据满满的四行，剩余31-1-28=2天独占一行，这样一个月就展示完了，最多占据
        //四行。
        int monthDayNum = mCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
        int maxCellCount = 6 * 7;
        if (prevDays + monthDayNum < 36){ //控制月份显示的行数
            maxCellCount = 5 * 7;
        }

        while (cells.size() < maxCellCount) {
            //添加天数
            cells.add(calendar.getTime());
            //当前日期加一后的日期，举个例子，假如今天是2017-12-8，那么
            //calendar.add(Calendar.DAY_OF_MONTH, 1)之后就变成了2017-12-9。
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        mGridView.setAdapter(new CalendarAdapter(getContext(), cells,mCalender.get(Calendar.MONTH) + 1));
        //添加点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (onNowDateListener != null) {
                    selectedDate = (Date) adapterView.getItemAtPosition(position);
                    renderCalendar();
                    onNowDateListener.onItemLongClick(selectedDate);
                }
            }
        });
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {

        LayoutInflater inflater;
        int monthNum;  //第几月

        public CalendarAdapter(@NonNull Context context, ArrayList<Date> dates,int monthNum) {
            super(context, R.layout.calender_text_day, dates);
            inflater = LayoutInflater.from(context);
            this.monthNum = monthNum;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //获取当前数据
            Date date = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.calender_text_day, parent, false);
            }
            //获取天
            int day = date.getDate();
            ((CircleTextView) convertView).setText(String.valueOf(day));

            //判断是否是同一个月
            boolean isTheSameMonth = false;

            if (date.getMonth()+1 == monthNum) {
                isTheSameMonth = true;
            }
            //判断有效月份
            if (isTheSameMonth) {
                //同一个月
                ((CircleTextView) convertView).setTextColor(Color.parseColor("#333333"));
            } else {
                //不同月
                ((CircleTextView) convertView).setTextColor(Color.parseColor("#999999"));
            }

            //获取当前日期
            Date now = new Date();
            //判断是否是当天
            if (now.getDate() == date.getDate() && now.getMonth() == date.getMonth()
                    && now.getYear() == date.getYear()) {
                ((CircleTextView) convertView).setTextColor(Color.parseColor("#ffffff"));
//                ((CircleTextView) convertView).isToday = true;
                ((CircleTextView) convertView).setToday(true);
            }

            if (selectedDate != null){
                if (selectedDate.getDate() == date.getDate() && selectedDate.getMonth() == date.getMonth()
                        && selectedDate.getYear() == date.getYear() && now.getDate() != selectedDate.getDate()) {
//                    ((CircleTextView) convertView).setTextColor(Color.parseColor("#ffffff"));
                    ((CircleTextView) convertView).setSelectedDay();
                }
            }
            return convertView;
        }
    }

    public interface OnNowDateListener {
        void onItemLongClick(Date date);
    }

    public void setOnNowDateListener(OnNowDateListener onNowDateListener) {
        this.onNowDateListener = onNowDateListener;
    }
}
