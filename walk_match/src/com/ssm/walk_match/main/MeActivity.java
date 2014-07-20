package com.ssm.walk_match.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssm.walk_match.R;
import com.ssm.walk_match.list.MeHistoryListView;
import com.ssm.walk_match.list.RankFriendListView;
import com.ssm.walk_match.list.RankWorldListView;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.HistoryObject;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.object.RankObject;
import com.ssm.walk_match.service.ServiceType;
import com.ssm.walk_match.util.AppManager;
import com.ssm.walk_match.util.AsyncImageLoader;

public class MeActivity extends BaseActivity implements HttpClientNet.OnResponseListener{

	
	private Context mContext;
	private ImageView btn_match;
	private ImageView btn_friend;
	private ImageView btn_me;
	private ImageView btn_ranking;
	private RelativeLayout btn_message; 
	
	private ImageView img_me;
	private LinearLayout btn_chat_friend;
	private TextView txt_myname;
	private RelativeLayout mBtnFriFriend;
	private RelativeLayout mBtnFriVictory;
	private RelativeLayout mBtnFriWorld;
	private TextView mTxtFriFirend;
	private TextView mTxtFriVictory;
	private TextView mTextFriWorld;
	private LinearLayout mBtnFacebook;
	
	private LinearLayout mBtnHistory;
	private LinearLayout mBtnBadge;
	private TextView mTxtHistory;
	private TextView mTxtBadge;
	private TextView mTxtWin;
	private TextView mTxtLose;
	private LinearLayout mListView;
	private ArrayList<HistoryObject> listObject = new ArrayList<HistoryObject>();
	
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private MeHistoryListView meHisoryListView;
	private boolean check_btn = false;
	
