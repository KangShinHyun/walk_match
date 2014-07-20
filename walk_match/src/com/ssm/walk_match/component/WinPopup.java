package com.ssm.walk_match.component;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ssm.walk_match.R;
import com.ssm.walk_match.net.HttpClientNet;

public class WinPopup extends LinearLayout implements View.OnClickListener, HttpClientNet.OnResponseListener  {
	
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
	public WinPopup(Context context,String email,Handler handler) {
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
		v = inflater.inflate(R.layout.popup_win, null);
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
				Message msg = new Message();
				handler.sendMessage(msg);
				dismissPopup();
				break;
			}
			case R.id.btn_ok:
			{
				Message msg = new Message();
				handler.sendMessage(msg);
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
