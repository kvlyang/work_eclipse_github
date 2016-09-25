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

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		loadingPager = new LoadingPager(UIUtils.getContext()) {

			@Override
			public LoadedResult initData() {
				return BaseFragment.this.initData();
			}

			@Override
			public View initSuccessView() {
				return BaseFragment.this.initSuccessView();
			}
		};

		loadingPager.loadData();
		return loadingPager;
	}

	protected abstract View initSuccessView();

	protected abstract LoadedResult initData();
	
	public LoadingPager getLoadingPager() {
		return loadingPager;
	}

}
