package com.example.sufaceopengl;

public class GLJNILib {  
    
    static {  
        System.loadLibrary("gljni");  
    }  
  
    /** 
     * @param width the current view width 
     * @param height the current view height 
     */  
    public static native void resize(int width, int height);   
      
    public static native void step();    
      
    public static native void init();    
}  
