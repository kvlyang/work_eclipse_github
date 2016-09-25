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
public class BaseFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LoadingPager loadingPager = new LoadingPager(UIUtils.getContext());
		return loadingPager;
	}
	
	
}
