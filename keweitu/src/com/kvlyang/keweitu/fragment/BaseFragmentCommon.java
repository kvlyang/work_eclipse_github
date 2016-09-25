package com.kvlyang.keweitu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragmentCommon extends Fragment {

	//共有属性和方法
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		init1Create();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return init2View();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		init3Data();
		init4Listener();
		super.onActivityCreated(savedInstanceState);
	}

	protected void init1Create() {
		// TODO Auto-generated method stub

	}

	protected abstract View init2View();

	protected void init3Data() {
		// TODO Auto-generated method stub

	}

	protected void init4Listener() {
		// TODO Auto-generated method stub

	}
}
