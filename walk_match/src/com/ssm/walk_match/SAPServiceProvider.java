package com.ssm.walk_match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.accessory.SA;
import com.samsung.android.sdk.accessory.SAAgent;
import com.samsung.android.sdk.accessory.SAPeerAgent;
import com.samsung.android.sdk.accessory.SASocket;
import com.ssm.walk_match.main.FirstActivity;
import com.ssm.walk_match.main.MainActivity;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.service.ServiceType;
import com.ssm.walk_match.util.AppManager;

public class SAPServiceProvider extends SAAgent implements  HttpClientNet.OnResponseListener {
	public static final String TAG = "walk";
	
	public Boolean isAuthentication = false;
	public Context mContext = null;

	public static final int SERVICE_CONNECTION_RESULT_OK = 0;

	public static final int WALK_CHANNEL_ID = 211;

	HashMap<Integer, WalkProviderConnection> mConnectionsMap = null;
	
	private final IBinder mBinder = new LocalBinder();
	private int id;
	public class LocalBinder extends Binder {
		public SAPServiceProvider getService() {
			return SAPServiceProvider.this;
		}
	}

	public SAPServiceProvider() {
		super(TAG, WalkProviderConnection.class);
	}

	public class WalkProviderConnection extends SASocket {
		private int mConnectionId;

		public WalkProviderConnection() {
			super(WalkProviderConnection.class.getName());
		}

		@Override
		public void onError(int channelId, String errorString, int error) {
			Log.e(TAG, "Connection is not alive ERROR: " + errorString + "  "
					+ error);
			
		}

