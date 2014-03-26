package com.mycopy.signaluploader;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class WatchChanges extends Service{
	 public static int TYPE_WIFI = 1;
     public static int TYPE_MOBILE = 2;
     public static int TYPE_NOT_CONNECTED = 0;
     public static int signalStrength=0;
     MyPhoneStateListener    MyListener;
     TelephonyManager        Tel;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		updateValues();
	}

	private void updateValues() {
		
		int conntype=getMyConnection();
		 if (conntype == TYPE_WIFI) {
			 /*
			  * This block is to update values for wifi
			  */
			 WifiManager wifiManager = ( WifiManager ) getSystemService(Context.WIFI_SERVICE);
		     int rssi = wifiManager.getConnectionInfo().getRssi();
		     
		     ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		     nameValuePairs.add(new BasicNameValuePair("name", "wifivalues"));
			 nameValuePairs.add(new BasicNameValuePair("value", ""+rssi));
			 post_to_internet(nameValuePairs);
			 
			 Toast.makeText(getApplicationContext(), "Executing update for wifi"+rssi, Toast.LENGTH_SHORT).show();
		 }else if (conntype == TYPE_MOBILE) {
			 MyListener=new MyPhoneStateListener();
			 Tel       = ( TelephonyManager )getSystemService(Context.TELEPHONY_SERVICE);
			 Tel.listen(MyListener ,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
			 
			 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			 nameValuePairs.add(new BasicNameValuePair("name", "mobilenetvalues"));
			 nameValuePairs.add(new BasicNameValuePair("value", ""+WatchChanges.signalStrength));
			 post_to_internet(nameValuePairs);
			 
			 Toast.makeText(getApplicationContext(), "Executing update for mobile network"+WatchChanges.signalStrength, Toast.LENGTH_SHORT).show();
		 }else if (conntype == TYPE_NOT_CONNECTED) {
			 Toast.makeText(getApplicationContext(), "Sorry Not connected to internet", Toast.LENGTH_SHORT).show();
			 MainActivity.am.cancel(MainActivity.sender);
			 stopSelf();
		 }
		
	}
	public void post_to_internet(ArrayList<NameValuePair> nameValuePairs){
		try {
			 DefaultHttpClient httpclient = new DefaultHttpClient();
			 HttpPost httppost = new HttpPost("http://192.168.37.51/insert.php");
			 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			 httpclient.execute(httppost);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}
	private class MyPhoneStateListener extends PhoneStateListener
    {
      @Override
      public void onSignalStrengthsChanged(SignalStrength signalStrength)
      {
         super.onSignalStrengthsChanged(signalStrength);
         WatchChanges.signalStrength=signalStrength.getGsmSignalStrength();
      }
    };

	private int getMyConnection() {
	    	 /*
	    	  * it returns number
	    	  */
	         ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	         NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	         if (null != activeNetwork) {
	             if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
	                 return TYPE_WIFI;
	             }
	              
	             if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
	                 return TYPE_MOBILE;
	         } 
	         return TYPE_NOT_CONNECTED;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		updateValues();
		return Service.START_NOT_STICKY;
	}
	

}
