package com.example.sufaceopengl;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

	 GLJNIView mView; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        mView = new GLJNIView(getApplication());  
        setContentView(mView);

    }
        
        @Override
        protected void onPause() {
            super.onPause();

        }

        @Override
        protected void onResume() {
            super.onResume();
   
        }
    }
    
 
  //======================================================================
   
   
 //=================================================
    
   
    
    
