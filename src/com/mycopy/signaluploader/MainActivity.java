package com.mycopy.signaluploader;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	public static AlarmManager am;
	public static PendingIntent sender;
	public static Intent intent_alarm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void tensec(View view){
		 intent_alarm = new Intent(this, WatchChanges.class);
    	 sender = PendingIntent.getService(this, 0, intent_alarm, 0);
    	 am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
    	 long nextTriggeringTime = System.currentTimeMillis();
    	 nextTriggeringTime += 10000L;  //10 sec * 1000= 10000 milliseconds
    	 am.setRepeating(AlarmManager.RTC_WAKEUP,nextTriggeringTime, 10000L, sender);
	}
	
	public void onemin(View view){
		 intent_alarm = new Intent(this, WatchChanges.class);
   	 sender = PendingIntent.getService(this, 0, intent_alarm, 0);
   	 am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
   	 long nextTriggeringTime = System.currentTimeMillis();
   	 nextTriggeringTime += 60000L;  //10 sec * 1000= 10000 milliseconds
   	 am.setRepeating(AlarmManager.RTC_WAKEUP,nextTriggeringTime, 60000L, sender);
	}
	
	public void twomin(View view){
		 intent_alarm = new Intent(this, WatchChanges.class);
  	 sender = PendingIntent.getService(this, 0, intent_alarm, 0);
  	 am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
  	 long nextTriggeringTime = System.currentTimeMillis();
  	 nextTriggeringTime += 120000L;  //10 sec * 1000= 10000 milliseconds
  	 am.setRepeating(AlarmManager.RTC_WAKEUP,nextTriggeringTime, 120000L, sender);
	}
	
	public void threemin(View view){
		 intent_alarm = new Intent(this, WatchChanges.class);
  	 sender = PendingIntent.getService(this, 0, intent_alarm, 0);
  	 am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
  	 long nextTriggeringTime = System.currentTimeMillis();
  	 nextTriggeringTime += 180000L;  //10 sec * 1000= 10000 milliseconds
  	 am.setRepeating(AlarmManager.RTC_WAKEUP,nextTriggeringTime, 180000L, sender);
	}
	
	public void stop_service(View view){
		am.cancel(sender);
		Intent intent=new Intent(this, WatchChanges.class);
		stopService(intent);
	}

}
