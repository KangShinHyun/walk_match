package com.ssm.walk_match.list;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Handler;
import android.util.AttributeSet;
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
import com.ssm.walk_match.component.MessagePopup;
import com.ssm.walk_match.main.BaseActivity;
import com.ssm.walk_match.main.MessageActivity;
import com.ssm.walk_match.object.MessageObject;
import com.ssm.walk_match.util.AsyncImageLoader;
import com.ssm.walk_match.util.Util;

public class MessageListView extends ListView  {
	private Context mContext;
	private MessageListViewAdapter listAdapter;
	ViewHolder holder;
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private Handler m_oHandler ;
	private ArrayList<MessageObject> MessageList;
	public static String manName;
	private MessagePopup popup;
	public MessageListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}

	public MessageListView(Context context, AttributeSet attrs) {
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
		listAdapter = new MessageListViewAdapter(object);
		setAdapter(listAdapter);
	}
	public void setData(ArrayList<MessageObject> object,MessagePopup popup)
	{
		this.popup = popup;
		MessageList = object;
		listAdapter = new MessageListViewAdapter(object);
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

	class MessageListViewAdapter extends ArrayAdapter<MessageObject> {
		private RelativeLayout mLayout;
		private ImageView mImg;
		private TextView mTitleTxt;
		private TextView mTimeTxt;
		private TextView mContentTxt;
		
		private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();

		public void recycle() {
			for (WeakReference<View> ref : mRecycleList) {
				Util.recursiveRecycle(ref.get());
			}
		}

		public MessageListViewAdapter(List<MessageObject> objects) {
			super(mContext, 0, objects);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_message, null);
				mLayout = (RelativeLayout) convertView.findViewById(R.id.message_layout);
				mImg = (ImageView) convertView.findViewById(R.id.message_img);
				mTitleTxt = (TextView) convertView.findViewById(R.id.message_name);
				mTimeTxt = (TextView) convertView.findViewById(R.id.message_time);
				mContentTxt = (TextView) convertView.findViewById(R.id.message_content);

				holder = new ViewHolder();
				holder.layout = mLayout;
				holder.img = mImg;
				holder.title_txt = mTitleTxt;
				holder.time_txt = mTimeTxt;
				holder.content_txt = mContentTxt;
				
				convertView.setTag(holder);

				mRecycleList.add(new WeakReference<View>(convertView));
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			convertView.setTag(R.id.imageId, position);
			holder.layout.setTag(R.id.imageId, position);
			MessageObject object = getItem(position);
			m_oAsyncImageLoader.setImageDrawableAsync(holder.img,object.getImg(),getResources().getDrawable(R.drawable.message_picture), getResources().getDrawable(R.drawable.message_picture), mContext);
			holder.title_txt.setText(object.getToMan());
			holder.time_txt.setText(object.getTime());
			holder.content_txt.setText(object.getContent());
			
			holder.layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(BaseActivity.activity_num == BaseActivity.MESSAGE_ACTIVITY)
					{
						((MessageActivity)mContext).notifyData(MessageList.get((Integer)v.getTag(R.id.imageId)).getToMan());
					}
					else
					{
						Intent intent =  new Intent(mContext, MessageActivity.class);
						intent.putExtra("toMan", MessageList.get((Integer)v.getTag(R.id.imageId)).getToMan());
						mContext.startActivity(intent);
						((Activity)mContext).overridePendingTransition(0,0);
						((Activity)mContext).finish();
						BaseActivity.activity_num = BaseActivity.MESSAGE_ACTIVITY;
					}
					popup.dismissPopup();
					
				}
			});
			
			

			return convertView;
		}
	}

	class ViewHolder {
		private RelativeLayout layout;
		private TextView title_txt;
		private TextView time_txt;
		private TextView content_txt;
		private ImageView img;
	}

	public void notifyData() {
		listAdapter.notifyDataSetChanged();
	}

}
