package com.hua.librarytools.widget.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

import com.hua.librarytools.widget.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * 适用于分组标签的ListView适配器
 *
 * @param <T>
 */
public abstract class BasePinnedHeaderAdapter<T> extends BaseAdapter implements SectionIndexer,
		PinnedHeaderListView.PinnedHeaderAdapter, OnScrollListener {
	private int mLocationPosition = -1;
	protected Context context;
	protected ScrollListViewListener scrollListViewListener;
	protected List<String> sections;                    // 分组标签（一般是字母A-Z）
	private List<Integer> sectionPositons;                // 分组标签在整个列表中的位置
	protected LayoutInflater inflater;
	protected List<T> datas;                            // 分组标签所对应的列表数据集合

	protected HashMap<String, Integer> alphabetPositionMap;  //分组标签对应的列表位置

	private int headerCount; //listView 头部视图处理
	/**
	 * 构造器
	 *
	 * @param context 上下文
	 * @param mMap
	 */
	public BasePinnedHeaderAdapter(Context context, LinkedHashMap<String, List<T>> mMap) {
		if (context != null) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		if(mMap!=null) {
			handlerData(mMap);
		}
	}



	public void setHeaderCout( int headerCount){
		this.headerCount =  headerCount;
	}

	/**
	 * 提取 分组标签数据 、 每个分组标签所对应的数据集合、 分组标签的在列表中的位置
	 *
	 * @param mMap
	 */
	public void handlerData(LinkedHashMap<String, List<T>> mMap) {

		datas = new ArrayList<T>();
		sections = new ArrayList<String>();
		sectionPositons = new ArrayList<Integer>();
		if (mMap != null && mMap.size() > 0) {
			Log.d("mMap:", mMap.size() + "");
			for (LinkedHashMap.Entry<String, List<T>> entry : mMap.entrySet()) {
				//初始化分组标签数据
				sections.add(entry.getKey());
				//添加标签对应的数据集合
				Log.d("dat",entry.getValue()+"");

				datas.addAll(entry.getValue());
			}

			int position = 0;
			if (sections != null && sections.size() > 0) {
				alphabetPositionMap = new HashMap<String, Integer>();
				int sectionSize = sections.size();
				for (int i = 0; i < sectionSize; i++) {
					alphabetPositionMap.put(sections.get(i), position);
					//添加标签对应列表位置
					sectionPositons.add(position);
					position += mMap.get(sections.get(i)).size();
				}
			}
		}

		Log.d("datas:",sections.size()+"");
	}

	/**
	 * 清空datas
	 */
	public void clearData(){
		if(datas!=null) {
			datas.clear();
		}

		if(sections!=null){
			sections.clear();
		}

		if(sectionPositons!=null){
			sectionPositons.clear();
		}
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		if (null != datas && datas.size() > 0) {
			return datas.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getListView(position, convertView, parent);
	}


	protected abstract View getListView(int position, View convertView, ViewGroup parent);

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollListViewListener != null) {
			scrollListViewListener.onScroll();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
		if (view instanceof PinnedHeaderListView) {
			((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
		}
	}

	/**
	 * 获取当前头的状态
	 *
	 * @param position
	 * @return
	 */
	@Override
	public int getPinnedHeaderState(int position) {
		int realPosition = position-headerCount;
		if (realPosition < 0
				|| (mLocationPosition != -1 && mLocationPosition == realPosition)) {
			return PINNED_HEADER_GONE;
		}
		mLocationPosition = -1;
		int section = getSectionForPosition(realPosition);
		int nextSectionPosition = getPositionForSection(section + 1);
		//如果当前位置的下一个位置是头，则返回头上升状态否则返回头显示状态
		if (nextSectionPosition != -1
				&& realPosition == nextSectionPosition - 1) {
			return PINNED_HEADER_PUSHED_UP;
		}
		return PINNED_HEADER_VISIBLE;
	}

	protected abstract void setHeaderContent(View header, String content);

	@Override
	public void configurePinnedHeader(View header, int position, int alpha) {
		int realPosition = position;
		int section = getSectionForPosition(realPosition - headerCount);
		if (getSections() != null && section > -1) {
			String title = (String) getSections()[section];
			if(title!=null) {
				setHeaderContent(header, title);
			}
		}
	}

	@Override
	public Object[] getSections() {
		if (sections != null) {
			return sections.toArray();
		}
		return null;
	}


	@Override
	public int getPositionForSection(int section) {
		if (section < 0 || section >= sections.size()) {
			return -1;
		}
		return sectionPositons.get(section);
	}

	public String getSection(int position) {
		return sections.get(getSectionForPosition(position));
	}

	@Override
	public int getSectionForPosition(int position) {
		if (position < 0 || position >= getCount()) {
			return -1;
		}
		int index = Arrays.binarySearch(sectionPositons.toArray(), position);
		return index >= 0 ? index : -index - 2;
	}

	public void setScrollListViewListener(ScrollListViewListener scrollListViewListener) {
		this.scrollListViewListener = scrollListViewListener;
	}

	public interface ScrollListViewListener {
		public void onScroll();
	}

}
