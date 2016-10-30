package com.kvlyang.keweitu.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadedDataAndView;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.protocal.HttpProtocal;
import com.kvlyang.keweitu.utils.CacheDataLoad;
import com.kvlyang.keweitu.utils.UIUtils;
import com.kvlyang.keweitu.views.flyoutin.ShakeListener;
import com.kvlyang.keweitu.views.flyoutin.ShakeListener.OnShakeListener;
import com.kvlyang.keweitu.views.flyoutin.StellarMap;



public class RecommentFragment extends BaseFragment {
	private ShakeListener	mShakeListener;
	
	@Override
	protected LoadedDataAndView initDataFromCaches() {
		//SystemClock.sleep(3000);
		LoadedDataAndView dataView = new LoadedDataAndView();
		String result = CacheDataLoad.loadDataFromFile("recommend", 0);
	//	Log.e("keweitubug",">>>cache "+ result);
		if( result == null){
			dataView.state = LoadedResult.UPDATE;
			dataView.data = null;
			return dataView;
		}else{
			Recommend Recommend = new Recommend();
			Recommend.mDatas = parseJson(result);
		//	Log.e("keweitubug",">>>cache size "+ Recommend.mDatas.size());
			dataView.state = LoadedResult.UPDATE;
			dataView.data = Recommend;
			return dataView;
		}
		
	}

	@Override
	protected LoadedDataAndView initDataFromHttp() {
		LoadedDataAndView dataView = new LoadedDataAndView();
		Recommend Recommend = new Recommend();
		String result;
		
	//	SystemClock.sleep(4000);
		//如果缓存数据未过期，不从网络更新，节省流量
		if (!CacheDataLoad.isCachaOutTime("recommend", 0)){
			dataView.state = LoadedResult.EMPTY;
			dataView.data = null;
			return dataView;
		}
			
			
		try {
			// 解析json网络数据
			result =  HttpProtocal.loadHttpData("recommend",0);
			//Log.e("keweitubug",">>>http "+ result);
			List<String> RecommendBeans = parseJson(result);
			// 异常处理，显示网络异常界面或缓存
			if (RecommendBeans == null) {
				dataView.state = LoadedResult.ERROR;
				dataView.data = null;
				return dataView;
			}

			Recommend.mDatas = RecommendBeans;


		} catch (Exception e) {
			Log.e("keweituBug", "HttpUtils() error;");
			// e.printStackTrace();
			dataView.state = LoadedResult.ERROR;
			dataView.data = null;
			return dataView;// 获取网络数据失败
		}
		//保存到缓存
		CacheDataLoad.writeDataToFile("recommend", 0, result);
		
		// 获取网络数据成功，强制更新SuccessView
		dataView.state = LoadedResult.UPDATE_F;
		dataView.data = Recommend;
		return dataView;
	}

	@Override
	protected View initSuccessView(Object data) {
		if (data == null || !(data instanceof Recommend)) {
			return null;
		}



		Recommend Recommend = (Recommend) data;

		// 无数据，返回null，容器将加载默认view
		if (Recommend.mDatas == null || Recommend.mDatas.size() == 0) {
			return null;
		}
		
		// 返回成功的视图
				final StellarMap stellarMap = new StellarMap(UIUtils.getContext());

				// 设置adapter
				final RecommendAdapter adapter = new RecommendAdapter(Recommend.mDatas);
				stellarMap.setAdapter(adapter);

				// 设置第一页的时候显示
				stellarMap.setGroup(0, true);
				// 设置把屏幕拆分成多少个格子
				stellarMap.setRegularity(15, 20);// 总的就有300个格子

				mShakeListener = new ShakeListener(UIUtils.getContext());
				mShakeListener.setOnShakeListener(new OnShakeListener() {

					@Override
					public void onShake() {
						int groupIndex = stellarMap.getCurrentGroup();
						if (groupIndex == adapter.getGroupCount() - 1) {
							groupIndex = 0;
						} else {
							groupIndex++;
						}
						stellarMap.setGroup(groupIndex, true);
					}
				});
				return stellarMap;


	
	}

	
	public List<String> parseJson(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, new TypeToken<List<String>>() {
		}.getType());
	}
	
	public class Recommend {
		public List<String> mDatas;
		public String result; // 网络数据
		
		public Recommend() {
			mDatas = new ArrayList<String>();

		}
	}
	
	class RecommendAdapter implements StellarMap.Adapter {
		private static final int	PAGER_SIZE	= 15;	// 每页显示多少条数据
		private List<String> mDatas;
		public RecommendAdapter(List<String> list){
			mDatas = list;
		}
		
		public int getGroupCount() {// 有多少组
			int groupCount = mDatas.size() / PAGER_SIZE;
			// 如果不能整除,还有余数的情况
			if (mDatas.size() % PAGER_SIZE > 0) {// 有余数
				groupCount++;
			}
			return groupCount;
		}

		@Override
		public int getCount(int group) {// 每组有多少条数据
			if (group == getGroupCount() - 1) {// 来到了最后一组
				// 是否有余数
				if (mDatas.size() % PAGER_SIZE > 0) {// 有余数

					return mDatas.size() % PAGER_SIZE;// 返回具体的余数值就可以

				}
			}
			return PAGER_SIZE;// 0-15
		}

		@Override
		public View getView(int group, int position, View convertView) {// 返回具体的view
			TextView tv = new TextView(UIUtils.getContext());
			// group:代表第几组
			// position:几组中的第几个位置
			int index = group * PAGER_SIZE + position;
			tv.setText(mDatas.get(index));

			// random对象
			Random random = new Random();
			// 随机大小
			tv.setTextSize(random.nextInt(6) + 15);// 15-21
			// 随机的颜色
			int alpha = 255;//
			int red = random.nextInt(180) + 30;// 30-210
			int green = random.nextInt(180) + 30;
			int blue = random.nextInt(180) + 30;
			int argb = Color.argb(alpha, red, green, blue);
			tv.setTextColor(argb);

			int padding = UIUtils.dip2Px(5);
			tv.setPadding(padding, padding, padding, padding);

			return tv;
		}

		@Override
		public int getNextGroupOnPan(int group, float degree) {
			// TODO
			return 0;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			// TODO
			return 0;
		}

	}
}
