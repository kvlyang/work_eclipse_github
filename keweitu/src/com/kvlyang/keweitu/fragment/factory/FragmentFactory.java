package com.kvlyang.keweitu.fragment.factory;

import javax.security.auth.Subject;

import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;

import com.kvlyang.keweitu.fragment.AppFragment;
import com.kvlyang.keweitu.fragment.CategoryFragment;
import com.kvlyang.keweitu.fragment.GameFragment;
import com.kvlyang.keweitu.fragment.HomeFragment;
import com.kvlyang.keweitu.fragment.HotFragment;
import com.kvlyang.keweitu.fragment.RecommentFragment;
import com.kvlyang.keweitu.fragment.SubjectFragment;


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
	private static SparseArrayCompat<Fragment> cachesFragment;

	public static Fragment getFragment(int position) {
		if(cachesFragment == null){
			cachesFragment = new SparseArrayCompat<Fragment>();
		}

		Fragment fragment = cachesFragment.get(position);
		if(fragment != null){
			return fragment;
		}
		switch (position) {
		case FRAGMENT_HOME:
			fragment = new HomeFragment();
			break;
		case FRAGMENT_APP:
			fragment = new AppFragment();

			break;
		case FRAGMENT_GAME:
			fragment = new GameFragment();

			break;
		case FRAGMENT_SUBJECT:
			fragment = new SubjectFragment();

			break;
		case FRAGMENT_RECOMMENT:
			fragment = new RecommentFragment();

			break;
		case FRAGMENT_CATEGORY:
			fragment = new CategoryFragment();

			break;
		case FRAGMENT_HOT:
			fragment = new HotFragment();

			break;
		default:
			break;

		}
		
		cachesFragment.put(position, fragment);
		return fragment;
	}
}
