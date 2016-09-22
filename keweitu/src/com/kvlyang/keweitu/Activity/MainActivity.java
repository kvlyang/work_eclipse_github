package com.kvlyang.keweitu.Activity;

import com.astuetz.PagerSlidingTabStrip;
import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.utils.UIUtils;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private ViewPager viewPager;
	private PagerSlidingTabStrip tabs;
	private String[] mMainTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initActionBar();
		initData();
	}

	private void initData() {
		mMainTitles = UIUtils.getStringArray(R.array.main_titles);
		viewPager.setAdapter(new HomeAdapter());
		tabs.setViewPager(viewPager);
	}

	private void initView() {
		tabs = (PagerSlidingTabStrip) findViewById(R.id.main_ac_tabs);
		viewPager = (ViewPager) findViewById(R.id.main_ac_viewpager);
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setLogo(R.drawable.ic_launcher);
		actionBar.setTitle("GooglePlay");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class HomeAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			if( mMainTitles!=null){
				return mMainTitles.length;
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TextView tv = new TextView(UIUtils.getContext());
			tv.setText(mMainTitles[position]);
			container.addView(tv);
			return tv;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mMainTitles[position];
		}
		
	}

}
