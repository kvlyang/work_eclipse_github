package com.kvlyang.keweitu.factory;

import android.support.v4.app.Fragment;

import com.kvlyang.keweitu.fragment.HomeFragment;


public class FragmentFactory {
	/*
	 * <string-array name="main_titles"> <item>首页</item> <item>应用</item>
	 * <item>游戏</item> <item>专题</item> <item>推荐</item> <item>分类</item>
	 * <item>排行</item> </string-array>
	 */

	public static final int FRAGMENT_HOME = 0;
	public static final int FRAGMENT_APP = 1;
	public static final int FRAGMENT_GAME = 2;
	public static final int FRAGMENT_SUBJECT = 3;
	public static final int FRAGMENT_RECOMMENT = 4;
	public static final int FRAGMENT_CATEGORY = 5;
	public static final int FRAGMENT_HOT = 6;

	public static Fragment getFragment(int position) {
		Fragment fragment = null;
		switch (position) {
		case FRAGMENT_HOME:
			fragment = new HomeFragment();
			break;
		case FRAGMENT_APP:
			fragment = new HomeFragment();

			break;
		case FRAGMENT_GAME:
			fragment = new HomeFragment();

			break;
		case FRAGMENT_SUBJECT:
			fragment = new HomeFragment();

			break;
		case FRAGMENT_RECOMMENT:
			fragment = new HomeFragment();

			break;
		case FRAGMENT_CATEGORY:
			fragment = new HomeFragment();

			break;
		case FRAGMENT_HOT:
			fragment = new HomeFragment();

			break;
		default:
			break;

		}
		return fragment;
	}
}
