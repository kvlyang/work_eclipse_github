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
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;



public class HomeFragment extends BaseFragment {
	private List<String> mDatas =new ArrayList<String>();
	
	@Override
	protected LoadedResult initDataFromCaches() {
		
		
		
		/*for(int n = 0; n<100;n++){
			mDatas.add("test"+n);
		}*/
		return LoadedResult.UPDATE;
	}
	
	@Override
	protected LoadedResult initDataFromHttp() { 
		//不需要管loading显示，没有数据显示时，默认 调用initDataFromHttp()已经自动显示了loading界面
		
		HttpUtils httpUtils = new HttpUtils();
		String url = "http://localhost/keweitu/home?indext=0";
		try {
			httpUtils.sendSync(HttpMethod.GET, url);
		} catch (HttpException e) {
			e.printStackTrace();
			return LoadedResult.EMPTY;
		}
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
		homeAdapter.setOnCreateHolderListener(new OnCreateHolderListener<String>() {

			@Override
			public BaseHolder<String> creatHolder() {
				return new TestHolder();
			}
			
		});
		//adapter最好先setOnCreateHolderListener再赋值给listView
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
