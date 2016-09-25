package com.kvlyang.keweitu.fragment.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.utils.UIUtils;
/*
	状态：
		加载页面
		空页面
		错误页面
		成功页面*/
public class LoadingPager extends FrameLayout {
	public static final int STATE_LOADING = 0;
	public static final int STATE_EMPTY = 1;
	public static final int STATE_ERROR = 2;
	public static final int STATE_SUCCESS = 3;

	public int currentState = STATE_LOADING;
	private View loadingView;
	private View errorView;
	private View emptyView;
	
	public LoadingPager(Context context) {
		super(context);
		initCommonView();
	}

	private void initCommonView() {
		loadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
		this.addView(loadingView);
		errorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
		this.addView(errorView);
		emptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
		this.addView(emptyView);
		
		refreshUI();
	}

	private void refreshUI() {
		if(currentState == STATE_LOADING){
			loadingView.setVisibility(View.VISIBLE);
		}else{
			loadingView.setVisibility(View.GONE);
		}
		if(currentState == STATE_EMPTY){
			emptyView.setVisibility(View.VISIBLE);
		}else{
			emptyView.setVisibility(View.GONE);
		}
		if(currentState == STATE_ERROR){
			errorView.setVisibility(View.VISIBLE);
		}else{
			errorView.setVisibility(View.GONE);
		}
	}

	public void loadData(){
		new Thread(new LoadDataTask()).start();
	}
	
	class LoadDataTask implements Runnable{

		@Override
		public void run() {
			
		}
		
	}
	
}
