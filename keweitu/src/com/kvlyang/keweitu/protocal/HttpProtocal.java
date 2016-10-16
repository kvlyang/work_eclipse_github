package com.kvlyang.keweitu.protocal;

import com.kvlyang.keweitu.conf.UrlUpdate;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HttpProtocal {
	static public String loadHttpData(String key,int index) throws Exception {
	
		String result;
		HttpUtils httpUtils = new HttpUtils();
		// String url = "http://10.0.3.2/keweituServer/home.php";
		
		String url = UrlUpdate.URLS.BASEURL+"/"+key;
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("index", ""+index);
		ResponseStream stream = httpUtils.sendSync(HttpMethod.GET, url,
				params);
		result = stream.readString();
		return result;
	
	}
}
