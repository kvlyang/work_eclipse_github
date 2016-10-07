package com.kvlyang.keweitu.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.bean.HomeBean;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt;
import com.kvlyang.keweitu.listview.base.BaseAdapterKwt.OnCreateHolderListener;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.listview.item.TestHolder;
import com.kvlyang.keweitu.utils.LogUtils;
import com.kvlyang.keweitu.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HomeFragment extends BaseFragment {
	private List<String> mDatas = new ArrayList<String>();
	private String result;

	@Override
	protected LoadedResult initDataFromCaches() {

		/*
		 * for(int n = 0; n<100;n++){ mDatas.add("test"+n); }
		 */
		return LoadedResult.UPDATE; 
	}

	@Override
	protected LoadedResult initDataFromHttp() {
		
		//SystemClock.sleep(3000);
	try {
		Log.e("keweituBug", "HttpUtils();");
			HttpUtils httpUtils = new HttpUtils();
		//	String url = "http://10.0.3.2/keweituServer/home.php";
			//String url = "http://10.0.3.2:8080/GooglePlayServer/home?index=0";
			String url = "http://10.0.3.2:8080/GooglePlayServer/image?name=app/com.renren.mobile.android/icon.jpg";
		//	String url = "https://www.baidu.com/?tn=62095104_oem_dg";
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("index", "0");
			ResponseStream stream = httpUtils.sendSync(HttpMethod.GET, url,
					params);
			result = stream.readString();
			
			//解析json网络数据
			//Gson gson = new Gson();
			//gson.fromJson(result, HomeBean.class);
		} catch (Exception e) {
			e.printStackTrace();
			return LoadedResult.EMPTY;//获取网络数据失败，如果SuccessView存在缓存数据则不做任何变化
		}
		
		return LoadedResult.UPDATE_F;//获取网络数据成功，强制更新SuccessView
	}

	@Override
	protected View initSuccessView() {
		TextView tv = new TextView(getActivity());
		tv.setText("hahahah"+result);
		return tv;
		
		
	/*	if (mDatas == null || mDatas.size() == 0) {// 无数据，返回null，容器将加载默认view
			return null;
		}

		ListView listView = new ListView(UIUtils.getContext());

		listView.setCacheColorHint(Color.TRANSPARENT);
		listView.setFastScrollEnabled(true);

		HomeAdapter homeAdapter = new HomeAdapter(mDatas);
		homeAdapter
				.setOnCreateHolderListener(new OnCreateHolderListener<String>() {

					@Override
					public BaseHolder<String> creatHolder() {
						return new TestHolder();
					}

				});
		// adapter最好先setOnCreateHolderListener再赋值给listView
		listView.setAdapter(homeAdapter);
		return listView;*/
	}

	// adapter作为内部类的好处，方便调用父类数据
	class HomeAdapter extends BaseAdapterKwt<String> {

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
