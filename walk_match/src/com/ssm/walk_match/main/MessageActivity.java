package com.ssm.walk_match.main;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssm.walk_match.R;
import com.ssm.walk_match.list.MessageContentListView;
import com.ssm.walk_match.list.RankFriendListView;
import com.ssm.walk_match.list.RankWorldListView;
import com.ssm.walk_match.object.MessageObject;
import com.ssm.walk_match.object.RankObject;
import com.ssm.walk_match.util.AppManager;

public class MessageActivity extends BaseActivity {

	private LinearLayout rank_list_view;
	private EditText editText;
	private LinearLayout btn_send;
	private Context mContext;
	private RelativeLayout btn_message; 
	private TextView txt_title;
	private MessageContentListView messageContentListView;
	private String toMan;
	@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
        mContext = this;
        toMan = getIntent().getStringExtra("toMan");
        setUi();
        AppManager.getInstance().setActivity(this);

	}

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void setUi()
    {
    	btn_message = (RelativeLayout)findViewById(R.id.btn_chat);
    	btn_message.setOnClickListener(this);
		editText = (EditText)findViewById(R.id.text_bottom);
		btn_send = (LinearLayout)findViewById(R.id.text_bottom_send);
		btn_send.setOnClickListener(this);
    	rank_list_view = (LinearLayout)findViewById(R.id.layout_list_me);
		txt_title = (TextView)findViewById(R.id.title_name);
		txt_title.setText(toMan);
    	messageContentListView = new MessageContentListView(this);
    	ArrayList<MessageObject> test_object = new ArrayList<MessageObject>();
		test_object.add(new MessageObject("강신현","ㅇㅇ","안녕하세요 반갑습니다 오랜만입니다, 아름다운밤이에요, 오오오오","2009.3.4",""));

		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));

		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		test_object.add(new MessageObject("이호호","강신현","안녕하세요 반갑습니다 오랜만입니다","2009.3.4",""));
		messageContentListView.setData(test_object);
		rank_list_view.addView(messageContentListView);
		
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
    public void notifyData(String toMan) {
    	this.toMan = toMan;
    	txt_title.setText(toMan);
    	rank_list_view.removeAllViews();
    	ArrayList<MessageObject> test_object = new ArrayList<MessageObject>();
		test_object.add(new MessageObject("강신현","ㅇㅇ","안녕하세요 반갑습니다 오랜만입니다, 아름다운밤이에요, 오오오오","2009.3.4",""));
    	messageContentListView.setData(test_object);
    	messageContentListView.notifyData();
    	rank_list_view.addView(messageContentListView);
	}
	@Override
	public void onClick(View v) 
	{
		super.onClick(v);
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.text_bottom_send:
			{
				
				break;
			}
			

		}
	
	}


}
