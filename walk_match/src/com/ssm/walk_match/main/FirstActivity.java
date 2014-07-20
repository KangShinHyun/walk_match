package com.ssm.walk_match.main;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.ssm.walk_match.R;
import com.ssm.walk_match.component.LoadingPopup;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.service.ServiceType;
import com.ssm.walk_match.util.AppManager;

public class FirstActivity extends Activity implements View.OnClickListener{

	private LinearLayout mBtnLogin;
	private LinearLayout mBtnFacebookLogin;
	private LinearLayout mBtnSignUp;
	private LoadingPopup loading;

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
				Toast.makeText(this, "Expect the next version", Toast.LENGTH_LONG).show();
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
	

}
