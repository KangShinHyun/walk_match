package com.ssm.walk_match.main;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ssm.walk_match.R;
import com.ssm.walk_match.component.LoadingPopup;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.service.ServiceType;
import com.ssm.walk_match.util.AppManager;

public class LoginActivity extends Activity implements View.OnClickListener, HttpClientNet.OnResponseListener{

	private EditText mEditId;
	private EditText mEditPwd;
	private LinearLayout mBtnLogin;
	private LinearLayout mBtnForget;

    private LoadingPopup loading;
	
	@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		setUi();
		AppManager.getInstance().setActivity(this);
	}
    @Override
    protected void onResume() {
        super.onResume();

    }
    public void setUi()
    {
    	mBtnLogin = (LinearLayout)findViewById(R.id.login_btn_real);
    	mBtnForget = (LinearLayout)findViewById(R.id.forget_id_pwd);
    	mEditId = (EditText)findViewById(R.id.edit_txt_id);
    	mEditPwd = (EditText)findViewById(R.id.edit_txt_pwd);
    	mBtnLogin.setOnClickListener(this);
    	mBtnForget.setOnClickListener(this);
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
    @Override
	public void onBackPressed() {
    		Intent intent =  new Intent(this.getApplicationContext(), FirstActivity.class);
			startActivity(intent);
			overridePendingTransition(0,0);
			finish();
    }
    public void requestLogin()
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		loginParams.add(new Params("facebook", sp.getBoolean("facebook", false)+""));
		loginParams.add(new Params("email",mEditId.getText().toString()));
		loginParams.add(new Params("pwd",mEditPwd.getText().toString()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestLogin(String id)
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		loginParams.add(new Params("facebook", sp.getBoolean("facebook", true)+""));
		loginParams.add(new Params("email",id));
		loginParams.add(new Params("pwd",""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestLogin(String id,String pwd)
  	{
  		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
  		ArrayList<Params> loginParams = new ArrayList<Params>();
  		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
  		SharedPreferences.Editor editer = sp.edit();
  		loginParams.add(new Params("facebook", sp.getBoolean("facebook", true)+""));
  		loginParams.add(new Params("email",id));
  		loginParams.add(new Params("pwd",pwd));
  		loginService.setParam(loginParams);
  		loginService.doAsyncExecute(this);
  		startProgressDialog();
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
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.login_btn_real:
			{
				if(mEditId.getText().toString().equals(""))
				{
					Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_LONG).show();
				}
				else
				{
					if(mEditPwd.getText().toString().equals(""))
					{
						Toast.makeText(this, "패스드워드를 입력해주세요", Toast.LENGTH_LONG).show();
					}
					else
					{
						requestLogin();
					}
				}
				
				break;
			}
			case R.id.forget_id_pwd:
			{
				Toast.makeText(this, "Expect the next version", Toast.LENGTH_LONG).show();
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
