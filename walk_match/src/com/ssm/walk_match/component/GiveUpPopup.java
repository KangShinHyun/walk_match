package com.ssm.walk_match.component;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ssm.walk_match.R;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.service.ServiceType;

public class GiveUpPopup extends LinearLayout implements View.OnClickListener, HttpClientNet.OnResponseListener  {
	
	private Context mContext;
	Dialog dialog;
	private View v = null;
	private ImageView img_change;
	private ImageView btnCancel;
	private ImageView btnOk;
	private int num = 1;
	private String email;
	private LoadingPopup loading;
	private Handler handler;
	public GiveUpPopup(Context context,String email,Handler handler) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		this.email = email;
		this.handler = handler;
		initView();
		
	}
	
	public void show() {
		dialog.show();
	}
	
	public void dismissPopup(){
		dialog.dismiss();
	}
	

	private void initView() {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.popup_giveup, null);
		this.addView(v);
		dialog = new Dialog(mContext, R.style.Dialog);
		dialog.addContentView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		dialog.setCanceledOnTouchOutside(false);
		img_change = (ImageView)v.findViewById(R.id.push_img_change);
		btnCancel = (ImageView)v.findViewById(R.id.btn_cancel);
		btnOk = (ImageView)v.findViewById(R.id.btn_ok);
		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		show();
		
	}
	public void requestMatchGcmGiveUp(String email)
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GCM_GO_MATCH_GIVE_UP);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
		loginParams.add(new Params("fri_email", email));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
	public void startProgressDialog() 
	{
		if( loading == null )
		{
			loading = new LoadingPopup(mContext);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.btn_cancel:
			{
				dismissPopup();
				break;
			}
			case R.id.btn_ok:
			{
				requestMatchGcmGiveUp(email);
				dismissPopup();
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
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_GCM_GO_MATCH_GIVE_UP"))
			{
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					Message msg = new Message();
					msg.arg1 = 100; // 0번부터니까 여기선 1번부터처리
					handler.sendMessage(msg);
				}
				else
				{
					Toast.makeText(mContext, reason, Toast.LENGTH_LONG).show();
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
