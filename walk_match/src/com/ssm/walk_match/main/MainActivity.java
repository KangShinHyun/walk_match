package com.ssm.walk_match.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssm.walk_match.AlarmReceiver;
import com.ssm.walk_match.GCMIntentService;
import com.ssm.walk_match.R;
import com.ssm.walk_match.SAPMatchService;
import com.ssm.walk_match.SAPService;
import com.ssm.walk_match.arService;
import com.ssm.walk_match.component.GiveUpPopup;
import com.ssm.walk_match.component.LosePopup;
import com.ssm.walk_match.component.PushPopup;
import com.ssm.walk_match.component.StartPopup;
import com.ssm.walk_match.component.WinPopup;
import com.ssm.walk_match.component.rePushPopup;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.service.ServiceType;
import com.ssm.walk_match.util.AppManager;
import com.ssm.walk_match.util.AsyncImageLoader;

public class MainActivity extends BaseActivity implements HttpClientNet.OnResponseListener{

	
	private Context mContext;
	private ImageView btn_match;
	private ImageView btn_friend;
	private ImageView btn_me;
	private ImageView btn_ranking;
	private RelativeLayout btn_message; 
	
	private RelativeLayout layoutTopStep;
	private RelativeLayout layoutNoMatch;
	private RelativeLayout layoutMatchStart;
	
	private TextView mTxtMyStep;
	private TextView mTxtMyPoint;
	private TextView mTxtFriStep;
	private TextView mTxtFriPoint;
	
	private ImageView mImgMyImg;
	private TextView mTxtMyName;
	private RelativeLayout mBtnMyFriend;
	private RelativeLayout mBtnMyVictory;
	private RelativeLayout mBtnMyWorld;
	private TextView mTxtMyFirend;
	private TextView mTxtMyVictory;
	private TextView mTextMyWorld;
	private ImageView mImgMyNation;
	private TextView mTxtMyNation;
	
	private ImageView mImgFriImg;
	private TextView mTxtFriName;
	private RelativeLayout mBtnFriFriend;
	private RelativeLayout mBtnFriVictory;
	private RelativeLayout mBtnFriWorld;
	private TextView mTxtFriFirend;
	private TextView mTxtFriVictory;
	private TextView mTextFriWorld;
	private ImageView mImgFriNation;
	private TextView mTxtFriNation;
	
	private String[] nation_name = {"Australia","Austria","Belgium","Brazil","Canada","China","Czech Repub","Denmark"
			,"Finland","France","Germany","Greece","Hong Kong","Hungry","Iceland","India","Indonesia","Italy"
			,"Korea","Japan","Malaysia","Mexico","Netherland","New Zealand","Norway","Poland","Portugal","Russia"
			,"Saudi Arabia","Singapore","Spain","Sweden","Switzerland","Thailand","UAE","U.K","USA","Vietnam"};
	
	private int[] nation_img = {R.drawable.australia,R.drawable.austria,R.drawable.belgium,R.drawable.brazil
			,R.drawable.canada,R.drawable.china,R.drawable.czechrepublic,R.drawable.denmark
			,R.drawable.finland,R.drawable.france,R.drawable.germany,R.drawable.greece,R.drawable.hongkong
			,R.drawable.hungary,R.drawable.iceland,R.drawable.india,R.drawable.indonesia,R.drawable.italy
			,R.drawable.korea,R.drawable.japan,R.drawable.malaysia,R.drawable.mexico,R.drawable.netherland
			,R.drawable.newzeland,R.drawable.norway,R.drawable.poland,R.drawable.portugal,R.drawable.russia
			,R.drawable.saudiarabia,R.drawable.singapore,R.drawable.spain,R.drawable.sweden,R.drawable.switzerland
			,R.drawable.thailand,R.drawable.uae,R.drawable.unitedkingdom,R.drawable.unitedstatesofamerica,R.drawable.vietnam};
	
