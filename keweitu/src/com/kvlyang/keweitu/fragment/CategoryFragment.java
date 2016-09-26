package com.kvlyang.keweitu.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.utils.UIUtils;



public class CategoryFragment extends BaseFragment {

	@Override
	protected LoadedResult initDataFromCaches() {
		SystemClock.sleep(2000);
		return LoadedResult.UPDATE;
	}
 
	@Override
	protected View initSuccessView() {
		ImageView iv = new ImageView(UIUtils.getContext());
		iv.setImageDrawable(UIUtils.getResource().getDrawable(R.drawable.icon_3));
		return null;
	}

	@Override
	protected LoadedResult initDataFromHttp() {
		return LoadedResult.UPDATE;
	}

	

	

	
}
