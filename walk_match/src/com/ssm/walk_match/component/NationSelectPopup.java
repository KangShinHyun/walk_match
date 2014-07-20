package com.ssm.walk_match.component;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ssm.walk_match.R;

public class NationSelectPopup extends LinearLayout implements View.OnClickListener {
	
	private Context mContext;
	Dialog dialog;
	private View v = null;
	private LinearLayout m1;
	private LinearLayout m2;
	private LinearLayout m3;
	private LinearLayout m4;
	private LinearLayout m5;
	private LinearLayout m6;
	private LinearLayout m7;
	private LinearLayout m8;
	private LinearLayout m9;
	private LinearLayout m10;
	private LinearLayout m11;
	private LinearLayout m12;
	private LinearLayout m13;
	private LinearLayout m14;
	private LinearLayout m15;
	private LinearLayout m16;
	private LinearLayout m17;
	private LinearLayout m18;
	private LinearLayout m19;
	private LinearLayout m20;
	private LinearLayout m21;
	private LinearLayout m22;
	private LinearLayout m23;
	private LinearLayout m24;
	private LinearLayout m25;
	private LinearLayout m26;
	private LinearLayout m27;
	private LinearLayout m28;
	private LinearLayout m29;
	private LinearLayout m30;
	private LinearLayout m31;
	private LinearLayout m32;
	private LinearLayout m33;
	private LinearLayout m34;
	private LinearLayout m35;
	private LinearLayout m36;
	private LinearLayout m37;
	private LinearLayout m38;
	
	private LinearLayout btnCancel;
	private LinearLayout btnOk;
	
	private int[] nation_id = {R.id.nation_1,R.id.nation_2,R.id.nation_3,R.id.nation_4,R.id.nation_5,R.id.nation_6,R.id.nation_7,R.id.nation_8,
			R.id.nation_9,R.id.nation_10,R.id.nation_11,R.id.nation_12,R.id.nation_13,R.id.nation_14,R.id.nation_15,R.id.nation_16,R.id.nation_17,
			R.id.nation_18,R.id.nation_19,R.id.nation_20,R.id.nation_21,R.id.nation_22,R.id.nation_23,R.id.nation_24,R.id.nation_25,R.id.nation_26,
			R.id.nation_27,R.id.nation_28,R.id.nation_29,R.id.nation_30,R.id.nation_31,R.id.nation_32,R.id.nation_33,R.id.nation_34,R.id.nation_35,
			R.id.nation_36,R.id.nation_37,R.id.nation_38};
	
