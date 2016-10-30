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
	
	/**得到String.xml中的字符串,带占位符*/
	public static String getString(int id, Object... formatArgs) {
		return getResource().getString(id, formatArgs);
	}

	/**得到String.xml中的字符串数组*/
	public static String[] getStringArr(int resId) {
		return getResource().getStringArray(resId);
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
	
	/**安全的执行一个任务*/
	public static void postTaskSafely(Runnable task) {
		int curThreadId = android.os.Process.myTid();

		if (curThreadId == getMainThreadId()) {// 如果当前线程是主线程
			task.run();
		} else {// 如果当前线程不是主线程
			getMainThreadHandler().post(task);
		}

	}

	/**延迟执行任务*/
	public static void postTaskDelay(Runnable task, int delayMillis) {
		getMainThreadHandler().postDelayed(task, delayMillis);
	}

	/**移除任务*/
	public static void removeTask(Runnable task) {
		getMainThreadHandler().removeCallbacks(task);
	}
	
	/**
	 * dip-->px
	 */
	public static int dip2Px(int dip) {
		// px/dip = density;
		float density = getResource().getDisplayMetrics().density;
		int px = (int) (dip * density + .5f);
		return px;
	}

	/**
	 * px-->dip
	 */
	public static int px2Dip(int px) {
		// px/dip = density;
		float density = getResource().getDisplayMetrics().density;
		int dip = (int) (px / density + .5f);
		return dip;
	}
	
}