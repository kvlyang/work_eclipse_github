package com.kvlyang.keweitu.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

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
	
	public static long getMainThreadId(){
		return StartApplication.getMainThreadId();
	}
	
	public static Handler getMainThreadHandler(){
		return StartApplication.getMainHandler();
	}
	public static void postUiTaskSafely(Runnable tast){
		if( getMainThreadId() == android.os.Process.myTid()){
			tast.run();
		}else{
			getMainThreadHandler().post(tast);
		}
	}
	
}