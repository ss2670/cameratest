package com.example.demoapk;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
/* //打开或创建test.db数据库  
        SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);  
        db.execSQL("DROP TABLE IF EXISTS person");  
        //创建person表  
        db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");  
        Person person = new Person();  
        person.name = "john";  
        person.age = 30;  
        //插入数据  
        db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{person.name, person.age});  
          
        person.name = "david";  
        person.age = 33;  
        //ContentValues以键值对的形式存放数据  
        ContentValues cv = new ContentValues();  
        cv.put("name", person.name);  
        cv.put("age", person.age);  
        //插入ContentValues中的数据  
        db.insert("person", null, cv);  
          
        cv = new ContentValues();  
        cv.put("age", 35);  
        //更新数据  
        db.update("person", cv, "name = ?", new String[]{"john"});  
          
        Cursor c = db.rawQuery("SELECT * FROM person WHERE age >= ?", new String[]{"33"});  
        while (c.moveToNext()) {  
            int _id = c.getInt(c.getColumnIndex("_id"));  
            String name = c.getString(c.getColumnIndex("name"));  
            int age = c.getInt(c.getColumnIndex("age"));  
            Log.i("db", "_id=>" + _id + ", name=>" + name + ", age=>" + age);  
        }  
        c.close();  
          
        //删除数据  
        db.delete("person", "age < ?", new String[]{"35"});  
          
        //关闭当前数据库  
        db.close();  
          
        //删除test.db数据库  
//      deleteDatabase("test.db"); 
 * */
import android.widget.Toast;
 
public class Setoutdate  extends Activity{
	  private final static String TAG = "CameraActivity";  
	
	    private ImageView imageView;
	    private CalendarView cv;
	    private DatePicker datepicker;
	    private Calendar c;
	    private File picture;  
	    private Button btnSave;  
	    private String dateEx;
	    private String paddrEx;
	    private int year;
	    private int monthOfYear;
	    private int dayOfMonth;
	      @Override
	    public void onCreate(Bundle savedInstanceState) {  
	    	 super.onCreate(savedInstanceState); 
	         //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
	    	 //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
	    	 setContentView(R.layout.set_outdate); 
	    	 Log.v(TAG,"Turned page!");
	    	 Bundle bundle = this.getIntent().getExtras();
	    	 paddrEx = bundle.getString("pictureaddr");
	    	 if(paddrEx!=null)
	    	 {
	    		 Log.v(TAG,"pictureaddr is not null:"+paddrEx);
	    		 
	    	 }
	    	 else Log.v(TAG,"pictureaddr is null!");
	    	 //Uri uri = Uri.parse(paddrEx);
	    	 //String fileName = "/data/data/com.test/aa.png;
	    	// cv=(CalendarView) this.findViewById(R.id.calendarView1);
	    	 datepicker=(DatePicker) this.findViewById(R.id.datePicker1);
	    	 if(paddrEx !="")
	    	 {
	    	 
	    	 Bitmap bm = BitmapFactory.decodeFile(paddrEx); 	    	  
	         if(bm!=null)
	         { imageView = (ImageView) this.findViewById(R.id.imageView1);    	
		     imageView.setBackgroundColor(0xFF000000);
		     imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		     imageView.setImageBitmap(bm);
		     Log.v(TAG,"LOADED IMAGE!");
	    	 }
             else  Log.v(TAG,"Can't find the image!");
	    	 }
		     
		     
		     /*  cv.setOnClickListener(new OnClickListener(){
		       //.setOnDateChangeListener(new OnDateChangeListener() {
		            public void onSelectedDayChange(CalendarView view, int year, int month,
		                    int dayOfMonth) {
		                // TODO Auto-generated method stub
		                String date = year + "年" + month + "月" + dayOfMonth + "日";
		                Toast.makeText(getApplicationContext(), date, 0).show();
		            }
		        });
		    }    */
	         setupViews();  
	         Log.v(TAG,"Setup views over!");
	    }  
	      
