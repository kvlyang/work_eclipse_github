package com.kvlyang.keweitu.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.kvlyang.keweitu.conf.Constants;

public class CacheDataLoad {

	public static String loadDataFromFile(String key, int index) {
		String dir = FileUtils.getDir("json");
		String name = key + "." + index;
		File cacheFile = new File(dir, name);
		if (cacheFile.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(cacheFile));
				@SuppressWarnings("unused")
				String time = reader.readLine();// 记录的上次写入时间
				// if((System.currentTimeMillis()-Long.parseLong(time))>Constants.PROTOCOLTIMEOUT){
				String jsonString = reader.readLine();
				IOUtils.close(reader);
				return jsonString;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				IOUtils.close(reader);
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static boolean writeDataToFile(String key, int index,String jsonString) {
		String dir = FileUtils.getDir("json");
		String name = key + "." + index;
		File cacheFile = new File(dir, name);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(cacheFile));
			writer.write(System.currentTimeMillis()+"");
			writer.write("\r\n");
			writer.write(jsonString);
			IOUtils.close(writer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			IOUtils.close(writer);
			return false;
		}
		
		
	}

	public static boolean isCachaOutTime(String key, int index) {
		String dir = FileUtils.getDir("json");
		String name = key + "." + index;
		File cacheFile = new File(dir, name);
		BufferedReader reader = null;
		if (cacheFile.exists()) {
			try {
				reader = new BufferedReader(new FileReader(cacheFile));
				String time = reader.readLine();
				IOUtils.close(reader);
				if ((System.currentTimeMillis() - Long.parseLong(time)) > Constants.PROTOCOLTIMEOUT) {
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				IOUtils.close(reader);
				return true;
			}
		} else {
			return true;
		}

	}
}
