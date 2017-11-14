package com.hua.dev.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.hua.dev.R;
import com.hua.dev.base.CityBean;
import com.hua.librarytools.widget.FancyIndexer;
import com.hua.librarytools.widget.adapter.BasePinnedHeaderAdapter;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by hjz on 2016/12/5.
 */

public class CityListAdapter<T> extends BasePinnedHeaderAdapter<T> {
    public List<String> alphabets;   //字母索引数据集合，品牌列表比较特殊，需要对原始的分组字母集合进行处理
    private int selectedPosition = -1;
    private View headerLayout = null;

    public CityListAdapter(Context context, LinkedHashMap<String, List<T>> mMap, ListView listView,
                           FancyIndexer mFancyIndexer, String[] constChar, int top, int bottom, View headerLayout) {
        super(context, mMap);
        mFancyIndexer.setParams(constChar , top, bottom);
        this.headerLayout = headerLayout;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if(position>0){
            viewType=1;
        }else{
            viewType=0;
        }
        return viewType;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    protected View getListView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        int itemViewType = getItemViewType(position);
        if (convertView == null) {
            if (itemViewType == 0){
                convertView = headerLayout;
            }else{
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.adapter_city_item, null);
                viewHolder.header = (TextView) convertView.findViewById(R.id.pinned_header_tv);
                viewHolder.cityName = (TextView) convertView.findViewById(R.id.city_name);
                viewHolder.pinnedheaderDivider = convertView.findViewById(R.id.pinnedheader_divider);
                viewHolder.spacingView = convertView.findViewById(R.id.spacing_view);
                viewHolder.spcingViewLine = convertView.findViewById(R.id.spacing_view_line);
                convertView.setTag(viewHolder);
            }
        } else {
            if (itemViewType == 0){

            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
        }

        if (itemViewType == 0){

        }else{
            if (selectedPosition == position) {
                convertView.setBackgroundColor(Color.parseColor("#f2f2f2"));
            } else {
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            if (datas != null) {
                int section = getSectionForPosition(position);
                final CityBean cityBean = (CityBean) getItem(position);
                if (cityBean != null) {
                    if (getPositionForSection(section) == position) {
                        viewHolder.header.setVisibility(View.VISIBLE);
                        viewHolder.pinnedheaderDivider.setVisibility(View.VISIBLE);
                        viewHolder.spacingView.setVisibility(View.VISIBLE);
                        viewHolder.spcingViewLine.setVisibility(View.VISIBLE);
                        viewHolder.header.setText(sections.get(section));
                        int selectedSection = getSectionForPosition(selectedPosition);
                        if (section == 0) {
                            viewHolder.spacingView.setVisibility(View.GONE);
                            viewHolder.spcingViewLine.setVisibility(View.GONE);
                        }
                        if (section != selectedSection) {
                            viewHolder.header.setBackgroundColor(Color.parseColor("#ffffff"));
                        }

                    } else {
                        viewHolder.header.setVisibility(View.GONE);
                        viewHolder.pinnedheaderDivider.setVisibility(View.GONE);
                        viewHolder.spacingView.setVisibility(View.GONE);
                        viewHolder.spcingViewLine.setVisibility(View.GONE);
                    }
                    String brandName = cityBean.getCity();
                    if (null != brandName) {
                        viewHolder.cityName.setText(brandName);
                    }
                }

                viewHolder.cityName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        EventBus.getDefault().post(cityBean);
//                        ((CityLocationActivity)context).finish();
                    }
                });

            }
        }
        return convertView;
    }

    @Override
    protected void setHeaderContent(View header, String section) {
        TextView textView = (TextView) header.findViewById(R.id.pinned_header_tv);
        if (section.equals("热")){
            section = "热门城市";
        }
        textView.setText(section);
    }


    class ViewHolder {
        private TextView header;// 头部
        private TextView cityName;
        private View pinnedheaderDivider;
        private View spacingView;
        private View spcingViewLine;

    }


}

