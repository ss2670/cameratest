package com.example.demoapk;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements Button.OnClickListener {

	 Button plainSent,cipherSent,takepictures;
	 EditText address,content;
	 TextView tv;
	 private static final String HOST = "192.168.0.132";//"192.168.110.63";  
	 private static final int PORT = 4700;  
	
	 
	 private static final int SERVER_PORT = 7070;//端口号   
     private static final String SERVER_IP = "192.168.1.6";//连接IP   
     private static final String CLIENT_KET_PASSWORD = "000000";//私钥密码   
     private static final String CLIENT_TRUST_PASSWORD = "000000";//信任证书密码   
     private static final String CLIENT_AGREEMENT = "TLS";//使用协议   
     private static final String CLIENT_KEY_MANAGER = "X509";//密钥管理器   
     private static final String CLIENT_TRUST_MANAGER = "X509";//   
     private static final String CLIENT_KEY_KEYSTORE = "BKS";//密库，这里用的是BouncyCastle密库   
     private static final String CLIENT_TRUST_KEYSTORE = "BKS";//   
     private static final String ENCONDING = "utf-8";//字符集   
     private SSLSocket Client_sslSocket;   
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads()
		.detectDiskWrites()
		.detectNetwork() // 这里可以替换为detectAll() 就包括了磁盘读写和网络I/O
		.penaltyLog() //打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
		.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		.detectLeakedSqlLiteObjects() //探测SQLite数据库操作
		.penaltyLog() //打印logcat
		.penaltyDeath()
		.build()); 

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		plainSent=(Button)findViewById(R.id.plainButton);
		cipherSent=(Button)findViewById(R.id.cipherButton);
		takepictures=(Button)findViewById(R.id.takepictures);
		address=(EditText)findViewById(R.id.address);
		content=(EditText)findViewById(R.id.content);
		tv=(TextView)findViewById(R.id.textView2);
		plainSent.setOnClickListener(this);
	    cipherSent.setOnClickListener(this);
	   takepictures.setOnClickListener(this);
	}

	




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	  @Override
	    public void onClick(View v) {
	        // TODO Auto-generated method stub
	        switch(v.getId()){
	            case R.id.plainButton:{
	            	try {  
	            		String currenthost=address.getText().toString();
	                    Socket socket = new Socket(currenthost, PORT);
	                    String text=content.getText().toString();
	                   Log.i("DemoAPK",text);
	                   new Thread(new plainSocket(socket,text)).run();  
	                   Log.i("DemoAPK","sendover!");
	                 /*  text=content.getText().toString();
	                   Log.i("DemoAPK",text);
	                   new Thread(new plainSocket(socket,text)).run();  
	                   Log.i("DemoAPK","sendover!");*/
	                    
	                } catch (IOException ex) {  
	                    ex.printStackTrace();  
	                    
	                }  

	            }
	            break;
	            case R.id.cipherButton:{

	            		init();
	            	    String text=content.getText().toString();
		                   Log.i("DemoAPK","SSL sending:"+text);
	            		 new Thread(new sslSocket(Client_sslSocket,text)).run();  
		                   Log.i("DemoAPK","SSL sendover!");
		               /* text=content.getText().toString();
		                   Log.i("DemoAPK","SSL sending:"+text);
	            		 new Thread(new sslSocket(Client_sslSocket,text)).run();  
		                   Log.i("DemoAPK","SSL sendover!");*/
	            }
	            break;
	            case R.id.takepictures:{
	            	 Intent intent = new Intent(); 
	                 intent.setClass(this, TakePictures.class);
	                 this.startActivity(intent);
	            }
	       break;
	        }
	    }
	  public void init() {   
	         try {   
	            //取得SSL的SSLContext实例   
	            SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);   
	             //取得KeyManagerFactory和TrustManagerFactory的X509密钥管理器实例   
	            KeyManagerFactory keyManager = KeyManagerFactory.getInstance(CLIENT_KEY_MANAGER);   
	             TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);   
	            //取得BKS密库实例   
	          KeyStore kks= KeyStore.getInstance(CLIENT_KEY_KEYSTORE);   
	             KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);   
	             //加客户端载证书和私钥,通过读取资源文件的方式读取密钥和信任证书   
	             kks.load(getBaseContext().getAssets().open("bksclientkeys"),CLIENT_KET_PASSWORD.toCharArray());  
	         //            .getResources()   
	          //           .openRawResource(R.drawable.bksclientkeys),CLIENT_KET_PASSWORD.toCharArray());   
	             tks.load(getBaseContext().getAssets().open("bksclienttrust"),CLIENT_TRUST_PASSWORD.toCharArray());     
	          //           .getResources()   
	          //           .openRawResource(R.drawable.lt_client),CLIENT_TRUST_PASSWORD.toCharArray());   
	             //初始化密钥管理器   
	             keyManager.init(kks,CLIENT_KET_PASSWORD.toCharArray());   
	             trustManager.init(tks);   
	             //初始化SSLContext   
	            sslContext.init(keyManager.getKeyManagers(),trustManager.getTrustManagers(),null);   
	             //生成SSLSocket   
	            String currenthost=address.getText().toString();
	            Log.i("DemoAPK","Connecting address "+currenthost);
	            Client_sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(currenthost,SERVER_PORT);   
	        } catch (Exception e) {   
	             Log.e("MySSLSocket",e.getMessage());   
	         }   
	     }   
}
class plainSocket implements Runnable {
	private Socket income=null;
	private String text=null;
	public plainSocket(Socket i,String t){
		income=i;
		text=t;
	}
	public void run() {
		try{
		OutputStream outStream = income.getOutputStream();
        PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);
	
        if (income.isConnected()) {  
            if (!income.isOutputShutdown()) {  
                out.println(text);
            }  
        }
        out.flush();
        out.close();
        outStream.close();
		}
		catch (IOException e)
	      {  
	         e.printStackTrace();
	      }
		
	
}
}

	class sslSocket implements Runnable {
		 private SSLSocket income=null;
		 private String text=null;
		    private Log tag;   

		public sslSocket(SSLSocket i,String t){
			income=i;
			text=t;
		}
		

         public void connect() { 
	      try { 
		 // 获取客户端套接字输出流
	    	  OutputStream  ss = income.getOutputStream();
	    	  OutputStreamWriter osw = new OutputStreamWriter(ss);
	    	  PrintWriter output = new PrintWriter(osw);
//		 PrintWriter output = new PrintWriter( 
//		       new OutputStreamWriter(income.getOutputStream())); 
	 // 将用户名和密码通过输出流发送到服务器端
		/* String userName = "principal"; 
		 output.println(userName); 
		 String password = "credential"; 
		 output.println(password); 
		 output.flush(); 
		Log.i("DemoApk","send pwd and accounts over!"); */
		 // 获取客户端套接字输入流
		/* BufferedReader input = new BufferedReader( 
		        new InputStreamReader(income.getInputStream())); 
	 // 从输入流中读取服务器端传送的数据内容，并打印出来
		 String response = input.readLine(); 
		 response += "\n " + input.readLine(); 
		// System.out.println(response); 
			Log.i("DemoApk",response);  */
	 // 关闭流资源和套接字资源
		// input.close(); 
		/* Scanner scanner = new Scanner(System.in);
		 boolean done = false;
         while ((!done)&&scanner.hasNextLine())
         {  
            String line = scanner.nextLine();
            System.out.println(line);
            output.println(line);
            output.flush();
            if (line.trim().equals("BYE"))
                done = true;
         }*/
		 output.println(text); 
		 output.flush(); 
		 
         //input.close();
         output.close();

	 } catch (IOException ioException) { 
		 ioException.printStackTrace(); 
	 } 
} 
         public void run() {
		
    		connect();  
			
		
	     }
	}