	private RelativeLayout mNoLiveLayout;
	private RelativeLayout mLiveLayout;
	private ImageView mMyNationImg;
	private TextView mStepTxt;
	private ImageView mResultimg;
	private TextView mFriStepTxt;
	private TextView mFriNameTxt;
	private ImageView mFriNationImg;
	private TextView mTime;
	private int[] nation_img = {R.drawable.australia,R.drawable.austria,R.drawable.belgium,R.drawable.brazil
			,R.drawable.canada,R.drawable.china,R.drawable.czechrepublic,R.drawable.denmark
			,R.drawable.finland,R.drawable.france,R.drawable.germany,R.drawable.greece,R.drawable.hongkong
			,R.drawable.hungary,R.drawable.iceland,R.drawable.india,R.drawable.indonesia,R.drawable.italy
			,R.drawable.korea,R.drawable.japan,R.drawable.malaysia,R.drawable.mexico,R.drawable.netherland
			,R.drawable.newzeland,R.drawable.norway,R.drawable.poland,R.drawable.portugal,R.drawable.russia
			,R.drawable.saudiarabia,R.drawable.singapore,R.drawable.spain,R.drawable.sweden,R.drawable.switzerland
			,R.drawable.thailand,R.drawable.uae,R.drawable.unitedkingdom,R.drawable.unitedstatesofamerica,R.drawable.vietnam};
	@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me);
        mContext = this;
        setUi();
        AppManager.getInstance().setActivity(this);
        requestHistory();
	}

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void requestHistory()
  	{
  		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_HISTORY);
  		ArrayList<Params> loginParams = new ArrayList<Params>();
  		loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
  		
  		loginService.setParam(loginParams);
  		loginService.doAsyncExecute(this);
  		startProgressDialog();
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
		
		img_me = (ImageView)findViewById(R.id.img_me_picture);
		txt_myname = (TextView)findViewById(R.id.my_name_txt);
		btn_chat_friend = (LinearLayout)findViewById(R.id.btn_chat_go);
		mBtnFriFriend = (RelativeLayout)findViewById(R.id.me_friend_layout);
		mBtnFriVictory = (RelativeLayout)findViewById(R.id.me_victory_layout);
		mBtnFriWorld = (RelativeLayout)findViewById(R.id.me_world_layout_3333);
		mTxtFriFirend = (TextView)findViewById(R.id.me_friend_txt);
		mTxtFriVictory = (TextView)findViewById(R.id.me_victory_txt);
		mTextFriWorld = (TextView)findViewById(R.id.me_world_txt_3333);
		mBtnFacebook = (LinearLayout)findViewById(R.id.btn_facebook);
		
		mBtnHistory = (LinearLayout)findViewById(R.id.btn_history);
		mBtnBadge = (LinearLayout)findViewById(R.id.btn_badge);
		mTxtHistory = (TextView)findViewById(R.id.btn_history_txt);
		mTxtBadge = (TextView)findViewById(R.id.btn_badge_txt);
		
		mTxtWin = (TextView)findViewById(R.id.win_text);
		mTxtLose = (TextView)findViewById(R.id.lose_text);
		mListView = (LinearLayout)findViewById(R.id.list_match_up);
		m_oAsyncImageLoader.setImageDrawableAsync(img_me,LoginObject.getInstance().getImg(),getResources().getDrawable(R.drawable.cp_picture), getResources().getDrawable(R.drawable.cp_picture), mContext);
		txt_myname.setText(LoginObject.getInstance().getName());
		mTxtFriFirend.setText(LoginObject.getInstance().getFriend()+"");
		mTxtFriVictory.setText(LoginObject.getInstance().getVictory()+"");
		mTextFriWorld.setText(LoginObject.getInstance().getWorldNum()+"");
		
		mNoLiveLayout = (RelativeLayout) findViewById(R.id.live_no_tab);
		mLiveLayout = (RelativeLayout) findViewById(R.id.live_tab);
		mMyNationImg = (ImageView) findViewById(R.id.img_nation);
		mStepTxt = (TextView) findViewById(R.id.me_step);
		mResultimg = (ImageView) findViewById(R.id.situation);
		mFriStepTxt = (TextView) findViewById(R.id.fri_step);
		mFriNameTxt = (TextView) findViewById(R.id.fri_name);
		mFriNationImg = (ImageView) findViewById(R.id.fri_img_nation);
		mTime = (TextView)findViewById(R.id.match_time_txt);
		mBtnFacebook.setOnClickListener(this);
		mBtnHistory.setOnClickListener(this);
		mBtnBadge.setOnClickListener(this);
		GregorianCalendar o = new GregorianCalendar();
		String time = o.get(Calendar.YEAR)+". "+ (o.get(Calendar.MONTH)+1)+". "+ o.get(Calendar.DATE);
		SharedPreferences sp = getSharedPreferences("match_ing",MODE_PRIVATE);
		if (sp.getBoolean("match_ing", false))
		{
			SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
			int mewalk = sp2.getInt("mewalk", 0);
			int youwalk = sp2.getInt("youwalk", 0);
			mTime.setText(time);
			mNoLiveLayout.setVisibility(View.GONE);
			mLiveLayout.setVisibility(View.VISIBLE);
			mMyNationImg.setBackgroundResource(nation_img[LoginObject.getInstance().getNation()]);
			mStepTxt.setText(mewalk+"");
			mFriStepTxt.setText(youwalk+"");
			mFriNameTxt.setText(MainActivity.fri_name);
			mFriNationImg.setBackgroundResource(nation_img[MainActivity.fri_nation]);
		} 
		else 
		{
			mNoLiveLayout.setVisibility(View.VISIBLE);
			mLiveLayout.setVisibility(View.GONE);
		} 
		
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
			case R.id.btn_facebook:
			{
				
				break;
			}
			case R.id.btn_history:
			{
				if(check_btn)
				{
//					if(rankWorldListView != null)
//					{
//						rank_list_view.removeAllViews();
//						rankWorldListView.notifyData();
//						rank_list_view.addView(rankWorldListView);
//					}
//					else
//					{
//						rank_list_view.removeAllViews();
//						rankWorldListView = new RankWorldListView(this);
//						ArrayList<RankObject> o = new ArrayList<RankObject>();
//						o.add(new RankObject("1","1","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2","강신현","100","98","100","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2"));
//						
//						rankWorldListView.setData(o);
//						rank_list_view.addView(rankWorldListView);
//					}
					mBtnHistory.setBackgroundResource(R.drawable.bttn_history_back_1);
					mBtnBadge.setBackgroundResource(R.drawable.bttn_badge_back_0);
					mTxtHistory.setBackgroundResource(R.drawable.bttn_history_text_1);
					//mTxtBadge.setBackgroundResource(R.drawable.bttn_badge_text_0);
					check_btn = false;
				}
				break;
			}
			case R.id.btn_badge:
			{
				if(!check_btn)
				{
//					if(rankFriendListView != null)
//					{
//						rank_list_view.removeAllViews();
//						rankFriendListView.notifyData();
//						rank_list_view.addView(rankFriendListView);
//					}
//					else
//					{
//						rank_list_view.removeAllViews();
//						rankFriendListView = new RankFriendListView(this);
//						ArrayList<RankObject> o = new ArrayList<RankObject>();
//						o.add(new RankObject("1","1","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2","강신현","100","98","100","http://postfiles1.naver.net/20140701_112/loveteen2424_1404221461533XTtHc_JPEG/large_%2811%29.jpg?type=w2"));
//						
//						rankFriendListView.setData(o);
//						rank_list_view.addView(rankFriendListView);
//					}
					mBtnHistory.setBackgroundResource(R.drawable.bttn_history_back_0);
					mBtnBadge.setBackgroundResource(R.drawable.bttn_badge_back_1);
					mTxtHistory.setBackgroundResource(R.drawable.bttn_history_text_0);
					//mTxtBadge.setBackgroundResource(R.drawable.bttn_badge_back_1);
					check_btn = true;
				}
				break;
			}

		}
	
	}
	public void listSort()
	{
		for(int i = 0 ; i < listObject.size() - 1; i++)
		{
			for(int j = i+1 ; j < listObject.size() ; j++)
			{
				GregorianCalendar first ;
				String time[] = listObject.get(i).getTime().split(". ");
				int year = Integer.parseInt(time[0]);
				int month = (Integer.parseInt(time[1]));
				int date = Integer.parseInt(time[2]);
				first = new GregorianCalendar(year, month,date);
				GregorianCalendar  two;
				String time2[] = listObject.get(j).getTime().split(". ");
				int year2 = Integer.parseInt(time2[0]);
				int month2 = (Integer.parseInt(time2[1]));
				int date2 = Integer.parseInt(time2[2]);
				two = new GregorianCalendar(year2, month2,date2);
				if(first.before(two))
				{
					HistoryObject o = listObject.get(i);
					listObject.set(i, listObject.get(j));
					listObject.set(j, o);
				}
			}
		}
	}
	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_HISTORY"))
			{
				JSONArray history = object.getJSONArray("data");
				for(int i = 0 ; i < history.length() ; i++)
				{
					JSONObject jsonObject = history.getJSONObject(i);
					String time = jsonObject.optString("time","");
					String man1 = jsonObject.optString("man1","");
					String man2 = jsonObject.optString("man2","");
					String name1 = jsonObject.optString("name1",""); //숫자
					String name2 = jsonObject.optString("name2","");//숫자
					String nation1 = jsonObject.optString("nation1","");//숫자
					String nation2 = jsonObject.optString("nation2","");//숫자
					String winner = jsonObject.optString("winner","");//숫자
					String step1 = jsonObject.optString("step1","");//숫자
					String step2 = jsonObject.optString("step2","");//숫자
					listObject.add(new HistoryObject(time, man1, man2, name1, name2, step1, step2, nation1, nation2));
				}
				int win = LoginObject.getInstance().getVictory();
				int lose = listObject.size() - LoginObject.getInstance().getVictory();
				mTxtWin.setText(win);
				mTxtLose.setText(lose);
				if(meHisoryListView == null)
				{
					listSort();
					meHisoryListView = new MeHistoryListView(mContext);
					meHisoryListView.setData(listObject);
					mListView.addView(meHisoryListView);
				}
				else
				{
					meHisoryListView.notifyData();
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
