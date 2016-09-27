package com.kvlyang.keweitu.listview.base;

import java.util.ArrayList;
import java.util.List;

import com.kvlyang.keweitu.listview.item.TestHolder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

/**
 * @author Administrator
 * @time 2015-7-16 上午9:58:55
 * @des TODO
 * 
 * @version $Rev: 38 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-19 09:59:23 +0800 (星期日, 19 七月 2015) $
 * @updateDes TODO
 */
public abstract class BaseAdapterKwt<ITEMBEANTYPE> extends BaseAdapter
		implements OnItemClickListener {
	public List<ITEMBEANTYPE> mListData = new ArrayList<ITEMBEANTYPE>();
	OnCreateHolderListener<ITEMBEANTYPE> onCreateHolderListener;

	public BaseAdapterKwt(List<ITEMBEANTYPE> listData) {
		super();
		mListData = listData;
	}

	@Override
	public int getCount() {
		if (mListData != null) {
			return mListData.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mListData != null) {
			return mListData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder<ITEMBEANTYPE> holder = null;
		if (convertView == null) {
			if(onCreateHolderListener !=null){
				holder = (BaseHolder<ITEMBEANTYPE>) onCreateHolderListener.creatHolder(); 
			}
			
			if (holder == null) {
				return null;
			}
			convertView = holder.getHolderView();
			convertView.setTag(holder);
		} else {
			holder =  (BaseHolder<ITEMBEANTYPE>) convertView.getTag();
		}

		holder.setDataAndRefreshHolderView(mListData.get(position));

		return holder.getHolderView();
	}

	

	public void setOnOnCreateHolderListener( OnCreateHolderListener<ITEMBEANTYPE> listener){
		onCreateHolderListener = listener;
	}

	
	public interface OnCreateHolderListener<ITEMBEANTYPE>{
		public BaseHolder<ITEMBEANTYPE> creatHolder();
		}
}


