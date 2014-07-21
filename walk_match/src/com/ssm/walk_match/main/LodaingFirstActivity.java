package com.ssm.walk_match.main;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.ssm.walk_match.GCMIntentService;
import com.ssm.walk_match.R;
import com.ssm.walk_match.component.LoadingPopup;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.service.ServiceType;
import com.ssm.walk_match.util.AppManager;

public class LodaingFirstActivity extends Activity implements HttpClientNet.OnResponseListener {

	public static String phone;
	public static String regId;
    private LoadingPopup loading;
    Handler myHandler = new Handler();
    Runnable myRunnable = new Runnable() {
    	
  	  @Override
  	  public void run() {
  		Intent intent =  new Intent(LodaingFirstActivity.this, FirstActivity.class);
		startActivity(intent);
		overridePendingTransition(0,0);
		stopProgressDialog();
		finish();
  	 	}
  	 };
	@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
	 private void registGCM() {
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);

			regId = GCMRegistrar.getRegistrationId(this);

			if ("".equals(regId)) // 구글 가이드에는 regId.equals("")로 되어 있는데 Exception을
									// 피하기 위해 수정
				GCMRegistrar.register(this, GCMIntentService.SEND_ID);
			else
				Log.d("==============", regId);
		}
	    public void requestLogin(String id, String pwd) {
			HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
			ArrayList<Params> loginParams = new ArrayList<Params>();
			SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
			SharedPreferences.Editor editer = sp.edit();
			loginParams.add(new Params("facebook", sp.getBoolean("facebook", false)+""));
			loginParams.add(new Params("email", id));
			loginParams.add(new Params("pwd", pwd));
			loginService.setParam(loginParams);
			loginService.doAsyncExecute(this);
			startProgressDialog();
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		TelephonyManager systemService = (TelephonyManager)getSystemService    (Context.TELEPHONY_SERVICE);
		String PhoneNumber = systemService.getLine1Number();
		PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-10,PhoneNumber.length());
		phone="0"+PhoneNumber;
		registGCM();
		setUi();
		AppManager.getInstance().setActivity(this);
//		 try {
//		        PackageInfo info = getPackageManager().getPackageInfo(
//		                "com.ssm.walk_match", 
//		                PackageManager.GET_SIGNATURES);
//		        for (Signature signature : info.signatures) {
//		            MessageDigest md = MessageDigest.getInstance("SHA");
//		            md.update(signature.toByteArray());
//		            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//		            }
//		    } catch (NameNotFoundException e) {
//
//		    } catch (NoSuchAlgorithmException e) {
//
//		    }
		 
		 
	}
    @Override
    protected void onResume() {
        super.onResume();

    }
    public void setUi()
    {
    	SharedPreferences sp = getSharedPreferences("autologin",MODE_PRIVATE);
		if (sp.getBoolean("autologinboolean", false))
		{
			if (sp.getString("email", "").equals("")) 
			{
				startProgressDialog() ;
				myHandler.postDelayed(myRunnable, 1000);
				
			} 
			else 
			{
				requestLogin(sp.getString("email", ""),sp.getString("pwd", ""));
			}
		} 
		else 
		{
			startProgressDialog() ;
			myHandler.postDelayed(myRunnable, 1000);
		}
    }
    @Override
    protected void onPause() 
    {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() 
    {
        super.onDestroy();
    }
    
    public void startProgressDialog() 
	{
		if( loading == null )
		{
			loading = new LoadingPopup(this);
			loading.start();
		}
	}
	public void stopProgressDialog() 
	{
		if( loading != null )
		{
			loading.stop();
			loading = null;
		}
	}
	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_LOGIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					String email = object.getJSONObject("data").optString("email","");
					String img = object.getJSONObject("data").optString("img","");
					String name = object.getJSONObject("data").optString("name","");
					String nation = object.getJSONObject("data").optString("nation","");
					String pwd = object.getJSONObject("data").optString("pwd","");
					String friend = object.getJSONObject("data").optString("friend","");
					String victory = object.getJSONObject("data").optString("victory","");
					String worldNum = object.getJSONObject("data").optString("worldNum","");
					LoginObject.getInstance().setLogin(name,email,img,Integer.parseInt(nation),pwd,Integer.parseInt(friend),Integer.parseInt(victory),Integer.parseInt(worldNum));
					Intent intent =  new Intent(this.getApplicationContext(), MainActivity.class);
					startActivity(intent);
					overridePendingTransition(0,0);
					finish();
					
				}
				else
				{
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			stopProgressDialog() ;
		}
	}
	

}
