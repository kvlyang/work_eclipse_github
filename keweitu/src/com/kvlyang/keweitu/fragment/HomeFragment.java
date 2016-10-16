package com.kvlyang.keweitu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kvlyang.keweitu.bean.AppInfoBean;
import com.kvlyang.keweitu.bean.HomeBean;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadedDataAndView;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt.OnCreateHolderListener;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.listview.item.HomeHolder;
import com.kvlyang.keweitu.protocal.HttpProtocal;
import com.kvlyang.keweitu.utils.CacheDataLoad;
import com.kvlyang.keweitu.utils.UIUtils;

public class HomeFragment extends BaseFragment {

	@Override
	protected LoadedDataAndView initDataFromCaches() {
		//SystemClock.sleep(3000);
		LoadedDataAndView dataView = new LoadedDataAndView();
		dataView.state = LoadedResult.UPDATE;
		dataView.data = null;
		return dataView;
	}

	@Override
	protected LoadedDataAndView initDataFromHttp() {
		LoadedDataAndView dataView = new LoadedDataAndView();
		HomeData homeData = new HomeData();
	//	SystemClock.sleep(2000);
		//如果缓存数据未过期，不从网络更新，节省流量
		if (!CacheDataLoad.isCachaOutTime("home", 0)){
			dataView.state = LoadedResult.EMPTY;
			dataView.data = null;
			return dataView;
		}
			
			
		try {
			HomeBean homeBean = HttpProtocal.loadHomeData(0);
			// 异常处理，显示网络异常界面或缓存
			if (homeBean == null) {
				dataView.state = LoadedResult.ERROR;
				dataView.data = null;
				return dataView;
			}
			if (homeBean.list == null || homeBean.list.size() == 0) {
				dataView.state = LoadedResult.ERROR;
				dataView.data = null;
				return dataView;
			}
			homeData.mDatas = homeBean.list;

		} catch (Exception e) {
			Log.e("keweituBug", "HttpUtils() error;");
			// e.printStackTrace();
			dataView.state = LoadedResult.ERROR;
			dataView.data = null;
			return dataView;// 获取网络数据失败，如果SuccessView存在缓存数据则不做任何变化
		}
		
		// 获取网络数据成功，强制更新SuccessView
		dataView.state = LoadedResult.UPDATE_F;
		dataView.data = homeData;
		return dataView;
	}

	@Override
	protected View initSuccessView(Object data) {
		if (data == null || !(data instanceof HomeData)) {
			return null;
		}

		/*
		 * 测试
		 * TextView tv = new TextView(getActivity());
		 * tv.setText("hahahah"+((HomeData)data).result); return tv;
		 */

		HomeData homeDatas = (HomeData) data;

		// 无数据，返回null，容器将加载默认view
		if (homeDatas.mDatas == null || homeDatas.mDatas.size() == 0) {
			return null;
		}

		ListView listView = new ListView(UIUtils.getContext());

		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setFastScrollEnabled(true);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		HomeAdapter homeAdapter = new HomeAdapter(homeDatas.mDatas);
		// adapter最好先setOnCreateHolderListener再赋值给listView
		homeAdapter
				.setOnCreateHolderListener(new OnCreateHolderListener<AppInfoBean>() {
					@Override
					public BaseHolder<AppInfoBean> creatHolder(int type) {
							return new HomeHolder();
					}
				});
		
		listView.setAdapter(homeAdapter);
		listView.setOnItemClickListener(homeAdapter);
		return listView;
	}

	// adapter作为内部类的好处，方便调用父类数据
	class HomeAdapter extends BaseAdapterKwt<AppInfoBean> {

		public HomeAdapter(List<AppInfoBean> mDatas) {
			super(mDatas);
		}

		public void onNormalItemClick(AdapterView<?> parent, View view, int position,
				long id){
			
		}
		


		@Override
		public List<AppInfoBean> OnLoadMore() throws Exception {
		//	SystemClock.sleep(3000);
			return HttpProtocal.loadHomeData(mListData.size()).list;
			
		}

	}
	
	public class HomeData {
		public List<AppInfoBean> mDatas;
		public List<String> pictures;// 轮播图
		public String result; // 网络数据
		
		public HomeData() {
			mDatas = new ArrayList<AppInfoBean>();
			pictures = new ArrayList<String>();
		}
	}

	

}
