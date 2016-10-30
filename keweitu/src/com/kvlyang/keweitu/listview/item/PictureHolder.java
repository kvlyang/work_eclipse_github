package com.kvlyang.keweitu.listview.item;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.conf.UrlUpdate;
import com.kvlyang.keweitu.listview.base.BaseHolder;
import com.kvlyang.keweitu.utils.BitmapHelper;
import com.kvlyang.keweitu.utils.UIUtils;
import com.kvlyang.keweitu.views.InnerViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PictureHolder extends BaseHolder<List<String>> {

	@ViewInject(R.id.item_home_picture_pager)
	InnerViewPager viewPager;
	@ViewInject(R.id.item_home_picture_container_indicator)
	LinearLayout containerIndicator;

	private List<String> list;
	
	int scrollCount = 0;

	@Override
	public View initHolderView() {
		View itemView = View.inflate(UIUtils.getContext(),
				R.layout.item_home_picture, null);
		// 注入
		ViewUtils.inject(this, itemView);

		return itemView;
	}

	@Override
	public void refreshHolderView(List<String> itemDatas) {
		list = itemDatas;
		viewPager.setAdapter(new PictureAdapter());

		LayoutParams params = new LayoutParams(UIUtils.dip2Px(5),
				UIUtils.dip2Px(5));
		params.leftMargin = UIUtils.dip2Px(5);
		params.bottomMargin = UIUtils.dip2Px(5);
		containerIndicator.removeAllViews();
		int selectNum = viewPager.getCurrentItem();
		for (int i = 0; i < list.size(); i++) {
			View indicatorView = new View(UIUtils.getContext());
			if(selectNum%list.size() == i){
				indicatorView.setBackgroundResource(R.drawable.indicator_selected);
			}else{
				indicatorView.setBackgroundResource(R.drawable.indicator_normal);
			}
			

			containerIndicator.addView(indicatorView, params);

		}

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				position = position % list.size();
				for (int i = 0; i < list.size(); i++) {
					View indicatorView = containerIndicator.getChildAt(i);
					
					if(i == position){
						indicatorView
						.setBackgroundResource(R.drawable.indicator_selected);
					}else{
						indicatorView
						.setBackgroundResource(R.drawable.indicator_normal);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		viewPager.setCurrentItem(Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2 % list.size());
		
		final AutoScrollTask autoScrollTask = new AutoScrollTask();
		autoScrollTask.start();
		
		//触摸时移除轮播任务
		viewPager.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()){
				case MotionEvent.ACTION_DOWN:
					autoScrollTask.stop();
					break;
				case MotionEvent.ACTION_UP:
					scrollCount=0;
					autoScrollTask.start();
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				}
				return false;
			}
		});
	}

	@Override
	public View getHolderView() {
		// TODO Auto-generated method stub
		return super.getHolderView();
	}

	class PictureAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if (list != null) {
				//return list.size();
				return Integer.MAX_VALUE;
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % list.size();
			ImageView iv = new ImageView(UIUtils.getContext());
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(R.drawable.ic_default);
			BitmapHelper.display(iv,
					UrlUpdate.URLS.ImageBASEURL + list.get(position));
			container.addView(iv);
			return iv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	class AutoScrollTask implements Runnable{
		public void start(){
			if(scrollCount == 0){
				UIUtils.postTaskDelay(this,1000);
				scrollCount++;
			}
			
		}
		
		public void stop(){
			UIUtils.removeTask(this);
			scrollCount=100;
		}
		
		@Override
		public void run() {
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
			scrollCount--;
			start();
		}
		
	}

}
