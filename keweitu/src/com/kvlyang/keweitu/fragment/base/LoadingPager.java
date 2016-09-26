package com.kvlyang.keweitu.fragment.base;

import android.content.Context;
import android.util.Log;
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
public abstract class LoadingPager extends FrameLayout {
	public static final int STATE_LOADING = 0;
	public static final int STATE_EMPTY = 1;
	public static final int STATE_ERROR = 2;
	public static final int STATE_FORCED_UPDATE = 3; //不管是否已有数据显示，强制更新
	public static final int STATE_UPDATE = 4;//已有数据显示时，不会更新
	
	public int currentState = STATE_LOADING;
	private View loadingView;
	private View errorView;
	private View emptyView;
	private View successView;
	boolean successViewFlag = false; //数据界面是否已加载到frame容器
	
	boolean LoadRule = true;//强制更新
	
	public LoadingPager(Context context) {
		super(context);
		initCommonView();
	}

	private void initCommonView() {
		Log.e("keweituBug", "initCommonView()");
		loadingView = View.inflate(UIUtils.getContext(),
				R.layout.pager_loading, null);
		this.addView(loadingView, 0);
		errorView = View.inflate(UIUtils.getContext(), R.layout.pager_error,
				null);
		this.addView(errorView);
		emptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty,
				null);
		this.addView(emptyView);

		refreshUI();
	}

	private void refreshUI() {
		Log.e("keweituBug", "refreshUI()");
		if(successView !=null ){	//优先显示历史信息 History Info
			if(currentState == STATE_FORCED_UPDATE){//强制刷新view
				View tempView = initSuccessView(); 
				if(tempView == null){	//待更新界面出现异常
					//应该处理：显示错误界面（可添加广告或错误提示）
					int doSomethingForcedUpdateErr = 1;
					if(doSomethingForcedUpdateErr != 0){
						currentState = STATE_ERROR;
						//显示错误界面并删除掉数据view减少内存占用
						this.removeView(successView);
						successView = null;
						successViewFlag = false;
					}
					Log.e("keweituBug", "initSuccessView() is null");
				}else{
					this.removeView(successView);
					successView = tempView;
					this.addView(successView);
					successViewFlag = true;
					//界面强制更新成功后更改状态，减少无意义的刷新
					currentState = STATE_UPDATE;
				}
			}else if(currentState == STATE_UPDATE){
				if(initSuccessView() == null){	//界面出现异常
					//不做处理则显示原历史数据界面,只在log提示
					int doSomethingUpdateErr = 0;
					if(doSomethingUpdateErr != 0){
						currentState = STATE_ERROR;
						this.removeView(successView);
						successView = null;
						successViewFlag = false;
					}
					Log.e("keweituBug", "initSuccessView() is null");
				}
			}
			
			if(!successViewFlag){ 
				this.addView(successView);
				successViewFlag = true;					
			}
			
		}else{  //successView ==null 
			if(successViewFlag){//发现不合逻辑的未知异常
				successViewFlag = false;
				Log.e("keweituBug", "successView is null;successViewFlag = true");
			}
			
			if(currentState == STATE_FORCED_UPDATE || currentState == STATE_UPDATE){
				//第一次更新界面
				View tempView = initSuccessView(); 
				if(tempView == null){	//待更新界面解析出现异常
					//不做处理则可能一直显示空界面
					//处理：显示错误界面（可添加广告或错误提示）
					int doSomethingFirstUpdateErr = 1;
					if(doSomethingFirstUpdateErr != 0){
						currentState = STATE_ERROR;
						successView = null;
						successViewFlag = false;
					}
					Log.e("keweituBug", "initSuccessView() is null when loading first");
				}else{
					successView = tempView;
					this.addView(successView);
					successViewFlag = true;
					//界面强制更新成功后更改状态，减少无意义的刷新
					currentState = STATE_UPDATE;
				}
			}
		}
		
		//只要有数据view就显示数据页 
		if(successViewFlag){ 
			successView.setVisibility(View.VISIBLE);
			loadingView.setVisibility(View.GONE);
			emptyView.setVisibility(View.GONE);
			errorView.setVisibility(View.GONE);
			return;
		}else{
			if(successView != null){
				Log.e("keweituBug", "successView is not null;successViewFlag = false");
			}
		}
		
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

	public void loadData() {
		currentState = STATE_LOADING;
		refreshUI();
		new Thread(new LoadDataTask()).start();
	}
	
	//强制更新succeView
	public void loadDataForce() {
		currentState = STATE_LOADING;
		refreshUI();
		new Thread(new LoadDataTaskForce()).start();
	}

	class LoadDataTask implements Runnable {
		//如果succesView已有数据显示，后台则不执行initData（节省流量）
		@Override
		public void run() {
			Log.e("keweituBug", "LoadDataTask ");
			if(currentState == STATE_UPDATE || currentState == STATE_FORCED_UPDATE){
				return;
			}
			LoadedResult state = initData();
			currentState = state.getState();
			UIUtils.postUiTaskSafely(new Runnable() {

				@Override
				public void run() {
					refreshUI();
				}
			});
		}

	}
	
	class LoadDataTaskForce implements Runnable {
		//一直执行initData获取最近数据，并强制更新successView（适合应用强行更新网络数据）
		@Override
		public void run() {
			Log.e("keweituBug", "LoadDataTaskForce ");
			LoadedResult state = initData();
			//强制更新succeView
			if(state == LoadedResult.UPDATE){
				state =LoadedResult.UPDATE_F;
			}
			currentState = state.getState();
			UIUtils.postUiTaskSafely(new Runnable() {

				@Override
				public void run() {
					refreshUI();
				}
			});
		}

	}

	public abstract LoadedResult initData();

	public abstract View initSuccessView();

	public enum LoadedResult {
		UPDATE_F(STATE_FORCED_UPDATE), UPDATE(STATE_UPDATE),
		ERROR(STATE_ERROR), EMPTY(STATE_EMPTY), LOADING(
				STATE_LOADING);
		int state;

		public int getState() {
			return state;
		}

		private LoadedResult(int state) {
			this.state = state;
		}
	}
}
