package com.ssm.walk_match.component;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ssm.walk_match.R;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.service.ServiceType;

public class PushPopup extends LinearLayout implements View.OnClickListener, HttpClientNet.OnResponseListener  {
	
	private Context mContext;
	Dialog dialog;
	private View v = null;
	private ImageView img_change;
	private ImageView btnLeft;
	private ImageView btnRigth;

	private ImageView btnCancel;
	private ImageView btnOk;
	private int num = 1;
	private String email;
	private LoadingPopup loading;
	public PushPopup(Context context,String email) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		this.email = email;
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
		v = inflater.inflate(R.layout.popup_push, null);
		this.addView(v);
		dialog = new Dialog(mContext, R.style.Dialog);
		dialog.addContentView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		dialog.setCanceledOnTouchOutside(false);
		img_change = (ImageView)v.findViewById(R.id.push_img_change);
		btnLeft = (ImageView)v.findViewById(R.id.push_left);
		btnRigth = (ImageView)v.findViewById(R.id.push_right);
		btnCancel = (ImageView)v.findViewById(R.id.btn_cancel);
		btnOk = (ImageView)v.findViewById(R.id.btn_ok);
		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnRigth.setOnClickListener(this);
		show();
		
	}
	public void requestGcm()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GCM_GO_MESSAGE);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		Log.d("my",LoginObject.getInstance().getEmail());
		Log.d("fri",email);
		loginParams.add(new Params("name", ""));
		loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
		loginParams.add(new Params("fri_email", email));
		loginParams.add(new Params("sendname",num+""));
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
			case R.id.push_left:
			{
				if(num == 1)
				{
					img_change.setBackgroundResource(R.drawable.tease_imt_3);
					num = 3;
				}
				else if(num == 2)
				{
					img_change.setBackgroundResource(R.drawable.tease_imt_1);
					num = 1;
				}
				else
				{
					img_change.setBackgroundResource(R.drawable.tease_imt_2);
					num = 2;
				}
				break;
			}
			case R.id.push_right:
			{
				if(num == 1)
				{
					img_change.setBackgroundResource(R.drawable.tease_imt_2);
					num = 2;
				}
				else if(num == 2)
				{
					img_change.setBackgroundResource(R.drawable.tease_imt_3);
					num = 3;
				}
				else
				{
					img_change.setBackgroundResource(R.drawable.tease_imt_1);
					num = 1;
				}
				break;
			}

			case R.id.btn_cancel:
			{
				dismissPopup();
				break;
			}
			case R.id.btn_ok:
			{
				requestGcm();
				dismissPopup();
				break;
			}
		}
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			
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
