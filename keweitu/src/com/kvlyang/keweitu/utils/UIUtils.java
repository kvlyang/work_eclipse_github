package com.kvlyang.keweitu.utils;

import android.content.Context;
import android.content.res.Resources;

import com.kvlyang.keweitu.init.StartApplication;

public class UIUtils {
	public static Context getContext(){
		return StartApplication.getContext();
	}
	
	public static Resources getResource(){
		return getContext().getResources();
	}
	
	public static String getString(int resId){
		return getResource().getString(resId);
	}
	
	public static String[] getStringArray(int resId){
		return getResource().getStringArray(resId);
	}
	
	public static int getColor(int resId){
		return getResource().getColor(resId);
	}

	public static String getPackageName() {
		return getContext().getPackageName();
	}
	
}