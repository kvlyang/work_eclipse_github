package com.example.sufaceopengl;

import android.content.Context;  
import android.opengl.GLSurfaceView; 
import javax.microedition.khronos.egl.EGLConfig;  
import javax.microedition.khronos.opengles.GL10;

public class GLJNIView extends GLSurfaceView {  
	  
    private static final String LOG_TAG = GLJNIView.class.getSimpleName();  
  
    private Renderer renderer;  
  
    public GLJNIView(Context context) {  
        super(context);  
  
        // setEGLConfigChooser会对fps产生影响  
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);  
  
        renderer = new Renderer(context);  
        setRenderer(renderer);  
    }  
  
    private static class Renderer implements GLSurfaceView.Renderer {  
  
        public Renderer(Context ctx) {  
  
        }  
  
        public void onDrawFrame(GL10 gl) {  
            GLJNILib.step();  
        }  
  
        public void onSurfaceChanged(GL10 gl, int width, int height) {  
            GLJNILib.resize(width, height);  
        }  
  
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {  
            GLJNILib.init();  
        }  
    }  
  
} 
