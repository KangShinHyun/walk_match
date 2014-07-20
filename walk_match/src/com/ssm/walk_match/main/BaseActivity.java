package com.ssm.walk_match.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.ssm.walk_match.R;
import com.ssm.walk_match.component.LoadingPopup;
import com.ssm.walk_match.component.rePushPopup;
import com.ssm.walk_match.util.AppManager;

public class BaseActivity extends Activity implements View.OnClickListener{

	private LoadingPopup loading;
	public static final int MAIN_ACTIVITY = 1;
	public static final int FRIEND_ACTIVITY = 2;
	public static final int ME_ACTIVITY = 3;
	public static final int RANKING_ACTIVITY = 4;
	public static final int MESSAGE_ACTIVITY = 5;
	public static int activity_num = MAIN_ACTIVITY; 
	@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
    @Override
	public void onBackPressed() {
    	if(activity_num != MAIN_ACTIVITY)
		{
    		Intent intent =  new Intent(this.getApplicationContext(), MainActivity.class);
			startActivity(intent);
			overridePendingTransition(0,0);
			finish();
			activity_num = MAIN_ACTIVITY;
    	}
    	else
    	{
    		finish();
    	}
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    public void setUi()
    {
		
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
	public void popup(String email)
	{
		rePushPopup o = new rePushPopup(this, email);
	}
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			
			case R.id.btn_chat:
			{
				Toast.makeText(this, "Expect the next version", Toast.LENGTH_LONG).show();
				//MessagePopup o = new MessagePopup(this);
				break;
			}
			case R.id.btn_match:
			{
				if(activity_num != MAIN_ACTIVITY)
				{
					Intent intent =  new Intent(this.getApplicationContext(), MainActivity.class);
					startActivity(intent);
					overridePendingTransition(0,0);
					finish();
		    	}
				else
				{
					((MainActivity)(AppManager.getInstance().getActivity())).onResume();
				}
				activity_num = MAIN_ACTIVITY;
				break;
			}
			case R.id.btn_friend:
			{
				Toast.makeText(this, "Expect the next version", Toast.LENGTH_LONG).show();
//				if(activity_num != FRIEND_ACTIVITY)
//				{
//					Intent intent =  new Intent(this.getApplicationContext(), FriendActivity.class);
//					startActivity(intent);
//					overridePendingTransition(0,0);
//					finish();
//		    	}
//				activity_num = FRIEND_ACTIVITY;
				break;
			}
			case R.id.btn_me:
			{
				//Toast.makeText(this, "Expect the next version", Toast.LENGTH_LONG).show();
				if(activity_num != ME_ACTIVITY)
				{
					Intent intent =  new Intent(this.getApplicationContext(), MeActivity.class);
					startActivity(intent);
					overridePendingTransition(0,0);
					finish();
		    	}
				activity_num = ME_ACTIVITY;
				break;
			}
			case R.id.btn_ranking:
			{
				//Toast.makeText(this, "Expect the next version", Toast.LENGTH_LONG).show();
				if(activity_num != RANKING_ACTIVITY)
				{
					Intent intent =  new Intent(this.getApplicationContext(), RankActivity.class);
					startActivity(intent);
					overridePendingTransition(0,0);
					finish();
				}
				activity_num = RANKING_ACTIVITY;
				break;
			}
		}
	
	}

}
