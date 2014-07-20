package com.ssm.walk_match.main;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssm.walk_match.R;
import com.ssm.walk_match.component.LoadingPopup;
import com.ssm.walk_match.component.NationSelectPopup;
import com.ssm.walk_match.net.HttpClientNet;
import com.ssm.walk_match.net.Params;
import com.ssm.walk_match.service.ServiceType;
import com.ssm.walk_match.util.AppManager;

public class SignActivity extends Activity implements View.OnClickListener, HttpClientNet.OnResponseListener{

	private boolean checkDouble = false; 
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private LoadingPopup loading;
	private static final int REQ_PICK_IMAGE = 0;
	//private static final String TEMP_PHOTO_FILE = "tmp_profile.jpg";
	private static Uri m_oImageCropUri;
	private String m_oStrImgThum = "";
	private EditText mEditName;
	private EditText mEditEmail;
	private EditText mEditPwd;
	private LinearLayout mEditNation;
	private TextView mTxtNation;
	private ImageView mBtnImg;
	private LinearLayout mBtnJoin;
	
	private Handler m_oHandler;
	private int nation;
	private Context mContext;
	private Uri mImageCaptureUri;
	
	private static final String TEMP_PHOTO_FILE = "temp.jpg";       // 임시 저장파일
    private static final int REQ_CODE_PICK_IMAGE = 0;
	
