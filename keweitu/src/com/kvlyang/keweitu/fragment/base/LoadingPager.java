package com.kvlyang.keweitu.fragment.base;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.factory.ThreadPoolFactory;
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
	public static final int STATE_FORCED_UPDATE = 3; // 不管是否已有数据显示，强制更新
	public static final int STATE_UPDATE = 4;// 已有数据显示时，不会更新
	public static final int STATE_CACHE = 5;// 当前界面有缓存数据

	public int currentState = STATE_LOADING;
	private Object currentData = null;
	private View loadingView;
	private View errorView;
	private View emptyView;
	private View successView;
	boolean successViewFlag = false; // 数据界面是否已加载到frame容器

	public boolean httpDowning = false; // 是否网络正在更新中(非线程安全)防止重复任务

	public LoadingPager(Context context) {
		super(context);
		initCommonView();
	}

	private void initCommonView() {
	//	Log.e("keweituBug", "initCommonView()");
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

	private void refreshUI(LoadedResult state, Object data) {
		currentState = state.getState();// 使状态和界面更新在同一主线程执行
		currentData = data;
		refreshUI();
	}

	private void refreshUI() {
	//	Log.e("keweituBug", "refreshUI()");
		if (successView != null) { // 优先显示历史信息 History Info
			if (currentState == STATE_FORCED_UPDATE) {// 强制刷新view
				// 界面强制更新成功后更改状态，减少无意义的刷新判断
				currentState = STATE_CACHE;

				View tempView = initSuccessView(currentData);
				if (tempView == null) { // 更新界面出现异常
					// 应该处理：默认显示错误界面（可添加广告或错误提示）
					int doSomethingForcedUpdateErr = 1;
					if (doSomethingForcedUpdateErr != 0) {
						currentState = STATE_ERROR;
						// 显示错误界面并删除掉数据view
						this.removeView(successView);
						successView = null;
						successViewFlag = false;
					}
					Log.e("keweituBug", "initSuccessView() is null");
				} else {
					this.removeView(successView);
					successView = tempView;
					this.addView(successView);
					successViewFlag = true;

				}
			} else if (currentState == STATE_UPDATE) {
				currentState = STATE_CACHE;
				if (initSuccessView(currentData) == null) { // 界面出现异常
					// 不做处理显示原缓存数据界面,只在log提示异常
					/*
					 * int doSomethingUpdateErr = 0; if (doSomethingUpdateErr !=
					 * 0) { currentState = STATE_ERROR;
					 * this.removeView(successView); successView = null;
					 * successViewFlag = false; }
					 */
					Log.e("keweituBug", "initSuccessView() is null");
				}
			}

			if (!successViewFlag) {
				successViewFlag = true;
			}

		} else { // successView ==null
			if (successViewFlag) {// 发现不合逻辑的未知异常
				successViewFlag = false;
				Log.e("keweituBug",
						"successView is null;successViewFlag = true");
			}

			if (currentState == STATE_FORCED_UPDATE
					|| currentState == STATE_UPDATE) {
				// 第一次更新界面
				View tempView = initSuccessView(currentData);
				if (tempView == null) { // 待更新界面解析出现异常
					int doSomethingFirstUpdateErr = 1;
					if (currentState == STATE_FORCED_UPDATE) {
						// 网络更新失败，并且缓存数据也为空时，处理：默认显示错误界面（可添加广告或错误提示）
						doSomethingFirstUpdateErr = 1;
					} else {
						// 发现缓存数据为空时，默认显示empty页，等后续网络更新结果 处理：
						doSomethingFirstUpdateErr = 0;
					}

					if (doSomethingFirstUpdateErr != 0) {
						currentState = STATE_ERROR;
						successView = null;
						successViewFlag = false;
					} else if (doSomethingFirstUpdateErr == 0) {
						if(httpDowning){
							currentState = STATE_LOADING;
						}else if(currentState != STATE_ERROR){
							currentState = STATE_EMPTY;
						}
						successView = null;
						successViewFlag = false;
					}
					Log.e("keweituBug",
							"initSuccessView() is null when loading first");
				} else {
					successView = tempView;
					this.addView(successView);
					successViewFlag = true;
					// 界面强制更新成功后更改状态，减少无意义的强制刷新判断
					currentState = STATE_CACHE;
				}
			}
		}

		// 只要有数据view就显示数据页 STATE_CACHE
		if (successViewFlag) {
			currentState = STATE_CACHE;
			successView.setVisibility(View.VISIBLE);
			loadingView.setVisibility(View.GONE);
			emptyView.setVisibility(View.GONE);
			errorView.setVisibility(View.GONE);
			return;
		} else {
			if (successView != null) {
				Log.e("keweituBug",
						"successView is not null;successViewFlag = false");
			}
		}

		if (currentState == STATE_LOADING) {
			loadingView.setVisibility(View.VISIBLE);
		} else {
			loadingView.setVisibility(View.GONE);
		}
		if (currentState == STATE_EMPTY) {
			emptyView.setVisibility(View.VISIBLE);
		} else {
			emptyView.setVisibility(View.GONE);
		}
		if (currentState == STATE_ERROR) {
			errorView.setVisibility(View.VISIBLE);
		} else {
			errorView.setVisibility(View.GONE);
		}

	}

	// 先判断succeView是否已经存在数据，存在则不执行从本地缓存加载
	public void loadDataCaches() {
		if (currentState == STATE_UPDATE || currentState == STATE_FORCED_UPDATE
				|| currentState == STATE_CACHE) {
			return;
		} else {
			currentState = STATE_LOADING;// 如果界面无数据，先显示loading页,表示正在加载缓存中
			refreshUI();
		}
		// new Thread(new LoadDataCachesTask()).start();
		ThreadPoolFactory.getNormalPool().execute(new LoadDataCachesTask());
	}

	class LoadDataCachesTask implements Runnable {
		@Override
		public void run() {
		//	Log.e("keweituBug", "LoadDataTask ");
			final LoadedDataAndView dataView = initDataFromCaches();
			final LoadedResult state = dataView.state; 
			final Object data = dataView.data;
			UIUtils.postUiTaskSafely(new Runnable() {

				@Override
				public void run() {
					// 如果无缓存数据，默认依然显示loading页，想显示空白页请在initDataFromCache中返回STATE_EMPTY
					refreshUI(state,data);
				}
			});
		}

	}

	// 不管succeView是否已经存在显示数据，从本地缓存加载并强制更新,无缓存则显示err页，一般情况应该用不到（频繁显示错误页，用户体验不好）
	public void loadDataCachesForce() {
		currentState = STATE_LOADING;// 如果界面无数据，先显示loading页,表示正在加载缓存中
		refreshUI();
		//new Thread(new LoadDataCachesForceTask()).start();
		ThreadPoolFactory.getNormalPool().execute(new LoadDataCachesForceTask());
	}

	class LoadDataCachesForceTask implements Runnable {

		@Override
		public void run() {
		//	Log.e("keweituBug", "LoadDataCachesForceTask ");
			final LoadedDataAndView dataView = initDataFromCaches();
			final Object data = dataView.data;
			// 不管是否获得缓存数据，反正要强制更新successView界面，哪怕initSuccessView为null就显示err界面
			final LoadedResult state = LoadedResult.UPDATE_F;

			UIUtils.postUiTaskSafely(new Runnable() {

				@Override
				public void run() {
					refreshUI(state,data);
				}
			});
		}

	}

	/*
	 * 从网路加载，根据返回值决定是否强制更新successView（适合应用更新网络数据，更新到缓存需自己实现）
	 */
	public void loadDataHttp() {
		if (httpDowning) {
			return; // 不重复下载任务
		}
		httpDowning = true;
		currentState = STATE_LOADING;// 如果界面无数据，先显示loading页,表示正在获取网络数据
		refreshUI();
		//new Thread(new LoadDataHttpTask()).start();
		ThreadPoolFactory.getNormalPool().execute(new LoadDataHttpTask());
	}

	class LoadDataHttpTask implements Runnable {
		// 执行initDataFromHttp获取最新数据，根据返结果决定是否要强制更新successView
		@Override
		public void run() {
		//	Log.e("keweituBug", "LoadDataTaskForce ");
			// （如果网络更新失败，initSuccessView为null，最好返回STATE_ERROR无任何缓存时显示错误，而不是UPDATE_F）
			// 除非必须实时更新的显示页面，不推荐无新数据时UPDATE_F强制更新，在有缓存显示情况下，会取消缓存数据显示页
			final LoadedDataAndView dataView = initDataFromHttp();
			final LoadedResult state = dataView.state; 
			final Object data = dataView.data;
			/*
			 * 强制更新succeView if(state == LoadedResult.UPDATE){ state
			 * =LoadedResult.UPDATE_F; }
			 */
			UIUtils.postUiTaskSafely(new Runnable() {

				@Override
				public void run() {
					httpDowning = false;
					if (state == null) {
						Log.e("keweituBug", "refreshUI(state) state is null ");
					}
					refreshUI(state,data);
				}
			});
		}

	}

	// 从网路加载，强制更新successView（适合应用实时更新网络数据，只要有网络更新异常就显示错误页，而不是用缓存页掩盖，用户体验差）
	public void loadDataHttpForce() {
		if (httpDowning) {
			return; // 不重复下载任务
		}
		httpDowning = true;
		currentState = STATE_LOADING;
		refreshUI();
		//new Thread(new LoadDataHttpForceTask()).start();
		ThreadPoolFactory.getNormalPool().execute(new LoadDataHttpForceTask());
	}

	class LoadDataHttpForceTask implements Runnable {
		// 执行initDataFromHttp获取最新数据，根据返结果决定是否要更新successView（如果网络更新失败，不需要更新view）
		@Override
		public void run() {
		//	Log.e("keweituBug", "LoadDataTaskForce ");
			LoadedDataAndView dataView = initDataFromHttp();
			final Object data = dataView.data;
			// 不管网络更新与否，强制更新succeView
			final LoadedResult state = LoadedResult.UPDATE_F;
			
			UIUtils.postUiTaskSafely(new Runnable() {

				@Override
				public void run() {
					httpDowning = false;
					refreshUI(state,data);
				}
			});
		}

	}

	public abstract LoadedDataAndView initDataFromCaches();

	public abstract LoadedDataAndView initDataFromHttp();

	public abstract View initSuccessView(Object data);

	public enum LoadedResult {
		UPDATE_F(STATE_FORCED_UPDATE), UPDATE(STATE_UPDATE), ERROR(STATE_ERROR), EMPTY(
				STATE_EMPTY), LOADING(STATE_LOADING);
		int state;

		public int getState() {
			return state;
		}

		private LoadedResult(int state) {
			this.state = state;
		}
	}
	
	
}
