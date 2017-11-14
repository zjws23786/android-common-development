package com.hua.dev;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hua.dev.adapter.CityListAdapter;
import com.hua.dev.adapter.HotCityAdapter;
import com.hua.dev.base.CityBean;
import com.hua.dev.base.CityModel;
import com.hua.dev.base.po.BaseActivity;
import com.hua.dev.utils.JsonUtils;
import com.hua.librarytools.utils.ConvertUtils;
import com.hua.librarytools.widget.FancyIndexer;
import com.hua.librarytools.widget.FixedGridView;
import com.hua.librarytools.widget.PinnedHeaderListView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CityLocationActivity extends BaseActivity {
    private CityModel cityModel;
    private PinnedHeaderListView listView;
    private FancyIndexer mFancyIndexer;
    private TextView tv_index_center;
    private CityListAdapter adapter;
    private HotCityAdapter hotCityAdapter;
    private LinearLayout headerLayout; // 头部布局
    private FixedGridView hotCityGv; //热门城市

    private List<CityBean> cityList = null;
    private List<String> hotCityList = new ArrayList<>(); //热门城市数据
    private List<String> group = new ArrayList<>();
    private LinkedHashMap<String, List<CityBean>> cityMpas = new LinkedHashMap<>();
    private HashMap<String, Integer> alphabetPositionMap;  //分组标签对应的列表位置
    private List<String> sections;                    // 分组标签（一般是字母A-Z）
    private List<Integer> sectionPositons;                // 分组标签在整个列表中的位置
    private Handler mHandler = new Handler();


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_city_location);
    }

    @Override
    protected void findViewById() {
        listView = (PinnedHeaderListView) findViewById(R.id.pinned_header_lv);
        listView.setPinnedHeaderView(this.getLayoutInflater().inflate(
                R.layout.pinned_header_listview_layout, listView, false));
        mFancyIndexer = (FancyIndexer) findViewById(R.id.fancy_indexer);
        tv_index_center = (TextView) findViewById(R.id.tv_index_center);

        headerLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.city_header_layout, null);
        hotCityGv = (FixedGridView) headerLayout.findViewById(R.id.hot_city_gv);
    }

    @Override
    protected void setListener() {
        mFancyIndexer.setOnTouchLetterChangedListener(new FancyIndexer.OnTouchLetterChangedListener() {
            @Override
            public void onTouchLetterChanged(String letter, int index) {
                if (alphabetPositionMap != null) {
                    int pos = alphabetPositionMap.get(letter) + listView.getHeaderViewsCount();
                    listView.setSelection(pos);
                }
            }

            @Override
            public void onTouchActionUp(String letter) {
                showLetter(letter);
            }
        });
    }

    @Override
    protected void init() {
        try {
            String json = ConvertUtils.toString(this.getAssets().open("citys.config"));
            cityModel = JsonUtils.fromJson(json, CityModel.class);
            if (cityModel != null && cityModel.getAddressList() != null) {
                List<CityBean> addressList  = new ArrayList<>();
                CityBean bean = new CityBean();
                bean.setCity("热门城市");
                bean.setCitypy("热");
                addressList.add(bean);
                addressList.addAll(cityModel.getAddressList());

                if (addressList == null || addressList.size()<2){
                    return;
                }

                for (int i = 0; i < addressList.size(); i++) {
                    CityBean cityBean = addressList.get(i);
                    int j = group.indexOf(cityBean.getCitypy());
                    if (j == -1) {
                        cityList = new ArrayList<>();
                        group.add(cityBean.getCitypy());
                        List<CityBean> list = new ArrayList<>();
                        list.add(cityBean);
                        cityList.add(cityBean);
                        cityMpas.put(cityBean.getCitypy(),cityList);

                    } else {
                        if (cityList != null){
                            cityList.add(cityBean);
                        }
                    }
                }
                String[] constChar = new String[group.size()];
                for (int i=0; i<group.size(); i++){
                    constChar[i] = group.get(i);
                }
                fillHotCityData();
                adapter = new CityListAdapter<CityBean>(this, cityMpas, listView, mFancyIndexer,
                        constChar, 130, 10, headerLayout);
                listView.setOnScrollListener(adapter);
                listView.setAdapter(adapter);
                handlerData(cityMpas);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //热门城市数据
    private void fillHotCityData() {
        hotCityList.clear();
        hotCityList.add("北京");
        hotCityList.add("上海");
        hotCityList.add("广州");
        hotCityList.add("杭州");
        hotCityList.add("南京");
        hotCityList.add("武汉");
        if (hotCityList.size() > 0){
            hotCityAdapter = new HotCityAdapter(this, hotCityList);
            hotCityGv.setAdapter(hotCityAdapter);
        }
    }

    /**
     * 提取 分组标签数据 、 每个分组标签所对应的数据集合、 分组标签的在列表中的位置
     *
     * @param brandMpas
     */
    private void handlerData(LinkedHashMap<String, List<CityBean>> brandMpas) {
        sections = new ArrayList<String>();
        sectionPositons = new ArrayList<Integer>();
        if (brandMpas != null && brandMpas.size() > 0) {
            for (LinkedHashMap.Entry<String, List<CityBean>> entry : brandMpas.entrySet()) {
                //初始化分组标签数据  (比如A,B,C,D.....)
                sections.add(entry.getKey());
            }

            int position = 0;
            if (sections != null && sections.size() > 0) {
                alphabetPositionMap = new HashMap<String, Integer>();
                int sectionSize = sections.size();
                for (int i = 0; i < sectionSize; i++) {
                    //固定头 在 列表中的 位置
                    alphabetPositionMap.put(sections.get(i), position);
                    //添加标签对应列表位置
                    sectionPositons.add(position);
                    position += brandMpas.get(sections.get(i)).size();
                }
            }
        }
    }

    /**
     * 显示字母提示
     *
     * @param letter
     */
    protected void showLetter(String letter) {
        tv_index_center.setVisibility(View.VISIBLE);
        tv_index_center.setText(letter);

        // 取消掉刚刚所有的演示操作
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 隐藏
                tv_index_center.setVisibility(View.GONE);
            }
        }, 2000);

    }
}
