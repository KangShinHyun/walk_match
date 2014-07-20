package com.ssm.walk_match.list;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssm.walk_match.R;
import com.ssm.walk_match.list.FriendListView.FriendListViewAdapter;
import com.ssm.walk_match.main.MainActivity;
import com.ssm.walk_match.object.FriendObject;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.object.MessageObject;
import com.ssm.walk_match.util.AsyncImageLoader;
import com.ssm.walk_match.util.Util;

public class MessageContentListView extends ListView  {
	private Context mContext;
	private MessageContentListViewAdapter listAdapter;
	ViewHolder holder;
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private Handler m_oHandler ;
	private ArrayList<MessageObject> MessageList;
	private String meName;
	public MessageContentListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}

	public MessageContentListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initListView();
	}

	public void setHandler(Handler handler)
	{
		this.m_oHandler = handler;
	}
	public void setData(ArrayList<MessageObject> object)
	{
		MessageList = object;
		if(listAdapter != null)
			listAdapter.recycle();
		listAdapter = new MessageContentListViewAdapter(object);
		setAdapter(listAdapter);
		meName = LoginObject.getInstance().getName();
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

	class MessageContentListViewAdapter extends ArrayAdapter<MessageObject> {
		private RelativeLayout mFriLayout;
		private RelativeLayout mMeLayout;
		
		private ImageView mFriImg;
		private TextView mFriTxt;
		private TextView mFriTimeTxt;
		
		private TextView mMeTxt;
		private TextView mMeTimeTxt;
		
		
		private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();

		public void recycle() {
			for (WeakReference<View> ref : mRecycleList) {
				Util.recursiveRecycle(ref.get());
			}
		}

		public MessageContentListViewAdapter(List<MessageObject> objects) {
			super(mContext, 0, objects);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_message_me, null);
				mFriLayout = (RelativeLayout) convertView.findViewById(R.id.fri_layout);
				mMeLayout = (RelativeLayout) convertView.findViewById(R.id.me_layout);
				
				mFriImg = (ImageView) convertView.findViewById(R.id.friend_img);
				mFriTxt = (TextView) convertView.findViewById(R.id.fri_text);
				mFriTimeTxt = (TextView) convertView.findViewById(R.id.fri_text_time);
				
				mMeTxt = (TextView) convertView.findViewById(R.id.me_text);
				mMeTimeTxt = (TextView) convertView.findViewById(R.id.me_text_time);

				holder = new ViewHolder();
				holder.meLayout = mMeLayout;
				holder.friLayout = mFriLayout;
				holder.friImg = mFriImg;
				holder.friTxt = mFriTxt;
				holder.friTimeTxt = mFriTimeTxt;
				holder.meTxt = mMeTxt;
				holder.meTimeTxt = mMeTimeTxt;
				
				convertView.setTag(holder);

				mRecycleList.add(new WeakReference<View>(convertView));
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			convertView.setTag(R.id.imageId, position);
			holder.friLayout.setTag(R.id.imageId, position);
			holder.meLayout.setTag(R.id.imageId, position);
			MessageObject object = getItem(position);
			if(meName.equals(object.getToMan()))
			{
				//내아이디이름과 보내는사람이 같을때 
				holder.meLayout.setVisibility(View.VISIBLE);
				holder.friLayout.setVisibility(View.GONE);
				holder.meTimeTxt.setText(object.getTime());
				holder.meTxt.setText(object.getContent());
			}
			else
			{
				holder.meLayout.setVisibility(View.GONE);
				holder.friLayout.setVisibility(View.VISIBLE);
				m_oAsyncImageLoader.setImageDrawableAsync(holder.friImg,object.getImg(),getResources().getDrawable(R.drawable.message_picture), getResources().getDrawable(R.drawable.message_picture), mContext);
				holder.friTimeTxt.setText(object.getTime());
				holder.friTxt.setText(object.getContent());
			}
			
			

			return convertView;
		}
	}

	class ViewHolder {
		private RelativeLayout meLayout;
		private RelativeLayout friLayout;
		private ImageView friImg;
		private TextView friTxt;
		private TextView friTimeTxt;
		private TextView meTxt;
		private TextView meTimeTxt;
	}

	public void notifyData() {
		listAdapter.notifyDataSetChanged();
	}

}
