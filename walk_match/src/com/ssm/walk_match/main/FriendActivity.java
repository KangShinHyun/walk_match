package com.ssm.walk_match.main;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ssm.walk_match.R;
import com.ssm.walk_match.list.FriendListView;
import com.ssm.walk_match.object.FriendObject;
import com.ssm.walk_match.util.AppManager;

public class FriendActivity extends BaseActivity {

	
	private Context mContext;
	private ImageView btn_match;
	private ImageView btn_friend;
	private ImageView btn_me;
	private ImageView btn_ranking;
	private RelativeLayout btn_message; 
	private FriendListView friendListView;
	private LinearLayout middle_list;
	@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend);
        mContext = this;
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
		btn_match = (ImageView)findViewById(R.id.btn_match);
		btn_friend = (ImageView)findViewById(R.id.btn_friend);
		btn_me = (ImageView)findViewById(R.id.btn_me);
		btn_ranking = (ImageView)findViewById(R.id.btn_ranking);
		middle_list = (LinearLayout)findViewById(R.id.middle_layout_middle2);
		
		friendListView = new FriendListView(this);
		ArrayList<FriendObject> o = new ArrayList<FriendObject>();
		o.add(new FriendObject("http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2","°­½ÅÇö"));
		
		friendListView.setData(o);
		middle_list.addView(friendListView);
		btn_message.setOnClickListener(this);
		btn_match.setOnClickListener(this);
		btn_friend.setOnClickListener(this);
		btn_me.setOnClickListener(this);
		btn_ranking.setOnClickListener(this);
		
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
		super.onClick(v);
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		
		}
	
	}


}
