package com.kvlyang.keweitu.fragment.base;

import com.kvlyang.keweitu.utils.UIUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 状态：
 加载页面
 空页面
 错误页面
 成功页面*/
public abstract class BaseFragment extends Fragment {
	LoadingPager loadingPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(loadingPager == null){
			loadingPager = new LoadingPager(UIUtils.getContext()) {

				@Override
				public LoadedDataAndView initDataFromCaches() {
					return BaseFragment.this.initDataFromCaches();
				}
				
				@Override
				public LoadedDataAndView initDataFromHttp() {
					return BaseFragment.this.initDataFromHttp();
				}

				@Override
				public View initSuccessView(Object data) {
					return BaseFragment.this.initSuccessView(data);
				}
				
			};
			//初始化 预先从缓存读数据
			loadingPager.loadDataCaches();

		}else{
			((ViewGroup)loadingPager.getParent()).removeView(loadingPager);
		}
		
		return loadingPager;
	}


	protected abstract View initSuccessView(Object data);

	protected abstract LoadedDataAndView initDataFromCaches();
	protected abstract LoadedDataAndView initDataFromHttp();
	
	public LoadingPager getLoadingPager() {
		return loadingPager;
	}
	

}
