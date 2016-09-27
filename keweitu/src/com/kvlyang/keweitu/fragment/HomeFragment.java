package com.kvlyang.keweitu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt.OnCreateHolderListener;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.listview.item.TestHolder;
import com.kvlyang.keweitu.utils.UIUtils;



public class HomeFragment extends BaseFragment {
	private List<String> mDatas =new ArrayList<String>();
	
	@Override
	protected LoadedResult initDataFromCaches() {
		SystemClock.sleep(1000);
		for(int n = 0; n<100;n++){
			mDatas.add("test"+n);
		}
		return LoadedResult.UPDATE;
	}
	
	@Override
	protected LoadedResult initDataFromHttp() {
		//此方法也可不起做用，listview的更新可自己下拉刷新
		SystemClock.sleep(3000);
		return LoadedResult.EMPTY;
	}
 
	@Override
	protected View initSuccessView() {
		if(mDatas == null || mDatas.size() == 0){//无数据，返回null，容器将加载默认view
			return null;
		}
		
		ListView listView = new ListView(UIUtils.getContext());
		
		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setFastScrollEnabled(true);
		
		HomeAdapter homeAdapter = new HomeAdapter(mDatas);
		homeAdapter.setOnOnCreateHolderListener(new OnCreateHolderListener<String>() {

			@Override
			public BaseHolder<String> creatHolder() {
				// TODO Auto-generated method stub
				return new TestHolder();
			}
			
		});
		listView.setAdapter(homeAdapter);
		return listView;
	}

	//adapter作为内部类的好处，方便调用父类数据
	class HomeAdapter extends BaseAdapterKwt<String>{

		public HomeAdapter(List<String> listData) {
			super(listData);
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
		}
		
	}
	

	
}