	    @SuppressWarnings("deprecation")
		private void setupViews(){  
	        //surfaceView = (SurfaceView) findViewById(R.id.camera_preview); // Camera interface to instantiate components  
	       // surfaceHolder = surfaceView.getHolder(); // Camera interface to instantiate components  
	        //surfaceHolder.addCallback(surfaceCallback); // Add a callback for the SurfaceHolder  
	//照片御览
	      /*  String viewAction = "android.intent.action.VIEW"; 
	        Uri picUri = Uri.parse("file://" + path); 
	        Intent lookPic = new Intent(); 
	        lookPic.setAction(viewAction);
	        lookPic.setDataAndType(picUri, "image/*"); 
	        lookPic.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK ); 
	        mContext.startActivity(lookPic);*/
	        //new ImageView(this);
	   
	        //imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
	        //LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	       // return imageView;
	//日期选择       
	    	
	        c=Calendar.getInstance();
	        year=c.get(Calendar.YEAR);
	        monthOfYear=c.get(Calendar.MONTH);
	        dayOfMonth=c.get(Calendar.DAY_OF_MONTH);
	        Log.v(TAG,"date is "+c.getTime().toString());
	        datepicker.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener(){
                
	            public void onDateChanged(DatePicker view, int year,
	                    int monthOfYear, int dayOfMonth) {
	                dateEx=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
	                Log.v(TAG,"date is changed to "+dateEx);
	               //'2004-01-01 
	               // 02:34:56
	            }
	            
	        });
	        
	        btnSave = (Button) findViewById(R.id.setoutdate_button);
	        Log.v(TAG,"set save_outdate button");
	        btnSave.setOnClickListener(new OnClickListener() {  
	              
	            @Override  
	           public void onClick(View v) {  
	            	 insertRecord(paddrEx,dateEx,"9:00:00");
		              Date date=new Date(0L);
		              date.setYear(year);
		              date.setMonth(monthOfYear);
		              date.setDate(dayOfMonth);
		              date.setHours(9);
		              date.setMinutes(0);
		              date.setSeconds(0);
		              // Time t = new Time();
		              Log.v(TAG,"set reminder date "+date.toString());
	            }  
	        });  
	    }  
	    public void onClick(View v) {
	        // TODO Auto-generated method stub
	        switch(v.getId()){
	            case R.id.setoutdate_button:{
	            	 insertRecord(paddrEx,dateEx,"9:00:00");
		              Date date=new Date(0L);
		              date.setYear(year);
		              date.setMonth(monthOfYear);
		              date.setDate(dayOfMonth);
		              date.setHours(9);
		              date.setMinutes(0);
		              date.setSeconds(0);
		              // Time t = new Time();
		              Log.v(TAG,"set reminder date "+date.toString());
		               setReminder(date);
	            }
	            
	        }
	    }
	    /**
	   　 * 文件选取完成回调函数
	   　 */
	   private boolean tableExistQuery( SQLiteDatabase db)
	  {
		 String sql="SELECT count(*) FROM sqlite_master WHERE type='table' AND name='reminderdate';";
		  Cursor cursor = db.rawQuery(sql, null);
          if(cursor.moveToNext()){
                  int count = cursor.getInt(0);
                  if(count>0){
                          return true;
                  }
          }
       
		return false;
          
	  }
	    private void insertRecord(String pictureaddr, String date,String time )
	    {
	    	  SQLiteDatabase db = openOrCreateDatabase("reminder.db", Context.MODE_PRIVATE, null);  
	         // db.execSQL("DROP TABLE IF EXISTS person");  
	          //创建reminderdate表  
	         if( tableExistQuery(db)==false)	          
	          {
	        	 Log.v(TAG,"table is not existing, create it now!");
	        	 db.execSQL("CREATE TABLE reminderdate (_id INTEGER PRIMARY KEY AUTOINCREMENT, pictureaddr VARCHAR, day DATE, ti TIME)");  
	          }
	        
	          //插入数据  
	          db.execSQL("INSERT INTO reminderdate" +
	          		" VALUES (NULL, ?, ?, ?)", new Object[]{pictureaddr, date, time});  
	            
	        
	    }
 public void setReminder(Date date) {
	        
	        // get the AlarmManager instance 
	        AlarmManager am= (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	        // create a PendingIntent that will perform a broadcast
	        PendingIntent pi= PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 0);
	        		//getBroadcast(SetReminder.this, 0, new Intent(this,MyReceiver.class), 0);
	        
	        //if(b){
	            // just use current time as the Alarm time. 
	            Calendar c=Calendar.getInstance();
	            c.setTime(date);
	            // schedule an alarm
	            /*c.set(Calendar.YEAR, date.getYear(),date.);  
	            c.set(Calendar.MONTH, month-1);  
	            c.set(Calendar.DAY_OF_MONTH, day);  
	            c.set(Calendar.HOUR_OF_DAY, hour);  
	            c.set(Calendar.MINUTE, minute);  */
	            am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
	        //}
	       // else{
	            // cancel current alarm
	        //    am.cancel(pi);
	        //}
	        
	    }
	  
}
