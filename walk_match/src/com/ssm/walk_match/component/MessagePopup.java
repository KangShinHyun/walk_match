package com.ssm.walk_match.component;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ssm.walk_match.R;
import com.ssm.walk_match.list.MessageListView;
import com.ssm.walk_match.object.MessageObject;

public class MessagePopup extends LinearLayout  {
	
	private Context mContext;
	Dialog dialog;
	private View v = null;
	private LinearLayout m_oList;
	private MessageListView messageListView;
	public MessagePopup(Context context) {
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
		v = inflater.inflate(R.layout.popup_message, null);
		this.addView(v);
		dialog = new Dialog(mContext, R.style.Dialog);
		dialog.addContentView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		dialog.setCanceledOnTouchOutside(false);
		m_oList = (LinearLayout)v.findViewById(R.id.message_list);
		messageListView = new MessageListView(mContext);
		ArrayList<MessageObject> test_object = new ArrayList<MessageObject>();
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다, 아름다운밤이에요, 오오오오","2009.3.4",""));

		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));

		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		messageListView.setData(test_object,this);
		m_oList.addView(messageListView);
		show();
		
	}
	
}
