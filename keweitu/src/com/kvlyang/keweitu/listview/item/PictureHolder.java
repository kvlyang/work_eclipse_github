package com.kvlyang.keweitu.listview.item;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
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
	int selectNum = 0;

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
		for (int i = 0; i < list.size(); i++) {
			View indicatorView = new View(UIUtils.getContext());
			if(selectNum == i){
				indicatorView.setBackgroundResource(R.drawable.indicator_selected);
			}else{
				indicatorView.setBackgroundResource(R.drawable.indicator_normal);
			}
			

			containerIndicator.addView(indicatorView, params);

		}

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				selectNum = arg0;
				for (int i = 0; i < list.size(); i++) {
					View indicatorView = containerIndicator.getChildAt(i);
					
					if(i == arg0){
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
				return list.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
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

}
