package com.kvlyang.keweitu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/*
 * 
 * @ kvl
 */

public class YouKuMenuActivity extends Activity {

	private ImageView btn_home;
	private ImageView btn_menu;
	private ImageView btn_search;
	private ImageView btn_myyouku;

	RelativeLayout menuRL2;
	RelativeLayout menuRL3;

	boolean menuDisplay2 = true; // 二级菜单显示
	boolean menuDisplay3 = true; // 三级菜单显示

	int animaRunCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_youku);
		menuRL2 = (RelativeLayout) findViewById(R.id.bottom_menu2);
		menuRL3 = (RelativeLayout) findViewById(R.id.bottom_menu3);

		btn_home = (ImageView) findViewById(R.id.home);
		btn_menu = (ImageView) findViewById(R.id.menu);
		btn_search = (ImageView) findViewById(R.id.search);
		btn_myyouku = (ImageView) findViewById(R.id.myyouku);

		btn_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(animaRunCount!=0){
					return;
				}
				
				if (menuDisplay2 || menuDisplay3) {
					if(menuDisplay2){
						menuDisplay2 = false;
						hideMenu2();
					}
					if(menuDisplay3){
						menuDisplay3 = false;
						hideMenu3();
					}
					
				} else if (!menuDisplay2 && !menuDisplay3) {
					menuDisplay2 = true;
					showMenu2();
				} else if (menuDisplay2 && !menuDisplay3) {
					hideMenu2();
					menuDisplay2 = false;
				}

			}
		});

		btn_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(animaRunCount!=0){
					return;
				}
				
				if (!menuDisplay3) {
					showMenu3();
					menuDisplay3 = true;
				} else {
					hideMenu3();
					menuDisplay3 = false;
				}

			}
		});

	}

	void hideMenu2() {
		RotateAnimation animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 1);
		animation.setDuration(400);
		animation.setFillAfter(true);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				animaRunCount++;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animaRunCount--;
				menuRL2.setVisibility(View.GONE);

			}
		});
		menuRL2.startAnimation(animation);
	}

	void hideMenu3() {
		RotateAnimation animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 1);
		animation.setDuration(300);
		animation.setFillAfter(true);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				animaRunCount++;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animaRunCount--;
				menuRL3.setVisibility(View.GONE);

			}
		});
		menuRL3.startAnimation(animation);
	}

	void showMenu2() {
		RotateAnimation animation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 1);
		animation.setDuration(300);
		animation.setFillAfter(true);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				animaRunCount++;
				menuRL2.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animaRunCount--;
				
			}
		});
		menuRL2.startAnimation(animation);
	}

	void showMenu3() {
		RotateAnimation animation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 1);
		animation.setDuration(400);
		animation.setFillAfter(true);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				animaRunCount++;
				menuRL3.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animaRunCount--;

			}
		});
		menuRL3.startAnimation(animation);
	}

}