		@Override
		public void onReceive(int channelId, byte[] data)
		{
			final WalkProviderConnection uHandler = mConnectionsMap.get(Integer
   					.parseInt(String.valueOf(mConnectionId)));
			id = mConnectionId;
   			if(uHandler == null){
   				Log.e(TAG,"Error, can not get HelloAccessoryProviderConnection handler");
   				return;
   			}
			Log.d(TAG, "onReceive");
			String strToUpdateUI = new String(data);
            if(strToUpdateUI.equals("call"))
            {
            	 try {
            		 //서버통신 
            		 //매치중 아니면 0 을 넣으면됨
            		 SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
            		 int mewalk = sp2.getInt("mewalk", 0);
            		 Log.d("미워크크크크크",mewalk+"");
            		 int youwalk = sp2.getInt("youwalk", 0);
            		 String mename = sp2.getString("mename", "");
            		 String youname = sp2.getString("youname", "");
            		 String youemail = sp2.getString("youemail", "");
            		 
            		 HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_MATCH_STEP);
        	  		 ArrayList<Params> loginParams = new ArrayList<Params>();
        	  		 loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
        	  		 loginParams.add(new Params("fri_email", youemail));
        	  		
        	  		 loginService.setParam(loginParams);
        	  		 loginService.doAsyncExecute(SAPServiceProvider.this);
            	 }
                 catch (Exception e1) {
     			// TODO Auto-generated catch block
     				e1.printStackTrace();
     			}
            }
           else if(strToUpdateUI.equals("walk"))
	       {
        	   //나한테 오는 정보 
	    	   //내 프리퍼런스 걸음수 +1;
        	   Log.i("zz", "워크하나증가");
        	   SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
        	   int mewalk = sp2.getInt("mewalk", 0);
      		   
      		   SharedPreferences.Editor editer2 = sp2.edit();
      		   Log.i("mewalk",mewalk+"");
      		   editer2.putInt("mewalk",(mewalk+1));
      		   editer2.commit();
      		   

	      	   Intent intent = new Intent("SAPWalkAction");
	      	   sendBroadcast(intent);
	       }
           else if(strToUpdateUI.equals("match"))
           {
              //랜덤 찾기
        	  //서버통신
           }
	       else if(strToUpdateUI.equals("setup"))
	       {
	    	   try {
          		 //서버통신 
          		 //매치중 아니면 0 을 넣으면됨
          		 SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
          		 int mewalk = sp2.getInt("mewalk", 0);
          		 int youwalk = sp2.getInt("youwalk", 0);
          		 String mename = sp2.getString("mename", "");
          		 String youname = sp2.getString("youname", "");
          		 String youemail = sp2.getString("youemail", "");
          		 
          		 HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GEAR_STEP);
      	  		 ArrayList<Params> loginParams = new ArrayList<Params>();
      	  		 loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
      	  		 loginParams.add(new Params("fri_email", youemail));
      	  		
      	  		 loginService.setParam(loginParams);
      	  		 loginService.doAsyncExecute(SAPServiceProvider.this);
	    	   }
	    	   catch (Exception e1) {
	     			// TODO Auto-generated catch block
	     				e1.printStackTrace();
	     			}
	       }
		}
		@Override
		protected void onServiceConnectionLost(int errorCode) {
			Log.e(TAG, "onServiceConectionLost  for peer = " + mConnectionId
					+ "error code =" + errorCode);

			if (mConnectionsMap != null) {
				mConnectionsMap.remove(mConnectionId);
			}
		}
	}

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate of smart view Provider Service");
        
        SA mAccessory = new SA();
        try {
        	mAccessory.initialize(this);
        } catch (SsdkUnsupportedException e) {
        	// Error Handling
        } catch (Exception e1) {
            Log.e(TAG, "Cannot initialize Accessory package.");
            e1.printStackTrace();
			/*
			 * Your application can not use Accessory package of Samsung
			 * Mobile SDK. You application should work smoothly without using
			 * this SDK, or you may want to notify user and close your app
			 * gracefully (release resources, stop Service threads, close UI
			 * thread, etc.)
			 */
            stopSelf();
        }

    }	    

	@Override
	protected void onFindPeerAgentResponse(SAPeerAgent arg0, int arg1) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onFindPeerAgentResponse  arg1 =" + arg1);
	}

	@Override
	protected void onServiceConnectionResponse(SASocket thisConnection,
			int result) {
		if (result == CONNECTION_SUCCESS) {
			
			if (thisConnection != null) {
				WalkProviderConnection myConnection = (WalkProviderConnection) thisConnection;

				if (mConnectionsMap == null) {
					mConnectionsMap = new HashMap<Integer, WalkProviderConnection>();
				}

				myConnection.mConnectionId = (int) (System.currentTimeMillis() & 255);

				Log.d(TAG, "onServiceConnection connectionID = "
						+ myConnection.mConnectionId);

				mConnectionsMap.put(myConnection.mConnectionId, myConnection);
				Log.e(TAG, "Connection Success");
			} else {
				Log.e(TAG, "SASocket object is null");
			}
		} else if (result == CONNECTION_ALREADY_EXIST) 
		{
			Log.e(TAG, "onServiceConnectionResponse, CONNECTION_ALREADY_EXIST");
		} else {
			Log.e(TAG, "onServiceConnectionResponse result error =" + result);
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_MATCH_STEP"))
			{
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					String my_step = object.getJSONObject("data").optString("my_step","");
					String fri_step = object.getJSONObject("data").optString("fri_step","");
					int my_sp = 0;
	                int fri_sp = 0;
				    if(my_step == null || my_step.equals("") || my_step.equals("null") )
				    {
					 	Log.d("mainWalkUp","my_step_error");
				    }
				    else
				    {
					 	my_sp = Integer.parseInt(my_step);
				    }
				    if(fri_step == null || fri_step.equals("") || fri_step.equals("null"))
				    {
					   Log.d("mainWalkUp","fri_step_error");
				    }
				    else
				    {
						fri_sp = Integer.parseInt(fri_step);
				    }
				
					final WalkProviderConnection uHandler = mConnectionsMap.get(Integer
		   					.parseInt(String.valueOf(id)));
		   			if(uHandler == null){
		   				Log.e(TAG,"Error, can not get HelloAccessoryProviderConnection handler");
		   				return;
		   			}
					final JSONObject message = new JSONObject();
					
		            Log.d("SAP MESSAGE", message.toString());
					SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
					SharedPreferences.Editor editer2 = sp2.edit();
					editer2.putInt("youwalk",fri_sp);
					editer2.commit();
					
					int mewalk = sp2.getInt("mewalk", 0);
					int youwalk = sp2.getInt("youwalk", 0);
					String mename = sp2.getString("mename", "");
					String youname = sp2.getString("youname", "");
           		 	String youemail = sp2.getString("youemail", "");
           		 
           		 	message.put("code", "call");
           		 	message.put("mewalk", mewalk); //내걸음수
                 	message.put("mename", mename ); 
                 	message.put("youwalk", youwalk); //상대걸음수
                 	message.put("youname", youname); //상대이름
                 	
                 	Log.d("SAP MESSAGE", message.toString());
                 
                 	if(uHandler == null){
                 		Log.e(TAG,"Error, can not get HelloAccessoryProviderConnection handler");
                 		return;
                 	}
                 	new Thread(new Runnable() 
                 	{
                 		public void run()
                 		{
                 			try
                 			{
                 				uHandler.send(WALK_CHANNEL_ID, message.toString().getBytes());
                 			} 
                 			catch (IOException e)
                 			{
                 				e.printStackTrace();
                 			}
                 		}
                 	}).start();
				}
				
				else
				{
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
				
		                
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_GEAR_STEP"))
			{
				
				
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					String my_step = object.getJSONObject("data").optString("my_step","");
					String fri_step = object.getJSONObject("data").optString("fri_step","");
					int my_sp = 0;
	                int fri_sp = 0;
				    if(my_step == null || my_step.equals("") || my_step.equals("null") )
				    {
						Log.d("mainWalkUp","my_step_error");
				    }
				    else
				    {
						my_sp = Integer.parseInt(my_step);
				    }
				    if(fri_step == null || fri_step.equals("") || fri_step.equals("null"))
				    {
					   Log.d("mainWalkUp","fri_step_error");
				    }
				    else
				    {
						fri_sp = Integer.parseInt(fri_step);
				    }
					
					final WalkProviderConnection uHandler = mConnectionsMap.get(Integer
		   					.parseInt(String.valueOf(id)));
		   			if(uHandler == null){
		   				Log.e(TAG,"Error, can not get HelloAccessoryProviderConnection handler");
		   				return;
		   			}
					final JSONObject message = new JSONObject();
					
		            Log.d("SAP MESSAGE", message.toString());
					SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
					SharedPreferences.Editor editer2 = sp2.edit();
					editer2.putInt("youwalk",fri_sp);
					editer2.commit();
					
					int mewalk = sp2.getInt("mewalk", 0);
					int youwalk = sp2.getInt("youwalk", 0);
           		 
           		 	message.put("code", "setup");
           		 	message.put("mewalk", mewalk); //내걸음수
           		 	message.put("youwalk", youwalk); //상대걸음수
           		 	Log.d("SAP MESSAGE", message.toString());
                 
		   			 if(uHandler == null){
		   				Log.e(TAG,"Error, can not get HelloAccessoryProviderConnection handler");
		   				return;
		   			 }
		   			 new Thread(new Runnable() 
		   			 {
		   				public void run()
		   				{
		   					try
		   					{
		   						uHandler.send(WALK_CHANNEL_ID, message.toString().getBytes());
		   					} 
		   					catch (IOException e)
		   					{
		   						e.printStackTrace();
		   					}
		   				}
		   			 }).start();
				}
				else
				{
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
				
		                
			}
		}

	   catch (JSONException e1) {
			// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		catch(Exception e )
		{
			e.printStackTrace();
		}
//		finally
//		{
//			stopProgressDialog() ;
//		}
	}
}