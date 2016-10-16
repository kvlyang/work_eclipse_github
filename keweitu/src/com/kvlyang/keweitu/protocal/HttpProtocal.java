package com.kvlyang.keweitu.protocal;

import android.util.Log;

import com.google.gson.Gson;
import com.kvlyang.keweitu.bean.HomeBean;
import com.kvlyang.keweitu.conf.UrlUpdate;
import com.kvlyang.keweitu.fragment.HomeFragment.HomeData;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HttpProtocal {
	static public HomeBean loadHomeData(int index) throws Exception {
	
		String result;
		HttpUtils httpUtils = new HttpUtils();
		// String url = "http://10.0.3.2/keweituServer/home.php";
		
		String url = UrlUpdate.URLS.BASEURL+"/home";
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("index", ""+index);
		ResponseStream stream = httpUtils.sendSync(HttpMethod.GET, url,
				params);
		result = stream.readString();

		// 解析json网络数据

		Gson gson = new Gson();
		return gson.fromJson(result, HomeBean.class);
	}
}
