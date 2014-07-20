package com.ssm.walk_match.main;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssm.walk_match.R;
import com.ssm.walk_match.list.RankFriendListView;
import com.ssm.walk_match.list.RankWorldListView;
import com.ssm.walk_match.object.RankObject;
import com.ssm.walk_match.util.AppManager;

public class RankActivity extends BaseActivity {

	
	private Context mContext;
	private LinearLayout btn_world;
	private LinearLayout btn_firend;
	private LinearLayout rank_list_view;
	private LinearLayout me_rank_layout;
	private RankWorldListView rankWorldListView;
	private RankFriendListView rankFriendListView;
	private TextView txt_world;
	private TextView txt_friend;
	private boolean check_btn = false;
	private ImageView btn_match;
	private ImageView btn_friend;
	private ImageView btn_me;
	private ImageView btn_ranking;
	private RelativeLayout btn_message; 
	@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank);
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
		btn_message.setOnClickListener(this);
		btn_match.setOnClickListener(this);
		btn_friend.setOnClickListener(this);
		btn_me.setOnClickListener(this);
		btn_ranking.setOnClickListener(this);
		
    	btn_world = (LinearLayout)findViewById(R.id.btn_world);
    	btn_firend = (LinearLayout)findViewById(R.id.btn_frd);
    	rank_list_view = (LinearLayout)findViewById(R.id.layout_list_me);
    	me_rank_layout = (LinearLayout)findViewById(R.id.layout_me_content);
    	txt_world = (TextView)findViewById(R.id.btn_world_txt);
    	txt_friend = (TextView)findViewById(R.id.btn_frd_txt);
		
    	rankWorldListView = new RankWorldListView(this);
		ArrayList<RankObject> o = new ArrayList<RankObject>();
		o.add(new RankObject("1","1","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2","강신현","100","98","100","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2"));
		
		rankWorldListView.setData(o);
		rank_list_view.addView(rankWorldListView);
		btn_world.setOnClickListener(this);
		btn_firend.setOnClickListener(this);
		me_rank_layout.setOnClickListener(this);
		
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
			case R.id.btn_world:
			{
				if(check_btn)
				{
					if(rankWorldListView != null)
					{
						rank_list_view.removeAllViews();
						rankWorldListView.notifyData();
						rank_list_view.addView(rankWorldListView);
					}
					else
					{
						rank_list_view.removeAllViews();
						rankWorldListView = new RankWorldListView(this);
						ArrayList<RankObject> o = new ArrayList<RankObject>();
						o.add(new RankObject("1","1","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2","강신현","100","98","100","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2"));
						
						rankWorldListView.setData(o);
						rank_list_view.addView(rankWorldListView);
					}
					btn_world.setBackgroundResource(R.drawable.up_tab_world_back1);
					btn_firend.setBackgroundResource(R.drawable.up_tab_frd_back0);
					txt_world.setBackgroundResource(R.drawable.up_tab_world_text1);
					txt_friend.setBackgroundResource(R.drawable.up_tab_frd_text0);
					check_btn = false;
				}
				break;
			}
			case R.id.btn_frd:
			{
				if(!check_btn)
				{
					if(rankFriendListView != null)
					{
						rank_list_view.removeAllViews();
						rankFriendListView.notifyData();
						rank_list_view.addView(rankFriendListView);
					}
					else
					{
						rank_list_view.removeAllViews();
						rankFriendListView = new RankFriendListView(this);
						ArrayList<RankObject> o = new ArrayList<RankObject>();
						o.add(new RankObject("1","1","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2","강신현","100","98","100","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2"));
						
						rankFriendListView.setData(o);
						rank_list_view.addView(rankFriendListView);
					}
					btn_world.setBackgroundResource(R.drawable.up_tab_world_back0);
					btn_firend.setBackgroundResource(R.drawable.up_tab_frd_back1);
					txt_world.setBackgroundResource(R.drawable.up_tab_world_text0);
					txt_friend.setBackgroundResource(R.drawable.up_tab_frd_text1);
					check_btn = true;
				}
				break;
			}

		}
	
	}


}
