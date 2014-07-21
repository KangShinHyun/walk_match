package com.ssm.walk_match.main;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.ssm.walk_match.R;
import com.ssm.walk_match.component.LoadingPopup;
import com.ssm.walk_match.component.NationSelectPopup;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.service.ServiceType;
import com.ssm.walk_match.util.AppManager;

public class FirstActivity extends Activity implements View.OnClickListener,HttpClientNet.OnResponseListener{

	private LinearLayout mBtnLogin;
	private LinearLayout mBtnFacebookLogin;
	private LinearLayout mBtnSignUp;
	private LoadingPopup loading;
	private String email;
	private String userName;
	private String thum;
	private Handler m_oHandler;
	private int nation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_start);
		setUi();
		AppManager.getInstance().setActivity(this);
	}
    @Override
    protected void onResume() {
        super.onResume();

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
    public void setUi()
    {
    	mBtnLogin = (LinearLayout)findViewById(R.id.login_btn);
    	mBtnFacebookLogin = (LinearLayout)findViewById(R.id.login_facebook);
    	mBtnSignUp = (LinearLayout)findViewById(R.id.login_sign);
    	mBtnLogin.setOnClickListener(this);
    	mBtnFacebookLogin.setOnClickListener(this);
    	mBtnSignUp.setOnClickListener(this);
    	 m_oHandler = new Handler() {
			  public void handleMessage(android.os.Message msg) {
				 nation = msg.arg1;
				 SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
				 SharedPreferences.Editor editer = sp.edit();
				 editer.putBoolean("autologinboolean", true);
				 editer.putBoolean("facebook", true);
				 editer.putString("name", userName);
				 editer.putString("email", email);
				 editer.putString("pwd", "");
				 editer.commit();
				 requestJoin(email, "", userName,thum,nation);
				
			  };
		 };
    	
    	
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
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
    public void requestLogin()
   	{
   		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
   		ArrayList<Params> loginParams = new ArrayList<Params>();
   		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
   		SharedPreferences.Editor editer = sp.edit();
   		loginParams.add(new Params("facebook", sp.getBoolean("facebook", false)+""));
   		loginParams.add(new Params("email",email));
   		loginParams.add(new Params("pwd",sp.getString("pwd", "")));
   		loginService.setParam(loginParams);
   		loginService.doAsyncExecute(this);
   		startProgressDialog();
   	}
    public void requestJoin(String email, String pwd, String name,String thum,int nation)
   	{
   		//회원가입 요청
   		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN);
   		ArrayList<Params> loginParams = new ArrayList<Params>();
   		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
   		SharedPreferences.Editor editer = sp.edit();
   		loginParams.add(new Params("facebook", sp.getBoolean("facebook", true)+""));
   		loginParams.add(new Params("email", email));
   		loginParams.add(new Params("pwd", pwd));
   		loginParams.add(new Params("name", name));
   		loginParams.add(new Params("thum", thum));
   		loginParams.add(new Params("nation", nation+""));
   		loginService.setParam(loginParams);
   		loginService.doAsyncExecute(this);
   		startProgressDialog();
   	}
    public void reqeustDoubleId(String email)
	{
    	Log.d("doubleCheck",email);
		//중복체크 요청
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN_DOUBLE_ID);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		this.email = email;
		loginParams.add(new Params("email",email));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.login_btn:
			{
				Intent intent =  new Intent(this.getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				break;
			}
			case R.id.login_facebook:
			{
//				Toast.makeText(this, "Expect the next version", Toast.LENGTH_LONG).show();
			  	Session.openActiveSession(this, true, new Session.StatusCallback() {
			
			        // callback when session changes state
			        @Override
			        public void call(Session session, SessionState state, Exception exception) {
			          if (session.isOpened()) {
			
			            // make request to the /me API
			            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
			
			              // callback after Graph API response with user object
			              @Override
			              public void onCompleted(GraphUser user, Response response) {
			                if (user != null) {
			                	Log.d("inFacebook Complete","Complete in");
			                	
								String mUserId = user.getId();
							    userName = user.getName();
							    email = mUserId;
							    thum = "https://graph.facebook.com/" + mUserId  + "/picture?type=large";
							    reqeustDoubleId(mUserId);
							    Log.d("inFacebook Complete",thum);
								

			                }
			              }
			            });
			          }
			        }
			      });
				break;
			}
			case R.id.login_sign:
			{
				Intent intent =  new Intent(this.getApplicationContext(), SignActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				break;
			}
			
		}
	
	}
	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_JOIN_DOUBLE_ID"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					//아이디가 없을때 회원가입 진행
					Log.d(" onResponseReceived MSG_JOIN_DOUBLE_IDMSG_JOIN_DOUBLE_ID",email);
					NationSelectPopup popup = new NationSelectPopup(FirstActivity.this);
					popup.setHandler(m_oHandler);
					//m_oImgDoubleCheck.setBackgroundResource(R.drawable.check_double);
				}				
				else	
				{
					Log.d(" onResponseReceived requestLogin",email);
					//아이다가 있을때 로그인 시작
					requestLogin();
					//m_oImgDoubleCheck.setBackgroundResource(R.drawable.check_double_no);
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_LOGIN"))
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
					int match = object.getJSONObject("data").optInt("match",0);
					LoginObject.getInstance().setLogin(name,email,img,Integer.parseInt(nation),pwd,Integer.parseInt(friend),Integer.parseInt(victory),Integer.parseInt(worldNum),match);
					Intent intent =  new Intent(this.getApplicationContext(), MainActivity.class);
					startActivity(intent);
					overridePendingTransition(0,0);
					finish();
					
					SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
					SharedPreferences.Editor editer = sp.edit();
					
					editer.putBoolean("autologinboolean", true);
					editer.putString("email", email);
					editer.putString("pwd", pwd);
					editer.commit();
					
					SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
					SharedPreferences.Editor editer2 = sp2.edit();
					editer2.putString("mename",name);
					editer2.putString("meemail",email);
					editer2.putString("menation", nation);
					editer2.commit();
					
				}
				else
				{
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG.JOIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					requestLogin();
				}
				else
				{
					Toast.makeText(this, "Join failed.", Toast.LENGTH_SHORT).show();
					Intent intent =  new Intent(this.getApplicationContext(), FirstActivity.class);
		   			startActivity(intent);
		   			overridePendingTransition(0,0);
		   			finish();
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
