package com.kvlyang.keweitu.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.fragment.base.LoadingPager.LoadedResult;
import com.kvlyang.keweitu.utils.UIUtils;



public class HomeFragment extends BaseFragment {

	@Override
	protected LoadedResult initData() {
		SystemClock.sleep(500);
		return LoadedResult.UPDATE_F;
	}
 
	@Override
	protected View initSuccessView() {
		ImageView iv = new ImageView(UIUtils.getContext());
		iv.setImageDrawable(UIUtils.getResource().getDrawable(R.drawable.icon_2));
		return iv;
	}

	

	

	
}
