package com.kvlyang.keweitu.listview.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import com.kvlyang.keweitu.conf.Constants;
import com.kvlyang.keweitu.factory.ThreadPoolFactory;
import com.kvlyang.keweitu.listview.item.TestHolder;
import com.kvlyang.keweitu.utils.UIUtils;

import android.os.Handler;
import android.test.PerformanceTestCase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

/**
 * @author Administrator
 * @time 2015-7-16 上午9:58:55
 * @des TODO
 * 
 * @version $Rev: 38 $
 * @updateAuthor $Author: admin $
 * @updateDate $Date: 2015-07-19 09:59:23 +0800 (星期日, 19 七月 2015) $
 * @updateDes TODO
 */
public abstract class BaseAdapterKwt<ITEMBEANTYPE> extends BaseAdapter
		implements OnItemClickListener {
	public List<ITEMBEANTYPE> mListData = new ArrayList<ITEMBEANTYPE>();
	public static final int ViewType_normal = 0;
	public static final int ViewType_loadMore = 1;
	BaseHolder<Integer> loadMoreHolder = new LoadMoreHolder();
	public int loadState = LoadMoreHolder.STATE_clickupdate;;
	boolean hasLoadMoreFlag = true;

	OnCreateHolderListener<ITEMBEANTYPE> onCreateHolderListener;// 监听itemView的创建

	public BaseAdapterKwt(List<ITEMBEANTYPE> listData) {
		super();
		mListData = listData;
	}

	@Override
	public int getCount() {
		if (mListData != null) {
			return mListData.size()+1;
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mListData != null) {
			return mListData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO
		return position;
	}

	/* ============listView里面可以显示几种viewType====================== */

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		// 如果滑到底部，对应的ViewType是加载更多
		if (position == getCount() - 1) {
			return ViewType_loadMore;
		}
		return ViewType_normal;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);

		if (convertView == null) {
			if (onCreateHolderListener != null) {
				if (ViewType_normal == type) {
					return creatNomalView(position, type);
				} else if (ViewType_loadMore == type) {
					return creatLoadMoreView(type);
				} else {
					Log.e("keweituBug", "error type in BaseAdapterKwt");
					return null;
				}
			} else {
				Log.e("keweituBug",
						"onCreateHolderListener = null in BaseAdapterKwt");
				return null;
			}

		} else {
			if (ViewType_normal == type) {
				if (convertView.getTag() != loadMoreHolder) {
					BaseHolder<ITEMBEANTYPE> holder = (BaseHolder<ITEMBEANTYPE>) convertView
							.getTag();
					holder.setDataAndRefreshHolderView(mListData.get(position));
					return holder.getHolderView();
				} else {
					return creatNomalView(position, type);
				}

			} else if (ViewType_loadMore == type) {
				return creatLoadMoreView(type);
			} else {
				Log.e("keweituBug", "error type in BaseAdapterKwt");
				return null;
			}

		}

	}
	
	

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		if(getItemViewType(position)==ViewType_loadMore){
			PerformLoadMore();
		}else {
			onNormalItemClick(parent,view,position,id);
		}
	}
	
	public void onNormalItemClick(AdapterView<?> parent, View view, int position,
			long id){
		
	}

	View creatNomalView(int position, int type) {
		BaseHolder<ITEMBEANTYPE> holder = null;
		holder = (BaseHolder<ITEMBEANTYPE>) onCreateHolderListener
				.creatHolder(type);
		if (holder == null) {
			Log.e("keweituBug", "creatHolder is null in BaseAdapterKwt");
		}
		holder.setDataAndRefreshHolderView(mListData.get(position));
		return holder.getHolderView();
	}

	View creatLoadMoreView(int type) {
		if (loadMoreHolder == null) {
			loadMoreHolder = new LoadMoreHolder();
		}
		if (loadMoreHolder == null) {
			Log.e("keweituBug", "creatHolder is null in BaseAdapterKwt");
		}
		if(!hasLoadMoreFlag){
			loadState = LoadMoreHolder.STATE_none;
		}
		loadMoreHolder.setDataAndRefreshHolderView(loadState);
		return loadMoreHolder.getHolderView();
	}

	protected void PerformLoadMore() {
		if(loadState == LoadMoreHolder.STATE_loading)return;
		Log.e("keweituBug", "perforemLoadMore");
		loadState = LoadMoreHolder.STATE_loading;
		creatLoadMoreView(ViewType_loadMore);
		
		if(hasLoadMore()){
			ThreadPoolFactory.getNormalPool().execute(new LoadMoreTask());
		}
		

	}

	private boolean hasLoadMore() {
		// TODO Auto-generated method stub
		return hasLoadMoreFlag;
	}
	
	public void setHasLoadMore(boolean enable){
		hasLoadMoreFlag = enable;
	}

	public void setOnCreateHolderListener(
			OnCreateHolderListener<ITEMBEANTYPE> listener) {
		onCreateHolderListener = listener;
	}

	public interface OnCreateHolderListener<ITEMBEANTYPE> {
		public BaseHolder<ITEMBEANTYPE> creatHolder(int type);
	}
	
	public  List<ITEMBEANTYPE> OnLoadMore() throws Exception{
		return null;
	}

	class LoadMoreTask implements Runnable {
		int state;
		List<ITEMBEANTYPE> loadMoreDatas;
		@Override
		public void run() {
			try {
				loadMoreDatas = OnLoadMore();
			
			if(loadMoreDatas == null){
				state = LoadMoreHolder.STATE_retry;
			}else if(loadMoreDatas.size() == 0||loadMoreDatas.size()<Constants.PAGESIZE){
				state = LoadMoreHolder.STATE_finish;
			}else if(loadMoreDatas.size() > 0){
				state = LoadMoreHolder.STATE_clickupdate;
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				state = LoadMoreHolder.STATE_retry;
			}
			//更新加载条状态
			UIUtils.postUiTaskSafely(new Runnable() {
				
				@Override
				public void run() {
					loadState = state;
					if(loadState == LoadMoreHolder.STATE_finish){
						//适当延时，防止过快刷新
						new Handler().postDelayed(new Runnable() {
							
							@Override
							public void run() {
								if(loadState == LoadMoreHolder.STATE_finish)
								loadState = LoadMoreHolder.STATE_clickupdate;
								
							}
						}, 4000);
					}
					
					if(state != LoadMoreHolder.STATE_retry && loadMoreDatas !=null && loadMoreDatas.size()>0){
						//更新listview显示
						mListData.addAll(loadMoreDatas);
						notifyDataSetChanged();
					}
					//更新状态
					creatLoadMoreView(ViewType_loadMore);
				}
			});
			
		}

	}
}
