package com.kvlyang.keweitu.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadedDataAndView;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.protocal.HttpProtocal;
import com.kvlyang.keweitu.utils.CacheDataLoad;
import com.kvlyang.keweitu.utils.UIUtils;
import com.kvlyang.keweitu.views.FlowLayout;



public class HotFragment extends BaseFragment {

	@Override
	protected LoadedDataAndView initDataFromCaches() {
		//SystemClock.sleep(3000);
		LoadedDataAndView dataView = new LoadedDataAndView();
		String result = CacheDataLoad.loadDataFromFile("hot", 0);
		if( result == null){
			dataView.state = LoadedResult.UPDATE;
			dataView.data = null;
			return dataView;
		}else{
			Gson gson = new Gson();
			List<String> bean = gson.fromJson(result,new TypeToken<List<String>>(){}.getType());
			HotData HotData = new HotData();
			HotData.mDatas = bean;
			dataView.state = LoadedResult.UPDATE;
			dataView.data = HotData;
			return dataView;
		}
		
	}

	@Override
	protected LoadedDataAndView initDataFromHttp() {
		LoadedDataAndView dataView = new LoadedDataAndView();
		HotData HotData = new HotData();
		String result;
	//	SystemClock.sleep(4000);
		//如果缓存数据未过期，不从网络更新，节省流量
		if (!CacheDataLoad.isCachaOutTime("hot", 0)){
			dataView.state = LoadedResult.EMPTY;
			dataView.data = null;
			return dataView;
		}
			
			
		try {
			// 解析json网络数据
			result =  HttpProtocal.loadHttpData("hot",0);
			Gson gson = new Gson();
			List<String> bean = gson.fromJson(result,new TypeToken<List<String>>(){}.getType());
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
			HotData.mDatas = bean;

		} catch (Exception e) {
			Log.e("keweituBug", "HttpUtils() error;");
			// e.printStackTrace();
			dataView.state = LoadedResult.ERROR;
			dataView.data = null;
			return dataView;// 获取网络数据失败
		}
		//保存到缓存
		CacheDataLoad.writeDataToFile("hot", 0, result);
		
		// 获取网络数据成功，强制更新SuccessView
		dataView.state = LoadedResult.UPDATE_F;
		dataView.data = HotData;
		return dataView;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected View initSuccessView(Object data) {
		if (data == null || !(data instanceof HotData)) {
			return null;
		}

		/*
		 * 测试
		 * TextView tv = new TextView(getActivity());
		 * tv.setText("hahahah"+((HotData)data).result); return tv;
		 */

		HotData HotDatas = (HotData) data;

		// 无数据，返回null，容器将加载默认view
		if (HotDatas.mDatas == null || HotDatas.mDatas.size() == 0) {
			return null;
		}
		
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		FlowLayout fl = new FlowLayout(UIUtils.getContext());
		int padding = UIUtils.dip2Px(5);
		
		Random rand = new Random();
		for(String str:HotDatas.mDatas){
			TextView tv = new TextView(UIUtils.getContext());
			tv.setText(str);
			tv.setTextColor(Color.WHITE);
			tv.setTextSize(16);
			tv.setPadding(padding, padding, padding, padding);
	//		tv.setnormalDrawableResource(R.drawable.shape_hot_fl_tv);
			GradientDrawable normalDrawable = new GradientDrawable();
			GradientDrawable pressedDrawable = new GradientDrawable();
			normalDrawable.setColor(Color.argb(255,rand.nextInt(190)+30 , rand.nextInt(190)+30, rand.nextInt(190)+30));
			normalDrawable.setCornerRadius(UIUtils.dip2Px(6));
			pressedDrawable.setColor(Color.DKGRAY);
			pressedDrawable.setCornerRadius(UIUtils.dip2Px(6));
			StateListDrawable state = new StateListDrawable();
			state.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
			state.addState(new int[]{}, normalDrawable);
			tv.setBackgroundDrawable(state);
			tv.setClickable(true);
			fl.addView(tv);
		}
		scrollView.addView(fl);
		return scrollView;

	}


	
	public class HotData {
		public List<String> mDatas;
		public String result; // 网络数据
		
		public HotData() {
			mDatas = new ArrayList<String>();
		}
	}
	
}
