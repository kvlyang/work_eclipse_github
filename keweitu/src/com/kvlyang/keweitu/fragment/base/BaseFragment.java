package com.kvlyang.keweitu.fragment.base;

import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
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
	public boolean httpDoneFirst = false; //判断是否尝试过一次网络更新
	public boolean httpDoneFinish = false; //是否网络更新成功过
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(loadingPager == null){
			loadingPager = new LoadingPager(UIUtils.getContext()) {

				@Override
				public LoadedResult initDataFromCaches() {
					return BaseFragment.this.initDataFromCaches();
				}
				
				@Override
				public LoadedResult initDataFromHttp() {
					return BaseFragment.this.initDataFromHttp();
				}

				@Override
				public View initSuccessView() {
					return BaseFragment.this.initSuccessView();
				}
				
			};

		}else{
			((ViewGroup)loadingPager.getParent()).removeView(loadingPager);
		}
		//初始化 预先从缓存读数据
		loadingPager.loadDataCaches();
		return loadingPager;
	}


	protected abstract View initSuccessView();

	protected abstract LoadedResult initDataFromCaches();
	protected abstract LoadedResult initDataFromHttp();
	
	public LoadingPager getLoadingPager() {
		return loadingPager;
	}
	

}
