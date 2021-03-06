package com.ssm.walk_match.list;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
import com.ssm.walk_match.object.FriendObject;
import com.ssm.walk_match.object.MessageObject;
import com.ssm.walk_match.object.RankObject;
import com.ssm.walk_match.util.AsyncImageLoader;
import com.ssm.walk_match.util.Util;

public class RankWorldListView extends ListView  {
	private Context mContext;
	private RankWorldListViewAdapter listAdapter;
	ViewHolder holder;
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private Handler m_oHandler ;
	private ArrayList<RankObject> RankWorldList;
	private int[] nation_img = {R.drawable.australia,R.drawable.austria,R.drawable.belgium,R.drawable.brazil
			,R.drawable.canada,R.drawable.china,R.drawable.czechrepublic,R.drawable.denmark
			,R.drawable.finland,R.drawable.france,R.drawable.germany,R.drawable.greece,R.drawable.hongkong
			,R.drawable.hungary,R.drawable.iceland,R.drawable.india,R.drawable.indonesia,R.drawable.italy
			,R.drawable.korea,R.drawable.japan,R.drawable.malaysia,R.drawable.mexico,R.drawable.netherland
			,R.drawable.newzeland,R.drawable.norway,R.drawable.poland,R.drawable.portugal,R.drawable.russia
			,R.drawable.saudiarabia,R.drawable.singapore,R.drawable.spain,R.drawable.sweden,R.drawable.switzerland
			,R.drawable.thailand,R.drawable.uae,R.drawable.unitedkingdom,R.drawable.unitedstatesofamerica,R.drawable.vietnam};
	public RankWorldListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}

	public RankWorldListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initListView();
	}

	public void setHandler(Handler handler)
	{
		this.m_oHandler = handler;
	}
	public void setData(ArrayList<RankObject> object)
	{
		RankWorldList = object;
		listAdapter = new RankWorldListViewAdapter(object);
		setAdapter(listAdapter);
	}
	public void initListView() {
		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setFadingEdgeLength(0);
		setDividerHeight(0);
		setCacheColorHint(0x00000000);
	}
	
	public void recycle() {
		if (listAdapter != null)
			listAdapter.recycle();
	}

	class RankWorldListViewAdapter extends ArrayAdapter<RankObject> {
		private RelativeLayout mLayout;
		private ImageView mImg;
		private ImageView mCultureImg;
		private TextView mRankTxt;
		private TextView mNameTxt;
		private TextView mPersentTxt;
		private TextView mWinTxt;
		private TextView mMatchTxt;
		
		private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();

		public void recycle() {
			for (WeakReference<View> ref : mRecycleList) {
				Util.recursiveRecycle(ref.get());
			}
		}

		public RankWorldListViewAdapter(List<RankObject> objects) {
			super(mContext, 0, objects);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_rank_world, null);
				mLayout = (RelativeLayout) convertView.findViewById(R.id.rank_layout);
				mImg = (ImageView) convertView.findViewById(R.id.rank_img);
				mCultureImg = (ImageView) convertView.findViewById(R.id.culture_img);
				mRankTxt = (TextView) convertView.findViewById(R.id.rank_number);
				mNameTxt = (TextView) convertView.findViewById(R.id.rank_name);
				mPersentTxt = (TextView) convertView.findViewById(R.id.rank_winner_percent);
				mWinTxt = (TextView) convertView.findViewById(R.id.rank_content_win);
				mMatchTxt = (TextView) convertView.findViewById(R.id.rank_content_match);

				holder = new ViewHolder();
				holder.layout = mLayout;
				holder.img = mImg;
				holder.cuture_img = mCultureImg;
				holder.rank_txt = mRankTxt;
				holder.name_txt = mNameTxt;
				holder.percent_txt = mPersentTxt;
				holder.win_txt = mWinTxt;
				holder.match_txt = mMatchTxt;
				
				convertView.setTag(holder);

				mRecycleList.add(new WeakReference<View>(convertView));
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			try{
			convertView.setTag(R.id.imageId, position);
			holder.layout.setTag(R.id.imageId, position);
			RankObject object = getItem(position);
			m_oAsyncImageLoader.setImageDrawableAsync(holder.img,object.getImg(),getResources().getDrawable(R.drawable.cp_picture), getResources().getDrawable(R.drawable.cp_picture), mContext);
			holder.cuture_img.setBackgroundResource(nation_img[object.getNation()]);
			holder.rank_txt.setText(object.getWorld_num()+"");
			holder.name_txt.setText(object.getName());
			if(object.getMatch() == 0)
			{
				holder.percent_txt.setText("0");
			}
			else
			{
				double data = (double)object.getWin() / (double)object.getMatch() * 100;
				holder.percent_txt.setText( String.format("%.1f", data));
			}
			holder.win_txt.setText(object.getWin()+"");
			holder.match_txt.setText((object.getMatch()-object.getWin())+"");
			}
			catch(Exception e)
			{
				e.printStackTrace();
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
		private RelativeLayout layout;
		private TextView rank_txt;
		private TextView name_txt;
		private TextView percent_txt;
		private TextView win_txt;
		private TextView match_txt;
		private ImageView img;
		private ImageView cuture_img;
	}

	public void notifyData() {
		listAdapter.notifyDataSetChanged();
	}

}
