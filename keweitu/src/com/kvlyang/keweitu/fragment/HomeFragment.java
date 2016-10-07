package com.kvlyang.keweitu.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.kvlyang.keweitu.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HomeFragment extends BaseFragment {

	@Override
	protected LoadedDataAndView initDataFromCaches() {
		SystemClock.sleep(3000);
		/*
		 * for(int n = 0; n<100;n++){ mDatas.add("test"+n); }
		 */
		LoadedDataAndView dataView = new LoadedDataAndView();
		dataView.state = LoadedResult.UPDATE;
		dataView.data = null;
		return dataView;
	}

	@Override
	protected LoadedDataAndView initDataFromHttp() {
		LoadedDataAndView dataView = new LoadedDataAndView();
		HomeData homeData = new HomeData();
		SystemClock.sleep(4000);
		try {
			Log.e("keweituBug", "HttpUtils() start;");
			HttpUtils httpUtils = new HttpUtils();
			// String url = "http://10.0.3.2/keweituServer/home.php";
			String url = "http://10.0.3.2:8080/GooglePlayServer/home?index=0";
			// String url =
			// "http://10.0.3.2:8080/GooglePlayServer/image?name=app/com.renren.mobile.android/icon.jpg";
			// String url = "https://www.baidu.com/?tn=62095104_oem_dg";
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("index", "0");
			ResponseStream stream = httpUtils.sendSync(HttpMethod.GET, url,
					params);
			homeData.result = stream.readString();

			// 解析json网络数据

			Gson gson = new Gson();
			HomeBean homeBean = gson.fromJson(homeData.result, HomeBean.class);

			// 异常处理，显示空界面或缓存
			if (homeBean == null) {
				dataView.state = LoadedResult.ERROR;
				return dataView;
			}
			if (homeBean.list == null || homeBean.list.size() == 0) {
				dataView.state = LoadedResult.ERROR;
				return dataView;
			}
			homeData.mDatas = homeBean.list;

		} catch (Exception e) {
			Log.e("keweituBug", "HttpUtils() error;");
			// e.printStackTrace();
			dataView.state = LoadedResult.ERROR;
			return dataView;// 获取网络数据失败，如果SuccessView存在缓存数据则不做任何变化
		}

		dataView.state = LoadedResult.UPDATE_F;
		dataView.data = homeData;
		return dataView;// 获取网络数据成功，强制更新SuccessView
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

		HomeAdapter homeAdapter = new HomeAdapter(homeDatas.mDatas);
		homeAdapter
				.setOnCreateHolderListener(new OnCreateHolderListener<AppInfoBean>() {

					@Override
					public BaseHolder<AppInfoBean> creatHolder() {
						return new HomeHolder();
					}

				});
		// adapter最好先setOnCreateHolderListener再赋值给listView
		listView.setAdapter(homeAdapter);
		return listView;
	}

	// adapter作为内部类的好处，方便调用父类数据
	class HomeAdapter extends BaseAdapterKwt<AppInfoBean> {

		public HomeAdapter(List<AppInfoBean> mDatas) {
			super(mDatas);
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

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