	private String[] nation_name = {"Australia","Austria","Belgium","Brazil","Canada","China","Czech Repub","Denmark"
			,"Finland","France","Germany","Greece","Hong Kong","Hungry","Iceland","India","Indonesia","Italy"
			,"Korea","Japan","Malaysia","Mexico","Netherland","New Zealand","Norway","Poland","Portugal","Russia"
			,"Saudi Arabia","Singapore","Spain","Sweden","Switzerland","Thailand","UAE","U.K","USA","Vietnam"};
	@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign);
		mContext = this;
		setUi();
		AppManager.getInstance().setActivity(this);
		
	}
    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
   	public void onBackPressed() {
       		Intent intent =  new Intent(this.getApplicationContext(), FirstActivity.class);
   			startActivity(intent);
   			overridePendingTransition(0,0);
   			finish();
       }
    public void setUi()
    {
    	mBtnImg = (ImageView)findViewById(R.id.photo_img);
    	mBtnImg.setOnClickListener(this);
    	mBtnJoin = (LinearLayout)findViewById(R.id.btn_join);
    	mEditEmail = (EditText)findViewById(R.id.edit_txt_email);
    	mEditPwd = (EditText)findViewById(R.id.edit_txt_password);
    	mEditName = (EditText)findViewById(R.id.edit_txt_username);
    	mEditNation = (LinearLayout)findViewById(R.id.edit_txt_nation);
    	mTxtNation = (TextView)findViewById(R.id.edit_text_nation);
    	mEditNation.setOnClickListener(this);
    	mBtnJoin.setOnClickListener(this);
        m_oHandler = new Handler() {
			  public void handleMessage(android.os.Message msg) {
				 nation = msg.arg1;
				 mTxtNation.setText(nation_name[nation]);
			  };
		 };
		 mEditEmail.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					if(hasFocus==false)
					{
						if((mEditEmail.getText().toString().equals("")))
						{
							
							Toast.makeText(SignActivity.this, "Please enter id", Toast.LENGTH_SHORT).show();
						}
						else
						{
							reqeustDoubleId();
						}
					}
					
				}
			});
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
    public void startProgressDialog() 
	{
		if( loading == null )
		{
			loading = new LoadingPopup(this);
			loading.start();
		}
	}
	public void stopProgressDialog() 
	{
		if( loading != null )
		{
			loading.stop();
			loading = null;
		}
	}
    public void reqeustDoubleId()
	{
		//중복체크 요청
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN_DOUBLE_ID);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("email", mEditEmail.getText().toString()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestJoin()
	{
		//회원가입 요청
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN);
		loginService.setCheck(true);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("email", mEditEmail.getText().toString()));
		loginParams.add(new Params("pwd", mEditPwd.getText().toString()));
		loginParams.add(new Params("name", mEditName.getText().toString()));
		loginParams.add(new Params("nation", nation+""));
		if(m_oImageCropUri != null)
		{
			
			loginParams.add(new Params("thum", m_oImageCropUri.getPath()));
		}
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.btn_join:
			{
				if(!checkDouble)
				{
					Toast.makeText(this, "id redundancy check", Toast.LENGTH_SHORT).show();
				}
				else if(mEditPwd.getText().toString().equals(""))
				{
					Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();			}
				else if(mEditPwd.getText().toString().length() < 4)
				{
					Toast.makeText(this, "Please enter at least a four-digit password", Toast.LENGTH_SHORT).show();
				}
				else
				{
					requestJoin();
				}
				break;
			}
			case R.id.photo_img:
			{
//				 Intent intent = new Intent(Intent.ACTION_PICK);
//			     intent.setType("image/*");
//			     intent.putExtra("crop", "true");
//			     intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
//			     intent.putExtra("output", m_oImageCropUri);
//			     intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//			     startActivityForResult(intent, REQ_PICK_IMAGE);
//				doTakeAlbumAction();
				 	Intent intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                intent.setType("image/*");              // 모든 이미지
	                intent.putExtra("crop", "true");        // Crop기능 활성화
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // 임시파일 생성
	                intent.putExtra("outputFormat",  Bitmap.CompressFormat.JPEG.toString());
	                intent.putExtra("output", m_oImageCropUri);
	                startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
	                // REQ_CODE_PICK_IMAGE == requestCode
				break;
			}
			case R.id.edit_txt_nation:
			{
				NationSelectPopup popup = new NationSelectPopup(mContext);
				popup.setHandler(m_oHandler);
				break;
			}
			
		}
	}
	private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }
 
    /** 외장메모리에 임시 이미지 파일을 생성하여 그 파일의 경로를 반환  */
    private File getTempFile() {
        if (isSDCARDMOUNTED()) {
            File f = new File(Environment.getExternalStorageDirectory(), // 외장메모리 경로
                    TEMP_PHOTO_FILE);
            try {
                f.createNewFile();      // 외장메모리에 temp.jpg 파일 생성
            } catch (IOException e) {
            }
 
            return f;
        } else
            return null;
    }
 
    /** SD카드가 마운트 되어 있는지 확인 */
    private boolean isSDCARDMOUNTED() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
 
        return false;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
 
        switch (requestCode) {
        case REQ_CODE_PICK_IMAGE:
            if (resultCode == RESULT_OK) {
                if (imageData != null) {
                    String filePath = Environment.getExternalStorageDirectory()
                            + "/temp.jpg";
 
                    System.out.println("path" + filePath); // logCat으로 경로확인.
                    
                    File outFile = new File(filePath);
    				m_oImageCropUri = Uri.fromFile(outFile);
    				
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    // temp.jpg파일을 Bitmap으로 디코딩한다.
 
                    ImageView _image = (ImageView) findViewById(R.id.photo_img);
                    _image.setImageBitmap(selectedImage); 
                    // temp.jpg파일을 이미지뷰에 씌운다.
                }
            }
            break;
        }
    }
	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_JOIN_DOUBLE_ID"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					Toast.makeText(this, "Id is available.", Toast.LENGTH_SHORT).show();
					checkDouble = true;
					//m_oImgDoubleCheck.setBackgroundResource(R.drawable.check_double);
				}
				else
				{
					Toast.makeText(this, "There is a duplicate email.", Toast.LENGTH_SHORT).show();
					checkDouble = false;
					//m_oImgDoubleCheck.setBackgroundResource(R.drawable.check_double_no);
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG.JOIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					Toast.makeText(this, "Join expire.", Toast.LENGTH_SHORT).show();
					Intent intent =  new Intent(this.getApplicationContext(), FirstActivity.class);
		   			startActivity(intent);
		   			overridePendingTransition(0,0);
		   			finish();
				}
				else
				{
					Toast.makeText(this, "Join failed.", Toast.LENGTH_SHORT).show();
					Intent intent =  new Intent(this.getApplicationContext(), FirstActivity.class);
		   			startActivity(intent);
		   			overridePendingTransition(0,0);
		   			finish();
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
