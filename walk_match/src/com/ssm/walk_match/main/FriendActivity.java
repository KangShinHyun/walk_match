package com.ssm.walk_match.main;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ssm.walk_match.R;
import com.ssm.walk_match.list.FriendListView;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.object.FriendObject;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.service.ServiceType;
import com.ssm.walk_match.util.AppManager;

public class FriendActivity extends BaseActivity implements HttpClientNet.OnResponseListener {


	public static String phone;
	public static ArrayList<FriendObject> friendList = new ArrayList<FriendObject>();
	private Context mContext;
	private ImageView btn_match;
	private ImageView btn_friend;
	private ImageView btn_me;
	private ImageView btn_ranking;
	private RelativeLayout btn_message; 
	private FriendListView friendListView;
	private LinearLayout middle_list;
	private RelativeLayout mBtn_find_friends;
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
        if(friendList.size() == 0)
        {
        	TelephonyManager systemService = (TelephonyManager)getSystemService    (Context.TELEPHONY_SERVICE);
    		String PhoneNumber = systemService.getLine1Number();
    		PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-10,PhoneNumber.length());
    		phone="0"+PhoneNumber;
    		requestFriend(getContactList());
        }
	}

    @Override
    protected void onResume() {
        super.onResume();

    }
    private ArrayList<String> getContactList() 
    {

		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		String[] projection = new String[] {
				ContactsContract.CommonDataKinds.Phone.NUMBER      }; // 연락처 번호

		String[] selectionArgs = null;

		String sortOrder = ContactsContract.CommonDataKinds.Phone.NUMBER
				+ " COLLATE LOCALIZED ASC";

		Cursor contactCursor = managedQuery(uri, projection, null,
				selectionArgs, sortOrder);

		ArrayList<String> contactlist = new ArrayList<String>();

		if (contactCursor.moveToFirst()) {
			do {
				String phonenumber = contactCursor.getString(0).replaceAll("-",
						"");
				if (phonenumber.length() == 10) {
					phonenumber = phonenumber.substring(0, 3) 
							+ phonenumber.substring(3, 6) 
							+ phonenumber.substring(6);
				} else if (phonenumber.length() > 8) {
					phonenumber = phonenumber.substring(0, 3) 
							+ phonenumber.substring(3, 7) 
							+ phonenumber.substring(7);
				}
				contactlist.add(phonenumber);
			} while (contactCursor.moveToNext());
		}

		return contactlist;
    }
	public void requestFriend(ArrayList<String> data) {
		Log.d("intput start frined","start");
		JSONArray arrayData = new JSONArray();
		TelephonyManager systemService = (TelephonyManager)getSystemService    (Context.TELEPHONY_SERVICE);
		String PhoneNumber = systemService.getLine1Number();
		PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-10,PhoneNumber.length());
		phone="0"+PhoneNumber;
		try
		{
	    	for(int  i = 0 ; i < data.size() ; i++)
	    	{
	    		
	    		if(phone.equals(data.get(i)))
	    		{
	    			
	    		}
	    		else
	    		{
		    		JSONObject o = new JSONObject();
		    		o.put("phone", data.get(i));
		    		arrayData.put(i,o );
	    		}
	    	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_FRIEND_SEARCH);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
	//	Log.d("d",arrayData.toString());
		loginParams.add(new Params("phone", arrayData.toString()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
		Log.d("intput end", "end");
	} 
    public void setUi()
    {
    	mBtn_find_friends = (RelativeLayout)findViewById(R.id.middle_layout_bottom);
    	mBtn_find_friends.setOnClickListener(this);
    	btn_message = (RelativeLayout)findViewById(R.id.btn_chat);
		btn_match = (ImageView)findViewById(R.id.btn_match);
		btn_friend = (ImageView)findViewById(R.id.btn_friend);
		btn_me = (ImageView)findViewById(R.id.btn_me);
		btn_ranking = (ImageView)findViewById(R.id.btn_ranking);
		middle_list = (LinearLayout)findViewById(R.id.middle_layout_middle2);
		
		
		if(friendList.size() != 0)
		{
			friendListView = new FriendListView(this);
			friendListView.setData(friendList);
			middle_list.addView(friendListView);
		}
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
			case R.id.middle_layout_bottom:
			{
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				String smsBody = "같이 걷자 친구야!!!!!";
				sendIntent.putExtra("sms_body", smsBody); // 보낼 문자
				sendIntent.putExtra("address", ""); // 받는사람 번호
				sendIntent.setType("vnd.android-dir/mms-sms");
				startActivity(sendIntent);
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
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_FRIEND_SEARCH"))
			{
				Log.d("input receive network","MSG_FRIEND_SEARCH");
				JSONArray history = object.getJSONArray("data");
				for(int i = 0 ; i < history.length() ; i++)
				{
					JSONObject jsonObject = history.getJSONObject(i);
					String email = jsonObject.optString("email","");
					String img = jsonObject.optString("img","");
					String name = jsonObject.optString("name","");
					int nation = jsonObject.optInt("nation",0); //숫자
					int victory = jsonObject.optInt("victory",0);//숫자
					int match = jsonObject.optInt("match",0);//숫자
					int friend = jsonObject.optInt("frined",0);//숫자
					int worldNum = jsonObject.optInt("worldNum",0);//숫자
					String vs = jsonObject.optString("vs","");//숫자
					int step = jsonObject.optInt("step",0);//숫자
					friendList.add(new FriendObject(email, img, name, friend, victory, worldNum, nation, match,vs,step));
				}
				if(friendList.size() != 0)
				{
					friendListView = new FriendListView(this);
					friendListView.setData(friendList);
					middle_list.addView(friendListView);
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
