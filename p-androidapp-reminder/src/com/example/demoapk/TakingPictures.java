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
    public void surfaceCreated(SurfaceHolder holder) {		//������SurfaceView������ʱ���Ҫ���д�����ͷ������Ԥ��ȡ�����ڵ�SurfaceView���������յĲ���������Ԥ��ȡ���Ȳ���
      try {
        camera = Camera.open();				//������ͷ
        camera.setPreviewDisplay(picSV.getHolder());	//����picSV������Ԥ��ȡ��
        
        Parameters params = camera.getParameters();	//��ȡ������Ĳ���
        params.setPictureSize(800, 480);		//������Ƭ�Ĵ�СΪ800*480
        params.setPreviewSize(800, 480);		//����Ԥ��ȡ���Ĵ�СΪ800*480
        params.setFlashMode("on");			//���������
        params.setJpegQuality(50);			//����ͼƬ����Ϊ50
        
        camera.setParameters(params);			//�������ϲ���Ϊ������Ĳ���
        camera.startPreview();
      } catch (IOException e) {				//��ʼԤ��ȡ����Ȼ�����ǾͿ���������
        e.printStackTrace();
      }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
        int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {		//��SurfaceView����ʱ�����ǽ���ֹͣԤ�����ͷ���������������յȹ���
      camera.stopPreview();
      camera.release();
      camera = null;
    }
    
  }
  public void tackpic(View v){
    camera.autoFocus(new MyAutoFocusCallback());			//�����ǿ�ʼ����ǰ��ʵ���Զ��Խ�
  }
  
  private class MyAutoFocusCallback implements AutoFocusCallback{

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
      
       camera.takePicture(null, null, null, new MyPictureCallback());		//��ʼ����
    }
    
  }
  
  private class MyPictureCallback implements PictureCallback{

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {	//�����㵽��ͼƬ�ŵ�"/mnt/sdcard2/DCIM/Camera/"����ļ�����
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