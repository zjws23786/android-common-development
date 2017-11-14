package com.hua.dev;

import android.graphics.Color;
import android.widget.TextView;

import com.hua.dev.base.po.BaseActivity;
import com.hua.librarytools.picker.common.LineConfig;
import com.hua.librarytools.picker.widget.WheelListView;
import com.hua.librarytools.utils.ConvertUtils;

public class ActivityInsideChoiceActivity extends BaseActivity {
    private WheelListView wheelListView;
    private TextView textView;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_inside_choice);
    }

    @Override
    protected void findViewById() {
        textView = (TextView) findViewById(R.id.wheelview_tips);
        wheelListView = (WheelListView) findViewById(R.id.wheelview_single);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void init() {
        wheelListView.setItems(new String[]{"汉族","蒙古族","回族","藏族","维吾尔族","苗族","彝族","壮族","布依族","朝鲜族","满族","侗族","瑶族","白族","土家族",
                "哈尼族","哈萨克族","傣族","黎族","傈僳族","佤族","畲族","高山族","拉祜族","水族","东乡族","纳西族","景颇族","柯尔克孜族",
                "土族","达斡尔族","仫佬族","羌族","布朗族","撒拉族","毛南族","仡佬族","锡伯族","阿昌族","普米族","塔吉克族","怒族", "乌孜别克族",
                "俄罗斯族","鄂温克族","德昂族","保安族","裕固族","京族","塔塔尔族","独龙族","鄂伦春族","赫哲族","门巴族","珞巴族","基诺族"}, 1);
        wheelListView.setSelectedTextColor(0xFFFF00FF);
        LineConfig config = new LineConfig();
        config.setColor(Color.parseColor("#26A1B0"));//线颜色
        config.setAlpha(100);//线透明度
        config.setRatio((float) (1.0 / 5.0));//线比率
        config.setThick(ConvertUtils.toPx(this, 3));//线粗
        config.setShadowVisible(false);
        wheelListView.setLineConfig(config);
        wheelListView.setOnWheelChangeListener(new WheelListView.OnWheelChangeListener() {
            @Override
            public void onItemSelected(boolean isUserScroll, int index, String item) {
                textView.setText("index=" + index + ",item=" + item);
            }
        });
    }
}
