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
import com.ssm.walk_match.main.MainActivity;
import com.ssm.walk_match.main.MessageActivity;
import com.ssm.walk_match.object.HistoryObject;
import com.ssm.walk_match.object.LoginObject;
import com.ssm.walk_match.object.MessageObject;
import com.ssm.walk_match.util.AsyncImageLoader;
import com.ssm.walk_match.util.Util;

public class MeHistoryListView extends ListView  {
	private Context mContext;
	private	HistoryListViewAdapter listAdapter;
	ViewHolder holder;
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private Handler m_oHandler ;
	private ArrayList<HistoryObject> MessageList;
	public static String manName;
	private MessagePopup popup;
	private int[] nation_img = {R.drawable.australia,R.drawable.austria,R.drawable.belgium,R.drawable.brazil
			,R.drawable.canada,R.drawable.china,R.drawable.czechrepublic,R.drawable.denmark
			,R.drawable.finland,R.drawable.france,R.drawable.germany,R.drawable.greece,R.drawable.hongkong
			,R.drawable.hungary,R.drawable.iceland,R.drawable.india,R.drawable.indonesia,R.drawable.italy
			,R.drawable.korea,R.drawable.japan,R.drawable.malaysia,R.drawable.mexico,R.drawable.netherland
			,R.drawable.newzeland,R.drawable.norway,R.drawable.poland,R.drawable.portugal,R.drawable.russia
			,R.drawable.saudiarabia,R.drawable.singapore,R.drawable.spain,R.drawable.sweden,R.drawable.switzerland
			,R.drawable.thailand,R.drawable.uae,R.drawable.unitedkingdom,R.drawable.unitedstatesofamerica,R.drawable.vietnam};
	public MeHistoryListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}

	public MeHistoryListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initListView();
	}

	public void setHandler(Handler handler)
	{
		this.m_oHandler = handler;
	}
	public void setData(ArrayList<HistoryObject> object)
	{
		MessageList = object;
		listAdapter = new HistoryListViewAdapter(object);
		setAdapter(listAdapter);
	}
	public void setData(ArrayList<HistoryObject> object,MessagePopup popup)
	{
		this.popup = popup;
		MessageList = object;
		listAdapter = new HistoryListViewAdapter(object);
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

	class HistoryListViewAdapter extends ArrayAdapter<HistoryObject> {
		private RelativeLayout mLayout;
		private TextView mTime;
		private ImageView mMyNationImg;
		private TextView mStepTxt;
		private ImageView mResultimg;
		private TextView mFriStepTxt;
		private TextView mFriNameTxt;
		private ImageView mFriNationImg;
		
		private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();

		public void recycle() {
			for (WeakReference<View> ref : mRecycleList) {
				Util.recursiveRecycle(ref.get());
			}
		}

		public HistoryListViewAdapter(List<HistoryObject> objects) {
			super(mContext, 0, objects);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_me_list_view, null);
				mLayout = (RelativeLayout) convertView.findViewById(R.id.list_me_all_layout);
				mMyNationImg = (ImageView) convertView.findViewById(R.id.img_nation);
				mStepTxt = (TextView) convertView.findViewById(R.id.me_step);
				mResultimg = (ImageView) convertView.findViewById(R.id.situation);
				mFriStepTxt = (TextView) convertView.findViewById(R.id.fri_step);
				mFriNameTxt = (TextView) convertView.findViewById(R.id.fri_name);
				mFriNationImg = (ImageView) convertView.findViewById(R.id.fri_img_nation);
				mTime = (TextView)convertView.findViewById(R.id.match_time_txt);
				holder = new ViewHolder();
				holder.layout = mLayout;
				holder.myNationImg = mMyNationImg;
				holder.stepTxt = mStepTxt;
				holder.resultimg = mResultimg;
				holder.friStepTxt = mFriStepTxt;
				holder.friNameTxt = mFriNameTxt;
				holder.friNationImg = mFriNationImg;
				holder.time = mTime;
				convertView.setTag(holder);

				mRecycleList.add(new WeakReference<View>(convertView));
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			convertView.setTag(R.id.imageId, position);
			holder.layout.setTag(R.id.imageId, position);
			HistoryObject object = getItem(position);
			if(object.getMan1().equals(LoginObject.getInstance().getEmail()))
			{
				//³»°¡ ½Â
				holder.time .setText(object.getTime());
				holder.resultimg.setBackgroundResource(R.drawable.match_down_win);
				holder.stepTxt .setText(object.getStep1());
				holder.myNationImg.setBackgroundResource(nation_img[Integer.parseInt(object.getNation1())]);
				holder.friNationImg.setBackgroundResource(nation_img[Integer.parseInt(object.getNation2())]);
				holder.friStepTxt.setText(object.getStep2());
				holder.friNameTxt.setText(object.getName2());
			}
			else
			{
				holder.time .setText(object.getTime());
				holder.resultimg.setBackgroundResource(R.drawable.match_down_lose);
				holder.stepTxt .setText(object.getStep2());
				holder.myNationImg.setBackgroundResource(nation_img[Integer.parseInt(object.getNation2())]);
				holder.friNationImg.setBackgroundResource(nation_img[Integer.parseInt(object.getNation1())]);
				holder.friStepTxt.setText(object.getStep1());
				holder.friNameTxt.setText(object.getName1());
			}
			holder.layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
				}
			});
			
			

			return convertView;
		}
	}

	class ViewHolder {
		private TextView time;
		private RelativeLayout layout;
		private ImageView myNationImg;
		private TextView stepTxt;
		private ImageView resultimg;
		private TextView friStepTxt;
		private TextView friNameTxt;
		private ImageView friNationImg;
	}

	public void notifyData() {
		listAdapter.notifyDataSetChanged();
	}

}
