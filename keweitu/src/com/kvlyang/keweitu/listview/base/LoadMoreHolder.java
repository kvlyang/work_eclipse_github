package com.kvlyang.keweitu.listview.base;

import android.view.View;
import android.widget.LinearLayout;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LoadMoreHolder extends BaseHolder<Integer> {
	@ViewInject(R.id.item_loadmore_container_loading)
	LinearLayout containerLoading;
	@ViewInject(R.id.item_loadmore_container_retry)
	LinearLayout containerRetry;
	@ViewInject(R.id.item_loadmore_container_clickupdate)
	LinearLayout containerUpdate;

	public static final int STATE_loading = 0;
	public static final int STATE_retry = 1;
	public static final int STATE_none = 2;
	public static final int STATE_finish = 3;
	public static final int STATE_clickupdate = 4;

	@Override
	public View initHolderView() {
		View view = View.inflate(UIUtils.getContext(), R.layout.item_loadmore,
				null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshHolderView(Integer LoadState) {
		containerLoading.setVisibility(View.GONE);
		containerRetry.setVisibility(View.GONE);
		containerUpdate.setVisibility(View.GONE);

		switch (LoadState) {
		case STATE_loading:
			containerLoading.setVisibility(View.VISIBLE);
			break;
		case STATE_retry:
			containerRetry.setVisibility(View.VISIBLE);
			break;
		case STATE_clickupdate:
			containerUpdate.setVisibility(View.VISIBLE);
			break;
		case STATE_none:
			break;
		case STATE_finish:
			break;
		default:
			break;
		}

	}
}
