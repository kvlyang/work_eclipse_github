package com.kvlyang.keweitu.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadedDataAndView;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.utils.UIUtils;



public class HotFragment extends BaseFragment {

	@Override
	protected LoadedDataAndView initDataFromCaches() {
		LoadedDataAndView dataView = new LoadedDataAndView();
		SystemClock.sleep(4000);
		dataView.state = LoadedResult.UPDATE;
		dataView.data = null;
		return dataView; 
	}
 
	@Override
	protected View initSuccessView(Object data) {
		ImageView iv = new ImageView(UIUtils.getContext());
		iv.setImageDrawable(UIUtils.getResource().getDrawable(R.drawable.icon_3));
		return iv;
	}

	@Override
	protected LoadedDataAndView initDataFromHttp() {
		LoadedDataAndView dataView = new LoadedDataAndView();
		SystemClock.sleep(6000);
		dataView.state = LoadedResult.UPDATE;
		dataView.data = null;
		return dataView;
	}
}
