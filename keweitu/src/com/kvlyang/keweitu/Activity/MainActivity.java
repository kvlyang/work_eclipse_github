package com.kvlyang.keweitu.Activity;

import com.astuetz.PagerSlidingTabStripExtends;
import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.factory.FragmentFactory;
import com.kvlyang.keweitu.fragment.base.BaseFragment;
import com.kvlyang.keweitu.utils.UIUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private ViewPager viewPager;
	private int cachePagers = 3;
	private PagerSlidingTabStripExtends tabs;
	private String[] mMainTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initActionBar();
		initData();
		initListener();
	}

	private void initData() {
		mMainTitles = UIUtils.getStringArray(R.array.main_titles);
		//MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
		MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(cachePagers);
		tabs.setViewPager(viewPager);
	}

	private void initView() {
		tabs = (PagerSlidingTabStripExtends) findViewById(R.id.main_ac_tabs);
		viewPager = (ViewPager) findViewById(R.id.main_ac_viewpager);
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setLogo(R.drawable.ic_launcher);
		actionBar.setTitle("GooglePlay");
	}
	
	private void initListener(){
		tabs.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				BaseFragment fragment = (BaseFragment) FragmentFactory.getFragment(position);
				if(fragment != null){
					
				
						/*
						 * 从网络更新数据，
						 * 异常处理：
						 * 如果缓存和网络都未获取到数据：
						 * initSuccessView返回null，依据返回的state加载提示页
						 */					
						fragment.getLoadingPager().loadDataHttp();
						//在initDataFromHttp()中判断， 如果缓存文件未过期，不会再拉数据（节省流量）
					
					
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
	
	class MainFragmentPagerAdapter extends FragmentPagerAdapter{

		public MainFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = FragmentFactory.getFragment(position);
			return fragment;
		}

		@Override
		public int getCount() {
			if(mMainTitles!=null){
				return mMainTitles.length;
			}
			return 0;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mMainTitles[position];
		}
		
		@Override
		public int getItemPosition(Object object) {
		return super.getItemPosition(object);
		}
		
	}
	
	class MainFragmentStatePagerAdapter extends FragmentStatePagerAdapter{

		public MainFragmentStatePagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = FragmentFactory.getFragment(position);
			return fragment;
		}

		@Override
		public int getCount() {
			if(mMainTitles!=null){
				return mMainTitles.length;
			}
			return 0;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mMainTitles[position];
		}
		
	}

}
