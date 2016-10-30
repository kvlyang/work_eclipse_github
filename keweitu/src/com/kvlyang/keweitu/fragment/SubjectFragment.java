package com.kvlyang.keweitu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kvlyang.keweitu.bean.SubjectBean;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadedDataAndView;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt.OnCreateHolderListener;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.listview.item.GameHolder;
import com.kvlyang.keweitu.listview.item.SubjectHolder;
import com.kvlyang.keweitu.protocal.HttpProtocal;
import com.kvlyang.keweitu.utils.CacheDataLoad;
import com.kvlyang.keweitu.utils.UIUtils;



public class SubjectFragment extends BaseFragment {

	@Override
	protected LoadedDataAndView initDataFromCaches() {
		//SystemClock.sleep(3000);
		LoadedDataAndView dataView = new LoadedDataAndView();
		String result = CacheDataLoad.loadDataFromFile("subject", 0);
		if( result == null){
			dataView.state = LoadedResult.UPDATE;
			dataView.data = null;
			return dataView;
		}else{
			Gson gson = new Gson();
			List<SubjectBean> bean = gson.fromJson(result,new TypeToken<List<SubjectBean>>(){}.getType());
			SubjectData SubjectData = new SubjectData();
			SubjectData.mDatas = bean;
			dataView.state = LoadedResult.UPDATE;
			dataView.data = SubjectData;
			return dataView;
		}
		
	}

	@Override
	protected LoadedDataAndView initDataFromHttp() {
		LoadedDataAndView dataView = new LoadedDataAndView();
		SubjectData SubjectData = new SubjectData();
		String result;
	//	SystemClock.sleep(4000);
		//如果缓存数据未过期，不从网络更新，节省流量
		if (!CacheDataLoad.isCachaOutTime("subject", 0)){
			dataView.state = LoadedResult.EMPTY;
			dataView.data = null;
			return dataView;
		}
			
			
		try {
			// 解析json网络数据
			result =  HttpProtocal.loadHttpData("subject",0);
			Gson gson = new Gson();
			List<SubjectBean> bean = gson.fromJson(result,new TypeToken<List<SubjectBean>>(){}.getType());
			// 异常处理，显示网络异常界面或缓存
			if (bean == null) {
				dataView.state = LoadedResult.ERROR;
				dataView.data = null;
				return dataView;
			}
			if (bean.size() == 0) {
				dataView.state = LoadedResult.ERROR;
				dataView.data = null;
				return dataView;
			}
			SubjectData.mDatas = bean;

		} catch (Exception e) {
			Log.e("keweituBug", "HttpUtils() error;");
			// e.printStackTrace();
			dataView.state = LoadedResult.ERROR;
			dataView.data = null;
			return dataView;// 获取网络数据失败
		}
		//保存到缓存
		CacheDataLoad.writeDataToFile("subject", 0, result);
		
		// 获取网络数据成功，强制更新SuccessView
		dataView.state = LoadedResult.UPDATE_F;
		dataView.data = SubjectData;
		return dataView;
	}

	@Override
	protected View initSuccessView(Object data) {
		if (data == null || !(data instanceof SubjectData)) {
			return null;
		}

		/*
		 * 测试
		 * TextView tv = new TextView(getActivity());
		 * tv.setText("hahahah"+((SubjectData)data).result); return tv;
		 */

		SubjectData SubjectDatas = (SubjectData) data;

		// 无数据，返回null，容器将加载默认view
		if (SubjectDatas.mDatas == null || SubjectDatas.mDatas.size() == 0) {
			return null;
		}

		ListView listView = new ListView(UIUtils.getContext());

		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setFastScrollEnabled(true);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		SubjectAdapter homeAdapter = new SubjectAdapter(SubjectDatas.mDatas);
		// adapter最好先setOnCreateHolderListener再赋值给listView
		homeAdapter
				.setOnCreateHolderListener(new OnCreateHolderListener<SubjectBean>() {
					@Override
					public BaseHolder<SubjectBean> creatHolder(int type) {
							return new SubjectHolder();
					}
				});
		
		listView.setAdapter(homeAdapter);
		listView.setOnItemClickListener(homeAdapter);
		return listView;
	}

	// adapter作为内部类的好处，方便调用父类数据
	class SubjectAdapter extends BaseAdapterKwt<SubjectBean> {

		public SubjectAdapter(List<SubjectBean> mDatas) {
			super(mDatas);
		}

		public void onNormalItemClick(AdapterView<?> parent, View view, int position,
				long id){
			
		}
		


		@Override
		public List<SubjectBean> OnLoadMore() throws Exception {
		//	SystemClock.sleep(3000);
			String result = HttpProtocal.loadHttpData("subject",mListData.size());
			Gson gson = new Gson();
			return gson.fromJson(result,new TypeToken<List<SubjectBean>>(){}.getType());
			
		}

	}
	
	public class SubjectData {
		public List<SubjectBean> mDatas;
		public String result; // 网络数据
		
		public SubjectData() {
			mDatas = new ArrayList<SubjectBean>();
		}
	}
	

	
}