	private LinearLayout mBtnGiveUp;
	private LinearLayout mLayoutDday;
	private TextView mTxtDday;
	private LinearLayout mBtnPush;
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	public static String fri_email = "";
	public static String fri_name ="";
	public static int fri_nation = 0;
	public static String winner_step = "0";
	public static String lose_step = "0";
	private Handler handler_give_up;
	private Handler handler_win;
	private Handler handler_match;
	@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        mContext = this;
        SharedPreferences sp = getSharedPreferences("reg",MODE_PRIVATE);
		if (sp.getBoolean("reg", false))
		{
			
		}
		else
		{
			requestRegId();
		}
        setUi();
        
        AppManager.getInstance().setActivity(this);
	}
	public void setWalkUi()
	{
		 SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
		 int mewalk = sp2.getInt("mewalk", 0);
		 mTxtMyStep.setText(mewalk+"");
		 mTxtMyPoint.setText((mewalk*2)+"");
	}
    
    public void onResumePublic()
    {
    	onResume();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //놀리기
        Log.d("onResume", "onResume in");
        if(arService.checkAlarm == true)
        {
        	Log.d("onResume", "alarm in");
        	arService.checkAlarm = false;
        	requestMatchGcmEND(fri_email);
        	cancel();
        }
        else
        {
        	 SharedPreferences sp = getSharedPreferences("match_ing",MODE_PRIVATE);
	        if(GCMIntentService.checkStyleTesase == 1 || GCMIntentService.checkStyleTesase == 2 || GCMIntentService.checkStyleTesase == 3)
	        {
				rePushPopup o = new rePushPopup(this, GCMIntentService.tesaseEmail);
				GCMIntentService.checkStyleTesase = 0;
	        }
	        else
	        {
	        	GCMIntentService.checkStyleTesase = 0;
	        }
	        
	        //매치정해졌을때 
	        if(GCMIntentService.checkMatchReGCM == true && !GCMIntentService.matchUpEmail.equals(""))
	        {
	        	Log.d("onResume", "checkMatchReGCM in");
	        	StartPopup o = new StartPopup(mContext, "", handler_match);
	        	requestMatchUp(GCMIntentService.matchUpEmail);
	        	GCMIntentService.checkMatchReGCM = false;
	        }
	       
	        //알람이 끝났다 경기 끝!!
	       
	        else if(GCMIntentService.whoWin == 1)
	        {
	        	//내가짐
	        	Log.d("onResume", "whoWin in 1");
	        	GCMIntentService.whoWin = -1;
	        	LosePopup o = new LosePopup(mContext, fri_email);
	        	setEndGame();
	        }
	        else if(GCMIntentService.whoWin ==2)
	        {
	        	//내가이김
	        	Log.d("onResume", "whoWin in 2");
	        	GCMIntentService.whoWin = -1;
	        	WinPopup o = new WinPopup(mContext,"",handler_win);
	        	requestMatchGcmWin(fri_email);
	        }
	        else if(GCMIntentService.whoWin == 0)
	        {
	        	//내가이김
	        	Log.d("onResume", "whoWin in 00");
	        	GCMIntentService.whoWin = -1;
	        	setEndGame();
	        }
	        //상대가 포기했을때
	        else if(GCMIntentService.receiveGiveUp == true) //상대가 포기했다면!!!
	        {
	        	Log.d("onResume", "receiveGiveUp in");
	        	GCMIntentService.receiveGiveUp = false;
	        	WinPopup o = new WinPopup(mContext,"",handler_win);
	        	requestMatchGcmWin(fri_email);
	        	cancel();
	        }
	        else if(SAPService.SAPWalkAction == true)
			{
				SAPService.SAPWalkAction = false;
				Log.d("onResume", "SAPWalkAction in ");
				setWalkUi();
				
			}

	        else if(SAPMatchService.SAPMatchAction == true)
	        {
	        	SAPMatchService.SAPMatchAction = false;
				Log.d("onResume", "SAPMatchAction in ");
				requestMatch();
	        }
	        else if (sp.getBoolean("match_ing", false))
			{
				if (!sp.getString("email", "").equals("")) //상대가 있을때 리쥼타면 무조건 웨이크업
				{
					Log.d("onResume", "requestWalkUp in ");
					requestWalkUp();
				}
			}
	        
	        //사프 액션이올때
	        
        }
	    
        
    }
    public void requestRegId()
    {
    	HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GCM_ID_JOIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("email",LoginObject.getInstance().getEmail()));
		loginParams.add(new Params("regId",LodaingFirstActivity.regId));
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
		
		layoutTopStep = (RelativeLayout)findViewById(R.id.layout_top_count);
		layoutNoMatch = (RelativeLayout)findViewById(R.id.main_middle_right_no);
		layoutNoMatch.setOnClickListener(this);
		
		layoutMatchStart = (RelativeLayout)findViewById(R.id.main_middle_right);
		
		
		mTxtMyStep = (TextView)findViewById(R.id.txt_me_step);
		mTxtMyPoint = (TextView)findViewById(R.id.txt_me_point);
		mTxtFriStep = (TextView)findViewById(R.id.txt_cp_step);
		mTxtFriPoint = (TextView)findViewById(R.id.txt_cp_point);
		
		mImgMyImg = (ImageView)findViewById(R.id.me_picture_img);
		m_oAsyncImageLoader.setImageDrawableAsync(mImgMyImg,LoginObject.getInstance().getImg(),getResources().getDrawable(R.drawable.me_no_picture), getResources().getDrawable(R.drawable.me_no_picture), mContext);
		mTxtMyName = (TextView)findViewById(R.id.me_name_txt);
		mTxtMyName.setText(LoginObject.getInstance().getName());
		mBtnMyFriend = (RelativeLayout)findViewById(R.id.me_friend_layout);
		mBtnMyVictory = (RelativeLayout)findViewById(R.id.me_victory_layout);
		mBtnMyWorld = (RelativeLayout)findViewById(R.id.me_world_layout);
		mTxtMyFirend = (TextView)findViewById(R.id.me_friend_txt);
		mTxtMyVictory = (TextView)findViewById(R.id.me_victory_txt);
		mTextMyWorld = (TextView)findViewById(R.id.me_world_txt);
		mTxtMyFirend.setText(LoginObject.getInstance().getFriend()+"");
		mTxtMyVictory.setText(LoginObject.getInstance().getVictory()+"");
		mTextMyWorld.setText(LoginObject.getInstance().getWorldNum()+"");
		mImgMyNation = (ImageView)findViewById(R.id.nation_img);
		mTxtMyNation = (TextView)findViewById(R.id.nation_txt);
		mImgMyNation.setBackgroundResource(nation_img[LoginObject.getInstance().getNation()]);
		mTxtMyNation.setText(nation_name[LoginObject.getInstance().getNation()]);
		
		mImgFriImg = (ImageView)findViewById(R.id.right_picture_img);
		mTxtFriName = (TextView)findViewById(R.id.right_name_txt);
		mBtnFriFriend = (RelativeLayout)findViewById(R.id.right_friend_layout);
		mBtnFriVictory = (RelativeLayout)findViewById(R.id.right_victory_layout);
		mBtnFriWorld = (RelativeLayout)findViewById(R.id.right_world_layout);
		mTxtFriFirend = (TextView)findViewById(R.id.right_friend_txt);
		mTxtFriVictory = (TextView)findViewById(R.id.right_victory_txt);
		mTextFriWorld = (TextView)findViewById(R.id.right_world_txt);
		mImgFriNation = (ImageView)findViewById(R.id.right_nation_img);
		mTxtFriNation = (TextView)findViewById(R.id.right_nation_txt);
		
		mLayoutDday = (LinearLayout)findViewById(R.id.layout_dday);
		mTxtDday = (TextView)findViewById(R.id.d_day_txt);
		mBtnGiveUp = (LinearLayout)findViewById(R.id.give_up_btn);
		mBtnGiveUp.setOnClickListener(this);
		mBtnPush = (LinearLayout)findViewById(R.id.push_your);
		mBtnPush.setOnClickListener(this);
		SharedPreferences sp = getSharedPreferences("match_ing",MODE_PRIVATE);
		if (sp.getBoolean("match_ing", false))
		{
			if (sp.getString("email", "").equals("")) 
			{
				//없을 경우 그냥 진행
				layoutNoMatch.setVisibility(View.VISIBLE);
				layoutMatchStart.setVisibility(View.GONE);
			} 
			else 
			{
				fri_email = sp.getString("email", "");
				layoutNoMatch.setVisibility(View.GONE);
				layoutMatchStart.setVisibility(View.VISIBLE);
				//매치중일경우
				String date = sp.getString("calendar", "");
				dayCheck(date);
				requestMatchUp(fri_email);
			}
		} 
		else 
		{
			layoutNoMatch.setVisibility(View.VISIBLE);
			layoutMatchStart.setVisibility(View.GONE);
		}
		handler_give_up = new Handler() {
			  public void handleMessage(android.os.Message msg) {
				 int data = msg.arg1;
				 if(data == 100)
				 {
					 LosePopup o = new LosePopup(mContext, "");
					 setEndGame();
				 }
			  };
		 };
		 handler_win = new Handler() {
			  public void handleMessage(android.os.Message msg) {
				  
			  };
		 };
		 handler_match = new Handler() {
			  public void handleMessage(android.os.Message msg) {
		        	
			  };
		 };
		
    }
    public void dayCheck(String date)
    {
    	String [] sp_date = date.split("/"); //기준일
    	GregorianCalendar today = new GregorianCalendar(); //오늘
    	int calendar = GetDifferenceOfDate(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE),Integer.parseInt(sp_date[0]),
    			Integer.parseInt(sp_date[1]),Integer.parseInt(sp_date[2]));
    	mTxtDday.setText((calendar+7)+"-Days");
    	
    }

	public void enroll()
	{
		//디비에 언제 몇시에 일어날건지 다시 저장해서 부팅되면 다시 이메소드로 등록.
		AlarmManager alm = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		Intent intent;
		PendingIntent sender;
		intent = new Intent(this,AlarmReceiver.class);
		sender = PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		GregorianCalendar at = new GregorianCalendar();
//		at.set(Calendar.DAY_OF_MONTH, at.get(Calendar.DAY_OF_MONTH)+7);
//		at.set(Calendar.HOUR, 9);
//		at.set(Calendar.MINUTE, 0);
		at.set(Calendar.MINUTE, at.get(Calendar.MINUTE)+1);
		alm.set(AlarmManager.RTC_WAKEUP, at.getTimeInMillis(), sender);
		SharedPreferences sp = getSharedPreferences("match_ing",MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		editer.putInt("year", at.get(Calendar.YEAR));
		editer.putInt("month", at.get(Calendar.MONTH));
		editer.putInt("day", at.get(Calendar.DAY_OF_MONTH));
		editer.commit();
	}
	public void cancel()// 알람이 다되거나 기브업했을경우
	{
		SharedPreferences sp = getSharedPreferences("match_ing",MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		editer.putInt("year", 0);
		editer.putInt("month",0);
		editer.putInt("day", 0);
		editer.commit();
		AlarmManager alm = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this,AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(this, 0 , intent, 0);
		alm.cancel(sender);
	}

    public static int GetDifferenceOfDate ( int nYear1, int nMonth1, int nDate1, int nYear2, int nMonth2, int nDate2 )
    {
        Calendar cal = Calendar.getInstance ( );
        int nTotalDate1 = 0, nTotalDate2 = 0, nDiffOfYear = 0, nDiffOfDay = 0;
        if ( nYear1 > nYear2 )
        {
            for ( int i = nYear2; i < nYear1; i++ ) 
            {
                cal.set ( i, 12, 0 );
                nDiffOfYear += cal.get ( Calendar.DAY_OF_YEAR );
            }
            nTotalDate1 += nDiffOfYear;
        }
        else if ( nYear1 < nYear2 )
        {
            for ( int i = nYear1; i < nYear2; i++ )
            {
                cal.set ( i, 12, 0 );
                nDiffOfYear += cal.get ( Calendar.DAY_OF_YEAR );
            }
            nTotalDate2 += nDiffOfYear;
        }
         
        cal.set ( nYear1, nMonth1-1, nDate1 );
        nDiffOfDay = cal.get ( Calendar.DAY_OF_YEAR );
        nTotalDate1 += nDiffOfDay;
        cal.set ( nYear2, nMonth2-1, nDate2 );
        nDiffOfDay = cal.get ( Calendar.DAY_OF_YEAR );
        nTotalDate2 += nDiffOfDay;
         
        return nTotalDate1-nTotalDate2;
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
    public void requestMatchStep(String email)
  	{
  		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_MATCH_STEP);
  		ArrayList<Params> loginParams = new ArrayList<Params>();
  		loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
  		loginParams.add(new Params("fri_email", email));
  		
  		loginService.setParam(loginParams);
  		loginService.doAsyncExecute(this);
  		startProgressDialog();
  	}
    public void requestMatchUp(String email)
  	{
  		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_MATCH_UP);
  		ArrayList<Params> loginParams = new ArrayList<Params>();
  		loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
  		loginParams.add(new Params("fri_email", email));
  		
  		loginService.setParam(loginParams);
  		loginService.doAsyncExecute(this);
  		startProgressDialog();
  	}
    public void requestMatch()
  	{
  		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_MATCH);
  		ArrayList<Params> loginParams = new ArrayList<Params>();
  		loginParams.add(new Params("email", LoginObject.getInstance().getEmail()));
  		
  		loginService.setParam(loginParams);
  		loginService.doAsyncExecute(this);
  		startProgressDialog();
  	}
    public void requestMatchGcm(String email)
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GCM_GO_MATCH);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
		loginParams.add(new Params("fri_email", email));
		Log.d("my",LoginObject.getInstance().getEmail());
		Log.d("fri",email);
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestMatchGcmWin(String email)
   	{
   		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GCM_GO_MATCH_WIN);
   		ArrayList<Params> loginParams = new ArrayList<Params>();
   		GregorianCalendar o = new GregorianCalendar();
   		String tiem = o.get(Calendar.YEAR)+". "+(o.get(Calendar.MONTH)+1)+". "+o.get(Calendar.DATE)+"";
   		loginParams.add(new Params("time", tiem));
   		loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
   		loginParams.add(new Params("fri_email", email));
   		loginParams.add(new Params("my_name", LoginObject.getInstance().getName()));
   		loginParams.add(new Params("fri_name", fri_name));
   		loginParams.add(new Params("my_nation", LoginObject.getInstance().getNation()+""));
   		loginParams.add(new Params("fri_nation", fri_nation+""));
   		loginParams.add(new Params("victory", (LoginObject.getInstance().getVictory()+1)+""));
  		loginParams.add(new Params("my_match", (LoginObject.getInstance().getMatch()+1)+""));
   		loginParams.add(new Params("winner_step", winner_step));
   		loginParams.add(new Params("lose_step", lose_step));
   		winner_step = "0";
   		lose_step = "0";
   		loginService.setParam(loginParams);
   		loginService.doAsyncExecute(this);
   		startProgressDialog();
   	}
    public void requestMatchGcmEND(String email)
   	{
   		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_GCM_GO_MATCH_END);
   		ArrayList<Params> loginParams = new ArrayList<Params>();
   		loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
   		loginParams.add(new Params("fri_email", email));
   		Log.d("my",LoginObject.getInstance().getEmail());
   		Log.d("fri",email);
   		loginService.setParam(loginParams);
   		loginService.doAsyncExecute(this);
   		startProgressDialog();
   	}
    public void requestWalkUp()
   	{
   		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_WALK_UP);
   		ArrayList<Params> loginParams = new ArrayList<Params>();
   		SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
		int mewalk = sp2.getInt("mewalk", 0);
   		loginParams.add(new Params("my_email", LoginObject.getInstance().getEmail()));
   		loginParams.add(new Params("fri_email", fri_email));
   		loginParams.add(new Params("step", mewalk+""));
   		loginService.setParam(loginParams);
   		loginService.doAsyncExecute(this);
   		startProgressDialog();
   	}
	@Override
	public void onClick(View v) 
	{
		super.onClick(v);
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			
			case R.id.main_middle_right_no :
			{
				requestMatch();
				break;
			}
			case R.id.push_your :
			{
				PushPopup o = new PushPopup(mContext,fri_email);
				break;
			}
			case R.id.give_up_btn :
			{
				GiveUpPopup o = new GiveUpPopup(mContext, fri_email,handler_give_up);
				break;
			}
		}
	
	}
	public void setWinGame()
	{
		layoutTopStep.setVisibility(View.GONE);
		layoutNoMatch.setVisibility(View.VISIBLE);
		layoutMatchStart.setVisibility(View.GONE);
		mLayoutDday.setVisibility(View.GONE);
		mBtnGiveUp.setVisibility(View.GONE);
		mTxtMyVictory.setText((LoginObject.getInstance().getVictory()+1)+"");
		LoginObject.getInstance().setMatch((LoginObject.getInstance().getMatch()+1));
		LoginObject.getInstance().setVictory((LoginObject.getInstance().getVictory()+1));
		SharedPreferences sp = getSharedPreferences("match_ing",MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		editer.putBoolean("match_ing", false);
		//GregorianCalendar calendar = new GregorianCalendar();
		//String calendarData = calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DATE);
		editer.putString("email", "");
		editer.putString("calendar", "");
		editer.commit();
		SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
		SharedPreferences.Editor editer2 = sp2.edit();
	    editer2.putInt("mewalk",0);
	    editer2.putInt("youwalk",0);
	    editer2.putString("youname", "");
	    editer2.putString("youemail", "");
	    mTxtMyStep.setText("0");
	    mTxtMyPoint.setText("0");
	    mTxtFriStep.setText("0");
	    mTxtFriPoint.setText("0");
	    editer2.commit();
		fri_email = "";
		fri_name ="";
		fri_nation = 0;
		cancel();
	}
	public void setEndGame()
	{
		layoutTopStep.setVisibility(View.GONE);
		layoutNoMatch.setVisibility(View.VISIBLE);
		layoutMatchStart.setVisibility(View.GONE);
		mLayoutDday.setVisibility(View.GONE);
		mBtnGiveUp.setVisibility(View.GONE);
		LoginObject.getInstance().setMatch((LoginObject.getInstance().getMatch()+1));
		SharedPreferences sp = getSharedPreferences("match_ing",MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		editer.putBoolean("match_ing", false);
		//GregorianCalendar calendar = new GregorianCalendar();
		//String calendarData = calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DATE);
		editer.putString("email", "");
		editer.putString("calendar", "");
		editer.commit();
		fri_email = "";
		fri_name ="";
		fri_nation = 0;
		SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
	    SharedPreferences.Editor editer2 = sp2.edit();
	    editer2.putInt("mewalk",0);
	    editer2.putInt("youwalk",0);
	    editer2.putString("youname", "");
	    editer2.putString("youemail", "");
	    editer2.commit();
	    mTxtMyStep.setText("0");
	    mTxtMyPoint.setText("0");
	    mTxtFriStep.setText("0");
	    mTxtFriPoint.setText("0");
	    cancel();
	}
	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		boolean check = false;
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_GCM_ID_JOIN"))
			{
				check = true;
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					SharedPreferences sp = getSharedPreferences("reg", MODE_PRIVATE);
					SharedPreferences.Editor editer = sp.edit();
					
					editer.putBoolean("reg", true);
					//GregorianCalendar calendar = new GregorianCalendar();
					//String calendarData = calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DATE);
					editer.commit();
				}
				else
				{
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
			}
			
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_MATCH"))
			{
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					String email = object.getJSONObject("data").optString("email","");
					if(email.equals(""))
					{
						Toast.makeText(this, "Wait your partner", Toast.LENGTH_LONG).show();
						check = true;
					}
					else
					{
						requestMatchUp(email);
						enroll(); // 매치시작알람시작
					}
				}
				else
				{
					
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_MATCH_UP"))
			{
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					String email = object.getJSONObject("data").optString("email","");
					String img = object.getJSONObject("data").optString("img","");
					String name = object.getJSONObject("data").optString("name","");
					String nation = object.getJSONObject("data").optString("nation",""); //숫자
					String friend = object.getJSONObject("data").optString("friend","");//숫자
					String victory = object.getJSONObject("data").optString("victory","");//숫자
					String worldNum = object.getJSONObject("data").optString("worldNum","");//숫자
					
					mLayoutDday.setVisibility(View.VISIBLE);
					mBtnGiveUp.setVisibility(View.VISIBLE);
					layoutTopStep.setVisibility(View.VISIBLE);
					layoutNoMatch.setVisibility(View.GONE);
					layoutMatchStart.setVisibility(View.VISIBLE);
					m_oAsyncImageLoader.setImageDrawableAsync(mImgFriImg,img,getResources().getDrawable(R.drawable.me_no_picture), getResources().getDrawable(R.drawable.me_no_picture), mContext);
					
					mTxtFriName.setText(name);
					mTxtFriFirend.setText(friend);
					mTxtFriVictory.setText(victory);
					mTextFriWorld.setText(worldNum);
					mImgFriNation.setBackgroundResource(nation_img[Integer.parseInt(nation)]);
					mTxtFriNation.setText(nation_name[Integer.parseInt(nation)]);
					
					fri_email = email;
					fri_name = name;
					fri_nation = Integer.parseInt(nation);
					requestMatchStep(email);
				}
				
				else
				{
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_MATCH_STEP"))
			{
				check = false;
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					String my_step = object.getJSONObject("data").optString("my_step","");
					String my_point = object.getJSONObject("data").optString("my_point","");
					String fri_step = object.getJSONObject("data").optString("fri_step","");
					String fri_point = object.getJSONObject("data").optString("fri_point",""); 
					
					check = true;
					
					if(GCMIntentService.checkMatchReGCM == false)
					{
						SharedPreferences sp = getSharedPreferences("match_ing",MODE_PRIVATE);
						if (sp.getBoolean("match_ing", false))
						{
							//매치중일때
						}
						else
						{
							//매치가 처음잡힐때
							
							GregorianCalendar calendar = new GregorianCalendar();
							String calendarData = calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DATE);
							dayCheck(calendarData);
							
							
							SharedPreferences.Editor editer = sp.edit();
							
							editer.putBoolean("match_ing", true);
							editer.putString("email", fri_email);
							editer.putString("calendar", calendarData);
							editer.commit();
							requestMatchGcm(fri_email);
							
							SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
							   SharedPreferences.Editor editer2 = sp2.edit();
							   editer2.putString("youname",fri_name);
							   editer2.putString("youemail",fri_email);
							   editer2.putInt("youwalk",0);
							   editer2.commit();
							
						}
						
				   }
				   else
				   {
						GCMIntentService.checkMatchReGCM = false;
				   }
			       int my_sp = 0;
	               int fri_sp = 0;
				   if(my_step == null || my_step.equals("") || my_step.equals("null") )
				   {
						Log.d("mainWalkUp","my_step_error");
				   }
				   else
				   {
						my_sp = Integer.parseInt(my_step);
				   }
				   if(fri_step == null || fri_step.equals("") || fri_step.equals("null"))
				   {
					   Log.d("mainWalkUp","fri_step_error");
				   }
				   else
				   {
						fri_sp = Integer.parseInt(fri_step);
				   }
				   mTxtFriStep.setText(fri_sp+"");
	               mTxtFriPoint.setText((fri_sp*2)+"");
				   SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
				   int mewalk = sp2.getInt("mewalk", 0);
				   SharedPreferences.Editor editer2 = sp2.edit();
				   editer2.putInt("youwalk",fri_sp);
				   editer2.commit();
				   mTxtMyStep.setText(mewalk+"");
				   mTxtMyPoint.setText((mewalk*2)+"");
				}
				
				else
				{
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_GCM_GO_MATCH"))
			{
				check = true;
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_GCM_GO_MATCH_WIN"))
			{
				check = true;
				setWinGame();
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_GEAR_STEP"))
	         {
	            check = false;
	            String result = object.getJSONObject("data").optString("result","");
	            String reason = object.getJSONObject("data").optString("reason","");
	            if(result.equals("true"))
	            {
	               String my_step = object.getJSONObject("data").optString("my_step","");
	               String my_point = object.getJSONObject("data").optString("my_point","");
	               String fri_step = object.getJSONObject("data").optString("fri_step","");
	               String fri_point = object.getJSONObject("data").optString("fri_point",""); 
	               
	               check = true;
	               int my_sp = 0;
	               int fri_sp = 0;
				   if(my_step == null || my_step.equals("") || my_step.equals("null") )
				   {
						Log.d("mainWalkUp","my_step_error");
				   }
				   else
				   {
						my_sp = Integer.parseInt(my_step);
				   }
				   if(fri_step == null || fri_step.equals("") || fri_step.equals("null"))
				   {
					   Log.d("mainWalkUp","fri_step_error");
				   }
				   else
				   {
						fri_sp = Integer.parseInt(fri_step);
				   }
				   mTxtFriStep.setText(fri_sp+"");
	               mTxtFriPoint.setText((fri_sp*2)+"");
				   SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
				   int mewalk = sp2.getInt("mewalk", 0);
				   SharedPreferences.Editor editer2 = sp2.edit();
				   editer2.putInt("youwalk",fri_sp);
				   editer2.commit();
				   mTxtMyStep.setText(mewalk+"");
				   mTxtMyPoint.setText((mewalk*2)+"");
	            }
	            
	            else
	            {
	               Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
	            }
	         }
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_WALK_UP"))
	         {
				check = false;
	            String result = object.getJSONObject("data").optString("result","");
	            String reason = object.getJSONObject("data").optString("reason","");
	            if(result.equals("true"))
	            {
	               String my_step = object.getJSONObject("data").optString("my_step","");
	               String my_point = object.getJSONObject("data").optString("my_point","");
	               String fri_step = object.getJSONObject("data").optString("fri_step","");
	               String fri_point = object.getJSONObject("data").optString("fri_point",""); 
	               
	               check = true;
	               int my_sp = 0;
	               int fri_sp = 0;
				   if(my_step == null || my_step.equals("") || my_step.equals("null") )
				   {
						Log.d("mainWalkUp","my_step_error");
				   }
				   else
				   {
						my_sp = Integer.parseInt(my_step);
				   }
				   if(fri_step == null || fri_step.equals("") || fri_step.equals("null"))
				   {
					   Log.d("mainWalkUp","fri_step_error");
				   }
				   else
				   {
						fri_sp = Integer.parseInt(fri_step);
				   }
				   mTxtFriStep.setText(fri_sp+"");
	               mTxtFriPoint.setText((fri_sp*2)+"");
				   SharedPreferences sp2 = getSharedPreferences("gearinfo",MODE_PRIVATE);
				   int mewalk = sp2.getInt("mewalk", 0);
				   SharedPreferences.Editor editer2 = sp2.edit();
				   editer2.putInt("youwalk",fri_sp);
				   editer2.commit();
				   mTxtMyStep.setText(mewalk+"");
				   mTxtMyPoint.setText((mewalk*2)+"");
	            }
	            
	            else
	            {
	               Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
	            }
	         }
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_GCM_GO_MATCH_END"))
	        {
				check = true;
				Log.d("mainWalkUp","fri_step_error");
	        }
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if(check == true)
			stopProgressDialog() ;
		}
	}

}
