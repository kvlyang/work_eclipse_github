package com.kvlyang.keweitu.fragment;

import com.kvlyang.keweitu.utils.UIUtils;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(this.getClass().toString());
		return tv;
	}
}
