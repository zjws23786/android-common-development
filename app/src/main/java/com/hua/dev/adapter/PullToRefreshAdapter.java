package com.hua.dev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hua.dev.R;
import com.hua.dev.base.RecommendModel;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class PullToRefreshAdapter extends BaseAdapter {
    private Context mContext;
    private List<RecommendModel.TopicListBean> lists;

    public PullToRefreshAdapter(Context context){
        this.mContext = context;
    }

    public void setData(List<RecommendModel.TopicListBean> lists) {
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists==null ? 0 : lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists==null ? null : lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_pull_to_refresh,null);
            holder.nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        RecommendModel.TopicListBean topicListBean = lists.get(position);
        holder.nameTv.setText(topicListBean.getTitle());
        return convertView;
    }

    class ViewHolder{
        TextView nameTv;
    }
}
