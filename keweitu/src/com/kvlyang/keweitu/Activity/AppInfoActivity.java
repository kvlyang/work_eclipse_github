package com.kvlyang.keweitu.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.bean.AppDetailBean;
import com.kvlyang.keweitu.fragment.base.LoadedDataAndView;
import com.kvlyang.keweitu.fragment.base.LoadingPager;
import com.kvlyang.keweitu.holder.AppDetailInfoHolder;
import com.kvlyang.keweitu.protocal.HttpProtocal;
import com.kvlyang.keweitu.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AppInfoActivity extends ActionBarActivity{

	@ViewInject(R.id.app_detail_bottom)
	FrameLayout						mContainerBottom;

	@ViewInject(R.id.app_detail_des)
	FrameLayout						mContainerDes;

	@ViewInject(R.id.app_detail_info)
	FrameLayout						mContainerInfo;

	@ViewInject(R.id.app_detail_pic)
	FrameLayout						mContainerPic;

	@ViewInject(R.id.app_detail_safe)
	FrameLayout						mContainerSafe;
	
	private String mPackageName;
	Context context;
	View view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		LoadingPager loadingPager = new LoadingPager(context){

			@Override
			public LoadedDataAndView initDataFromCaches() {
				return null; //未做缓存，忽略
			}

			@Override
			public LoadedDataAndView initDataFromHttp() {
				LoadedDataAndView dataView = new LoadedDataAndView();
				AppDetailBean appBean;
				String result;

				try {
					// 解析json网络数据
					result =  HttpProtocal.loadHttpData("detail","packageName",mPackageName);
				//	Log.e("keweitubug",">>>http "+ result);
					Gson gson = new Gson();
					appBean = gson.fromJson(result, AppDetailBean.class);
					// 异常处理，显示网络异常界面或缓存
					if (appBean == null) {
						dataView.state = LoadedResult.ERROR;
						dataView.data = null;
						return dataView;
					}


				} catch (Exception e) {
				//	Log.e("keweituBug", "HttpUtils() error AppInfoActivity;");
					// e.printStackTrace();
					dataView.state = LoadedResult.ERROR;
					dataView.data = null;
					return dataView;// 获取网络数据失败
				}
				
				// 获取网络数据成功，强制更新SuccessView
				dataView.state = LoadedResult.UPDATE_F;
				dataView.data = appBean;
				return dataView;
			}

			@Override
			public View initSuccessView(Object data) {
				
				
				if (data == null || !(data instanceof AppDetailBean)) {
					return null;
				}
				
				
				
				
				AppDetailBean mData = (AppDetailBean)data;
				AppDetailInfoHolder appDetailInfoHolder = new AppDetailInfoHolder();
					

				appDetailInfoHolder.setDataAndRefreshHolderView(mData);
				if(mContainerInfo==null){
					Log.e("keweituBug", "mContainerInfo==null");
				}
				mContainerInfo.addView(appDetailInfoHolder.getHolderView());
				
				
				return view;
			}
			
		};
		setContentView(loadingPager);
		
		view = View.inflate(UIUtils.getContext(), R.layout.activity_detail, null);
		ViewUtils.inject(this, view);
		loadingPager.loadDataHttp();
	}

	private void init() {
		context = this;
		mPackageName = getIntent().getStringExtra("packageName");
		
	}
	
	
	
}
