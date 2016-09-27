package com.kvlyang.keweitu.Activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.kvlyang.keweitu.LunBoActivity;
import com.kvlyang.keweitu.R;
import com.kvlyang.keweitu.YouKuMenuActivity;
import com.kvlyang.keweitu.R.id;
import com.kvlyang.keweitu.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

/*
 * 
 * @ kvl
 */

public class SplashActivity extends Activity {

	private RelativeLayout rl_splash;
	private Button btn_lunbo;
	private Button btn_youku;
	String version;
	String urlVersion;
	String descript;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		
		initAnimation();
		checkVersion();
	}

	private void checkVersion() {
		new Thread(){
			public void run() {
				try {
					//URL url = new URL("http://10.0.2.2:90/version.php");
					URL url = new URL("http://10.0.3.2/keweituServer/home.php");

					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					int responseCode = conn.getResponseCode();
					if(responseCode == 200){
						InputStream is = conn.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
						String line = reader.readLine();
						StringBuilder jsonString = new StringBuilder();
						while(line != null){
							jsonString.append(line);
							line = reader.readLine();
						}
						System.out.println(jsonString+" json");
						reader.close();conn.disconnect();
						
						//json
						JSONObject jobj = new JSONObject(jsonString.toString());
						version = jobj.getString("version");
						urlVersion = jobj.getString("url");
						descript = jobj.getString("des");
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
		
	}

	private void initView() {
		setContentView(R.layout.activity_splash);
		rl_splash =(RelativeLayout) findViewById(R.id.rl_splash);
		
		btn_lunbo = (Button) findViewById(R.id.button1);
		btn_lunbo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(SplashActivity.this,LunBoActivity.class));
				
			}
		});
		btn_youku = (Button) findViewById(R.id.Button02);
		btn_youku.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(SplashActivity.this, YouKuMenuActivity.class));
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
			}
		});
	}

	private void initAnimation() {
		AlphaAnimation aAni = new AlphaAnimation(0.0f, 1.0f);
		aAni.setDuration(3000);
		aAni.setFillAfter(true);
		rl_splash.startAnimation(aAni);
	}


}
