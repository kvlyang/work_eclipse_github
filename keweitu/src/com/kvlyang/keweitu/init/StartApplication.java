package com.kvlyang.keweitu.init;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class StartApplication extends Application {
	
	private static long mainThreadId;
	private static Thread mainThread;
	private static Context context;
	private static Looper mainLooper;
	private static Handler mainHandler;

	@Override
	public void onCreate() { // main() 程序入口
		context = getApplicationContext();
		
		mainThread = Thread.currentThread();
		mainThreadId = android.os.Process.myTid();
		mainLooper = getMainLooper();
		mainHandler = new Handler(mainLooper);
		super.onCreate();
	}
	
	public static long getMainThreadId() {
		return mainThreadId;
	}

	public static Thread getMainThread() {
		return mainThread;
	}

	public static Context getContext() {
		return context;
	}

	public static Looper getMainThreadLooper() {
		return mainLooper;
	}

	public static Handler getMainHandler() {
		return mainHandler;
	}

	
}
