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
	
	 
	 private static final int SERVER_PORT = 7070;//�˿ں�   
     private static final String SERVER_IP = "192.168.1.6";//����IP   
     private static final String CLIENT_KET_PASSWORD = "000000";//˽Կ����   
     private static final String CLIENT_TRUST_PASSWORD = "000000";//����֤������   
     private static final String CLIENT_AGREEMENT = "TLS";//ʹ��Э��   
     private static final String CLIENT_KEY_MANAGER = "X509";//��Կ������   
     private static final String CLIENT_TRUST_MANAGER = "X509";//   
     private static final String CLIENT_KEY_KEYSTORE = "BKS";//�ܿ⣬�����õ���BouncyCastle�ܿ�   
     private static final String CLIENT_TRUST_KEYSTORE = "BKS";//   
     private static final String ENCONDING = "utf-8";//�ַ���   
     private SSLSocket Client_sslSocket;   
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads()
		.detectDiskWrites()
		.detectNetwork() // ��������滻ΪdetectAll() �Ͱ����˴��̶�д������I/O
		.penaltyLog() //��ӡlogcat����ȻҲ���Զ�λ��dropbox��ͨ���ļ�������Ӧ��log
		.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		.detectLeakedSqlLiteObjects() //̽��SQLite���ݿ����
		.penaltyLog() //��ӡlogcat
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
	            //ȡ��SSL��SSLContextʵ��   
	            SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);   
	             //ȡ��KeyManagerFactory��TrustManagerFactory��X509��Կ������ʵ��   
	            KeyManagerFactory keyManager = KeyManagerFactory.getInstance(CLIENT_KEY_MANAGER);   
	             TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);   
	            //ȡ��BKS�ܿ�ʵ��   
	          KeyStore kks= KeyStore.getInstance(CLIENT_KEY_KEYSTORE);   
	             KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);   
	             //�ӿͻ�����֤���˽Կ,ͨ����ȡ��Դ�ļ��ķ�ʽ��ȡ��Կ������֤��   
	             kks.load(getBaseContext().getAssets().open("bksclientkeys"),CLIENT_KET_PASSWORD.toCharArray());  
	         //            .getResources()   
	          //           .openRawResource(R.drawable.bksclientkeys),CLIENT_KET_PASSWORD.toCharArray());   
	             tks.load(getBaseContext().getAssets().open("bksclienttrust"),CLIENT_TRUST_PASSWORD.toCharArray());     
	          //           .getResources()   
	          //           .openRawResource(R.drawable.lt_client),CLIENT_TRUST_PASSWORD.toCharArray());   
	             //��ʼ����Կ������   
	             keyManager.init(kks,CLIENT_KET_PASSWORD.toCharArray());   
	             trustManager.init(tks);   
	             //��ʼ��SSLContext   
	            sslContext.init(keyManager.getKeyManagers(),trustManager.getTrustManagers(),null);   
	             //����SSLSocket   
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
		 // ��ȡ�ͻ����׽��������
	    	  OutputStream  ss = income.getOutputStream();
	    	  OutputStreamWriter osw = new OutputStreamWriter(ss);
	    	  PrintWriter output = new PrintWriter(osw);
//		 PrintWriter output = new PrintWriter( 
//		       new OutputStreamWriter(income.getOutputStream())); 
	 // ���û���������ͨ����������͵���������
		/* String userName = "principal"; 
		 output.println(userName); 
		 String password = "credential"; 
		 output.println(password); 
		 output.flush(); 
		Log.i("DemoApk","send pwd and accounts over!"); */
		 // ��ȡ�ͻ����׽���������
		/* BufferedReader input = new BufferedReader( 
		        new InputStreamReader(income.getInputStream())); 
	 // ���������ж�ȡ�������˴��͵��������ݣ�����ӡ����
		 String response = input.readLine(); 
		 response += "\n " + input.readLine(); 
		// System.out.println(response); 
			Log.i("DemoApk",response);  */
	 // �ر�����Դ���׽�����Դ
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

