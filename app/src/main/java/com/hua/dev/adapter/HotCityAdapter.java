package com.hua.dev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hua.dev.R;

import java.util.List;


/**
 * 热门城市adapter
 */
public class HotCityAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> hotCityList;


    public HotCityAdapter(Context context, List<String> hotCityList) {
        this.mContext = context;
        this.hotCityList = hotCityList;
    }

    @Override
    public int getCount() {
        return hotCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hot_city_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.hot_city_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String cityName = hotCityList.get(position);
        if(!"".equals(cityName)){
            holder.name.setText(cityName);
        }
        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