	private int selectNum = 0;
	private int selectLayout = 0;
	private Handler handler;
	public NationSelectPopup(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
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
		v = inflater.inflate(R.layout.popup_nation, null);
		this.addView(v);
		dialog = new Dialog(mContext, R.style.Dialog);
		dialog.addContentView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		dialog.setCanceledOnTouchOutside(false);
		m1 = (LinearLayout)v.findViewById(R.id.nation_1);
		m2 = (LinearLayout)v.findViewById(R.id.nation_2);
		m3 = (LinearLayout)v.findViewById(R.id.nation_3);
		m4 = (LinearLayout)v.findViewById(R.id.nation_4);
		m5 = (LinearLayout)v.findViewById(R.id.nation_5);
		m6 = (LinearLayout)v.findViewById(R.id.nation_6);
		m7 = (LinearLayout)v.findViewById(R.id.nation_7);
		m8 = (LinearLayout)v.findViewById(R.id.nation_8);
		m9 = (LinearLayout)v.findViewById(R.id.nation_9);
		m10 = (LinearLayout)v.findViewById(R.id.nation_10);
		m11 = (LinearLayout)v.findViewById(R.id.nation_11);
		m12 = (LinearLayout)v.findViewById(R.id.nation_12);
		m13 = (LinearLayout)v.findViewById(R.id.nation_13);
		m14 = (LinearLayout)v.findViewById(R.id.nation_14);
		m15 = (LinearLayout)v.findViewById(R.id.nation_15);
		m16 = (LinearLayout)v.findViewById(R.id.nation_16);
		m17 = (LinearLayout)v.findViewById(R.id.nation_17);
		m18 = (LinearLayout)v.findViewById(R.id.nation_18);
		m19 = (LinearLayout)v.findViewById(R.id.nation_19);
		m20 = (LinearLayout)v.findViewById(R.id.nation_20);
		m21 = (LinearLayout)v.findViewById(R.id.nation_21);
		m22 = (LinearLayout)v.findViewById(R.id.nation_22);
		m23 = (LinearLayout)v.findViewById(R.id.nation_23);
		m24 = (LinearLayout)v.findViewById(R.id.nation_24);
		m25 = (LinearLayout)v.findViewById(R.id.nation_25);
		m26 = (LinearLayout)v.findViewById(R.id.nation_26);
		m27 = (LinearLayout)v.findViewById(R.id.nation_27);
		m28 = (LinearLayout)v.findViewById(R.id.nation_28);
		m29 = (LinearLayout)v.findViewById(R.id.nation_29);
		m30 = (LinearLayout)v.findViewById(R.id.nation_30);
		m31 = (LinearLayout)v.findViewById(R.id.nation_31);
		m32 = (LinearLayout)v.findViewById(R.id.nation_32);
		m33 = (LinearLayout)v.findViewById(R.id.nation_33);
		m34 = (LinearLayout)v.findViewById(R.id.nation_34);
		m35 = (LinearLayout)v.findViewById(R.id.nation_35);
		m36 = (LinearLayout)v.findViewById(R.id.nation_36);
		m37 = (LinearLayout)v.findViewById(R.id.nation_37);
		m38 = (LinearLayout)v.findViewById(R.id.nation_38);
		
		m1.setOnClickListener(this);
		m2.setOnClickListener(this);
		m3.setOnClickListener(this);
		m4.setOnClickListener(this);
		m5.setOnClickListener(this);
		m6.setOnClickListener(this);
		m7.setOnClickListener(this);
		m8.setOnClickListener(this);
		m9.setOnClickListener(this);
		m10.setOnClickListener(this);
		m11.setOnClickListener(this);
		m12.setOnClickListener(this);
		m13.setOnClickListener(this);
		m14.setOnClickListener(this);
		m15.setOnClickListener(this);
		m16.setOnClickListener(this);
		m17.setOnClickListener(this);
		m18.setOnClickListener(this);
		m19.setOnClickListener(this);
		m20.setOnClickListener(this);
		m21.setOnClickListener(this);
		m22.setOnClickListener(this);
		m23.setOnClickListener(this);
		m24.setOnClickListener(this);
		m25.setOnClickListener(this);
		m26.setOnClickListener(this);
		m27.setOnClickListener(this);
		m28.setOnClickListener(this);
		m29.setOnClickListener(this);
		m30.setOnClickListener(this);
		m31.setOnClickListener(this);
		m32.setOnClickListener(this);
		m33.setOnClickListener(this);
		m34.setOnClickListener(this);
		m35.setOnClickListener(this);
		m36.setOnClickListener(this);
		m37.setOnClickListener(this);
		m38.setOnClickListener(this);
		
		btnCancel = (LinearLayout)v.findViewById(R.id.canel_btn);
		btnOk = (LinearLayout)v.findViewById(R.id.select_btn);
		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);
		show();
		
	}
	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}
	@Override
	public void onClick(View vv) {
		// TODO Auto-generated method stub
		if(vv.getId() == R.id.canel_btn)
		{
			dismissPopup();
		}
		else if(vv.getId() == R.id.select_btn)
		{
			int i = 0;
			for( i = 0 ; i < nation_id.length ;i++)
			{
				if(selectLayout == nation_id[i])
				{
					break;
				}
			}
			Message msg = new Message();
			msg.arg1 = i; // 0번부터니까 여기선 1번부터처리
			handler.sendMessage(msg);
			dismissPopup();
		}
		else
		{
			if(selectLayout != 0)
			{
				LinearLayout o = (LinearLayout) v.findViewById(selectLayout);
				o.setBackgroundResource(R.drawable.nation_select_nation_back_wht);
			}
			selectLayout = vv.getId();
			LinearLayout o = (LinearLayout) v.findViewById(selectLayout);
			o.setBackgroundResource(R.drawable.nation_select_nation_back_green);
			
		}
		
	}
	
}
