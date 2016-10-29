package com.kvlyang.keweitu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kvlyang.keweitu.bean.AppInfoBean;
import com.kvlyang.keweitu.bean.HomeBean;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadedDataAndView;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt.OnCreateHolderListener;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.listview.item.HomeHolder;
import com.kvlyang.keweitu.listview.item.PictureHolder;
import com.kvlyang.keweitu.protocal.HttpProtocal;
import com.kvlyang.keweitu.utils.CacheDataLoad;
import com.kvlyang.keweitu.utils.UIUtils;

public class HomeFragment extends BaseFragment {

	@Override
	protected LoadedDataAndView initDataFromCaches() {
		//SystemClock.sleep(3000);
		LoadedDataAndView dataView = new LoadedDataAndView();
		String result = CacheDataLoad.loadDataFromFile("home", 0);
		if( result == null){
			dataView.state = LoadedResult.UPDATE;
			dataView.data = null;
			return dataView;
		}else{
			Gson gson = new Gson();
			HomeBean bean = gson.fromJson(result, HomeBean.class);
			HomeData homeData = new HomeData();
			homeData.mDatas = bean.list;
			homeData.pictures = bean.picture;
			dataView.state = LoadedResult.UPDATE;
			dataView.data = homeData;
			return dataView;
		}
		
	}

	@Override
	protected LoadedDataAndView initDataFromHttp() {
		LoadedDataAndView dataView = new LoadedDataAndView();
		HomeData homeData = new HomeData();
		String result;
	//	SystemClock.sleep(4000);
		//如果缓存数据未过期，不从网络更新，节省流量
		if (!CacheDataLoad.isCachaOutTime("home", 0)){
			dataView.state = LoadedResult.EMPTY;
			dataView.data = null;
			return dataView;
		}
			
			
		try {
			// 解析json网络数据
			result =  HttpProtocal.loadHttpData("home",0);
			Gson gson = new Gson();
			HomeBean homeBean = gson.fromJson(result, HomeBean.class);
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
			homeData.pictures = homeBean.picture;

		} catch (Exception e) {
			Log.e("keweituBug", "HttpUtils() error;");
			// e.printStackTrace();
			dataView.state = LoadedResult.ERROR;
			dataView.data = null;
			return dataView;// 获取网络数据失败
		}
		//保存到缓存
		CacheDataLoad.writeDataToFile("home", 0, result);
		
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
		
		//轮播图
		PictureHolder pictureHolder = new PictureHolder();
		pictureHolder.setDataAndRefreshHolderView(homeDatas.pictures);
		View headView = pictureHolder.getHolderView();
		
		HomeAdapter homeAdapter = new HomeAdapter(homeDatas.mDatas);
		// adapter最好先setOnCreateHolderListener再赋值给listView
		homeAdapter
				.setOnCreateHolderListener(new OnCreateHolderListener<AppInfoBean>() {
					@Override
					public BaseHolder<AppInfoBean> creatHolder(int type) {
							return new HomeHolder();
					}
				});
		listView.addHeaderView(headView);
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
			String result = HttpProtocal.loadHttpData("home",mListData.size());
			Gson gson = new Gson();
			return gson.fromJson(result, HomeBean.class).list;
			
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
