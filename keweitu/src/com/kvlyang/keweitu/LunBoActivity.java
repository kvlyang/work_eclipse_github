package com.kvlyang.keweitu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/*
 * 
 * @ kvl
 */

public class LunBoActivity extends Activity {


	ViewPager vPager;
	private LinearLayout pointLL;
	private List<ImageView> listDatas ;
	private TextView titleIv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lunbo);
		
		vPager = (ViewPager) findViewById(R.id.vp_lunbo);
		pointLL = (LinearLayout)findViewById(R.id.point_container_lunbo);
		titleIv = (TextView) findViewById(R.id.image_title_lunbo);
		listDatas = new ArrayList<ImageView>();
		int[] imgs = {R.drawable.icon_1,R.drawable.icon_2,R.drawable.icon_3,
				R.drawable.icon_4,R.drawable.icon_5
		};
		titleIv.setText("test");
		for(int i = 0; i< imgs.length; i++){
			ImageView iv = new ImageView(this);
			iv.setImageResource(imgs[i]);
			iv.setScaleType(ScaleType.FIT_XY);
			listDatas.add(iv);
			
			View point = new View(this);
			
			LayoutParams params = new LayoutParams(10, 10);
			if(i != 0){
				params.leftMargin = 10;
				point.setBackgroundResource(R.drawable.point_unselected);
			}else{
				point.setBackgroundResource(R.drawable.point_selected);
			}
			pointLL.addView(point,params);
		}
		
		vPager.setAdapter(new MyAdapter());
		vPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				int count = pointLL.getChildCount();
				position = position % listDatas.size();
				titleIv.setText("lunbo"+position);
				for(int i = 0; i<count; i++){
					
					View view = pointLL.getChildAt(i);
					if(position == i){
						view.setBackgroundResource(R.drawable.point_selected);
					}else{
						view.setBackgroundResource(R.drawable.point_unselected);
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
		
		int  n = Integer.MAX_VALUE/2;
		
		vPager.setCurrentItem(n - n%listDatas.size());
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if(listDatas!=null){
				//return listDatas.size();
				return Integer.MAX_VALUE;
			}	
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(View container, int position) {
			position = position % listDatas.size();
			ImageView iv = listDatas.get(position);
			vPager.addView(iv);
			//return super.instantiateItem(container, position);
			return iv;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			position = position % listDatas.size();
			ImageView iv = listDatas.get(position);
			vPager.removeView(iv);
		}
	}
	
	class MyListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}



}
