package com.example.demoapk;





import java.io.FileOutputStream;
import java.io.IOException;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.app.Activity;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class TakingPictures  extends Activity {
  
 /* private SurfaceView picSV;
  private Camera camera;
  
  @SuppressWarnings("deprecation")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.set_outdate);
    picSV = (SurfaceView) findViewById(R.id.picSV);
    picSV.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);	
    picSV.getHolder().addCallback(new MyCallback());
  }
  
  private class MyCallback implements Callback{

    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {		//我们在SurfaceView创建的时候就要进行打开摄像头、设置预览取景所在的SurfaceView、设置拍照的参数、开启预览取景等操作
      try {
        camera = Camera.open();				//打开摄像头
        camera.setPreviewDisplay(picSV.getHolder());	//设置picSV来进行预览取景
        
        Parameters params = camera.getParameters();	//获取照相机的参数
        params.setPictureSize(800, 480);		//设置照片的大小为800*480
        params.setPreviewSize(800, 480);		//设置预览取景的大小为800*480
        params.setFlashMode("on");			//开启闪光灯
        params.setJpegQuality(50);			//设置图片质量为50
        
        camera.setParameters(params);			//设置以上参数为照相机的参数
        camera.startPreview();
      } catch (IOException e) {				//开始预览取景，然后我们就可以拍照了
        e.printStackTrace();
      }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
        int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {		//当SurfaceView销毁时，我们进行停止预览、释放摄像机、垃圾回收等工作
      camera.stopPreview();
      camera.release();
      camera = null;
    }
    
  }
  public void tackpic(View v){
    camera.autoFocus(new MyAutoFocusCallback());			//在我们开始拍照前，实现自动对焦
  }
  
  private class MyAutoFocusCallback implements AutoFocusCallback{

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
      
       camera.takePicture(null, null, null, new MyPictureCallback());		//开始拍照
    }
    
  }
  
  private class MyPictureCallback implements PictureCallback{

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {	//将拍摄到的图片放到"/mnt/sdcard2/DCIM/Camera/"这个文件夹中
      try {
        FileOutputStream fos = new FileOutputStream("/mnt/sdcard2/DCIM/Camera/"+System.currentTimeMillis()+".jpg");
        fos.write(data);
        fos.close();
        camera.startPreview();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
  }*/
}