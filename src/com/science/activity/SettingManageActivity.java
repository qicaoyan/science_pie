package com.science.activity;

import com.example.science.R;
import com.science.services.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingManageActivity extends Activity{
	
	private MyApplication myApplication=null;
	private ImageButton headerback=null;
	private TextView headertitle;
	private LinearLayout myInfo=null;
	private LinearLayout myCollection=null;
	private LinearLayout myPublish=null;
	private LinearLayout myMessage=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		myApplication=(MyApplication)this.getApplication();
		
		// 去掉顶部灰条
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settingmanage);
		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		
		InitVariable();
		InitViews();
		InitData();
		SetListener();
	}
	
	private void InitVariable()
	{
		
	}
	
	private void InitViews()
	{
		headerback=(ImageButton)findViewById(R.id.settingback);
		headertitle=(TextView)findViewById(R.id.settingheadertitle);
		myInfo=(LinearLayout)findViewById(R.id.settingmyinfo);
		myCollection=(LinearLayout)findViewById(R.id.settingmycollection);
		myPublish=(LinearLayout)findViewById(R.id.settingpublish);
		myMessage=(LinearLayout)findViewById(R.id.settingmymessage);
	}
	
	private void InitData()
	{
		headertitle.setText("个人设置");
		headerback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	
	private void SetListener()
	{
		myInfo.setOnClickListener(onClickListener);
		myCollection.setOnClickListener(onClickListener);
		myPublish.setOnClickListener(onClickListener);
		myMessage.setOnClickListener(onClickListener);
	}
	
	private View.OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.settingmyinfo:
				Intent intent=new Intent(SettingManageActivity.this,MyInfoActivity.class);
				startActivity(intent);
				break;
			case R.id.settingmycollection:
				Intent collection=new Intent(SettingManageActivity.this,MyCollectionActivity.class);
				startActivity(collection);
				break;
			case R.id.settingpublish:
				Intent publish=new Intent(SettingManageActivity.this,MyPublishActivity.class);
				startActivity(publish);
				break;
			case R.id.settingmymessage:
				Intent message=new Intent(SettingManageActivity.this,MyMessageActivity.class);
				startActivity(message);
				break;
			default:
				break;
			}
		}
	};
	

}
