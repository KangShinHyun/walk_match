package com.ssm.walk_match.list;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Contacts.Intents;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ssm.walk_match.R;
import com.ssm.walk_match.main.BaseActivity;
import com.ssm.walk_match.main.MainActivity;
import com.ssm.walk_match.main.MessageActivity;
import com.ssm.walk_match.object.FriendObject;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.util.AsyncImageLoader;
import com.ssm.walk_match.util.Util;

public class FriendListView extends ListView  {
	private Context mContext;
	private FriendListViewAdapter listAdapter;
	ViewHolder holder;
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private Handler m_oHandler ;
	private ArrayList<FriendObject> friendList;
	public FriendListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}

	public FriendListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initListView();
	}

	public void setHandler(Handler handler)
	{
		this.m_oHandler = handler;
	}
	public void setData(ArrayList<FriendObject> object)
	{
		friendList = object;
		listAdapter = new FriendListViewAdapter(object);
		setAdapter(listAdapter);
	}
	public void initListView() {
		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setFadingEdgeLength(0);
		setDividerHeight(0);
		setSelector(new PaintDrawable(0x00000000));
		setCacheColorHint(0);
	}
	
	public void recycle() {
		if (listAdapter != null)
			listAdapter.recycle();
	}

	class FriendListViewAdapter extends ArrayAdapter<FriendObject> {
		private LinearLayout mLayout1;
		private ImageView mImg1;
		private TextView mTxt1;
		
		private LinearLayout mLayout2;
		private ImageView mImg2;
		private TextView mTxt2;
		
		private LinearLayout mLayout3;
		private ImageView mImg3;
		private TextView mTxt3;
		
		private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();

		public void recycle() {
			for (WeakReference<View> ref : mRecycleList) {
				Util.recursiveRecycle(ref.get());
			}
		}

		public FriendListViewAdapter(List<FriendObject> objects) {
			super(mContext, 0, objects);
		}
		@Override
		public int getCount()
		{
			return friendList.size() / 3 +1;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_friend, null);
				mLayout1 = (LinearLayout) convertView.findViewById(R.id.friend_first);
				mLayout2 = (LinearLayout) convertView.findViewById(R.id.friend_two);
				mLayout3 = (LinearLayout) convertView.findViewById(R.id.friend_three);
				mImg1 = (ImageView) convertView.findViewById(R.id.friend_first_img);
				mImg2 = (ImageView) convertView.findViewById(R.id.friend_two_img);
				mImg3 = (ImageView) convertView.findViewById(R.id.friend_three_img);
				mTxt1 = (TextView) convertView.findViewById(R.id.friend_first_name_txt);
				mTxt2 = (TextView) convertView.findViewById(R.id.friend_two_name_txt);
				mTxt3 = (TextView) convertView.findViewById(R.id.friend_three_name_txt);
				
				holder = new ViewHolder();
				holder.layout1 = mLayout1;
				holder.layout2 = mLayout2;
				holder.layout3 = mLayout3;
				holder.img1 = mImg1;
				holder.img2 = mImg2;
				holder.img3 = mImg3;
				holder.txt1 = mTxt1;
				holder.txt2 = mTxt2;
				holder.txt3 = mTxt3;
				
				convertView.setTag(holder);

				mRecycleList.add(new WeakReference<View>(convertView));
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			convertView.setTag(R.id.imageId, position);
			holder.layout1.setTag(R.id.imageId, position*3);
			holder.layout2.setTag(R.id.imageId, position*3+1);
			holder.layout3.setTag(R.id.imageId, position*3+2);
			holder.layout1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(BaseActivity.activity_num == BaseActivity.MESSAGE_ACTIVITY)
					{
						((MessageActivity)mContext).notifyData(friendList.get((Integer)v.getTag(R.id.imageId)).getName());
					}
					else
					{
						Intent intent =  new Intent(mContext, MessageActivity.class);
						intent.putExtra("toMan", friendList.get((Integer)v.getTag(R.id.imageId)).getName());
						mContext.startActivity(intent);
						((Activity)mContext).overridePendingTransition(0,0);
						((Activity)mContext).finish();
						BaseActivity.activity_num = BaseActivity.MESSAGE_ACTIVITY;
					}
				}
			});
			holder.layout2.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if(BaseActivity.activity_num == BaseActivity.MESSAGE_ACTIVITY)
								{
									((MessageActivity)mContext).notifyData(friendList.get((Integer)v.getTag(R.id.imageId)).getName());
								}
								else
								{
									Intent intent =  new Intent(mContext, MessageActivity.class);
									intent.putExtra("toMan", friendList.get((Integer)v.getTag(R.id.imageId)).getName());
									mContext.startActivity(intent);
									((Activity)mContext).overridePendingTransition(0,0);
									((Activity)mContext).finish();
									BaseActivity.activity_num = BaseActivity.MESSAGE_ACTIVITY;
								}
							}
						});
			holder.layout3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(BaseActivity.activity_num == BaseActivity.MESSAGE_ACTIVITY)
					{
						((MessageActivity)mContext).notifyData(friendList.get((Integer)v.getTag(R.id.imageId)).getName());
					}
					else
					{
						Intent intent =  new Intent(mContext, MessageActivity.class);
						intent.putExtra("toMan", friendList.get((Integer)v.getTag(R.id.imageId)).getName());
						mContext.startActivity(intent);
						((Activity)mContext).overridePendingTransition(0,0);
						((Activity)mContext).finish();
						BaseActivity.activity_num = BaseActivity.MESSAGE_ACTIVITY;
					}
				}
			});
			if(position == friendList.size()/3)
			{
				if(friendList.size()%3 == 1)
				{
					holder.layout1.setVisibility(View.VISIBLE);
					m_oAsyncImageLoader.setImageDrawableAsync(holder.img1, friendList.get(position*3).getImg_url(),getResources().getDrawable(R.drawable.frd_pictue), getResources().getDrawable(R.drawable.frd_pictue), mContext);
					holder.txt1.setText(friendList.get(position*3).getName());
					holder.layout2.setVisibility(View.GONE);
					holder.layout3.setVisibility(View.GONE);
				}
				
				else if(friendList.size()%3 == 2)
				{
					holder.layout1.setVisibility(View.VISIBLE);
					holder.layout2.setVisibility(View.VISIBLE);
					m_oAsyncImageLoader.setImageDrawableAsync(holder.img1, friendList.get(position*3).getImg_url(),getResources().getDrawable(R.drawable.frd_pictue), getResources().getDrawable(R.drawable.frd_pictue), mContext);
					holder.txt1.setText(friendList.get(position*3).getName());
					m_oAsyncImageLoader.setImageDrawableAsync(holder.img2, friendList.get(position*3+1).getImg_url(),getResources().getDrawable(R.drawable.frd_pictue), getResources().getDrawable(R.drawable.frd_pictue), mContext);
					holder.txt2.setText(friendList.get(position*3+1).getName());
					holder.layout3.setVisibility(View.GONE);
				}
				return convertView;
			}
			holder.layout1.setVisibility(View.VISIBLE);
			holder.layout2.setVisibility(View.VISIBLE);
			holder.layout3.setVisibility(View.VISIBLE);
			m_oAsyncImageLoader.setImageDrawableAsync(holder.img1, friendList.get(position*3).getImg_url(),getResources().getDrawable(R.drawable.frd_pictue), getResources().getDrawable(R.drawable.frd_pictue), mContext);
			m_oAsyncImageLoader.setImageDrawableAsync(holder.img2, friendList.get(position*3+1).getImg_url(),getResources().getDrawable(R.drawable.frd_pictue), getResources().getDrawable(R.drawable.frd_pictue), mContext);
			m_oAsyncImageLoader.setImageDrawableAsync(holder.img3, friendList.get(position*3+2).getImg_url(),getResources().getDrawable(R.drawable.frd_pictue), getResources().getDrawable(R.drawable.frd_pictue), mContext);
			holder.txt1.setText(friendList.get(position*3).getName());
			holder.txt2.setText(friendList.get(position*3+1).getName());
			holder.txt3.setText(friendList.get(position*3+2).getName());
			FriendObject itemObject = getItem(position);
			
			
			

			return convertView;
		}
	}

	class ViewHolder {
		private LinearLayout layout1;
		private LinearLayout layout2;
		private LinearLayout layout3;
		private TextView txt1;
		private TextView txt2;
		private TextView txt3;
		private ImageView img1;
		private ImageView img2;
		private ImageView img3;
	}

	public void notifyData() {
		listAdapter.notifyDataSetChanged();
	}

}
