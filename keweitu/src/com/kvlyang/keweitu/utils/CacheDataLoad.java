package com.kvlyang.keweitu.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.kvlyang.keweitu.conf.Constants;

public class CacheDataLoad {
	
	
	public static String loadDataFromFile(String key, int index){
		return null;
		
	}
	
	public static boolean isCachaOutTime(String key, int index){
		String dir = FileUtils.getDir("json");
		String name = key+"."+index;
		File cacheFile = new File(dir,name);
		if(cacheFile.exists()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(cacheFile));
				String time = reader.readLine();
				if((System.currentTimeMillis()-Long.parseLong(time))>Constants.PROTOCOLTIMEOUT){
					return true;
				}else{
					return false;
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return true;
			}
		}else{
			return true;
		}
		
	}
}
