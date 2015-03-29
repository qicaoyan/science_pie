package com.science.activity;
import com.example.science.R;

import com.science.fragment.CoopPaperFragment;
import com.science.fragment.CoopResourceFragment;
import com.science.view.MyHeader;
import com.science.view.MyImageButton;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CoopResearchActivity extends Activity  {
	


	
	
	private LinearLayout main=null;

	private MyImageButton mTabResource;  
	private MyImageButton mTabPaper;  
	private CoopResourceFragment mResource;  
	private CoopPaperFragment mPaper;
	
	private MyHeader coop_header = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		LayoutInflater inflater = getLayoutInflater();  
		main=(LinearLayout)inflater.inflate(R.layout.coop_research, null); 
		setContentView(main);
		//setContentView(R.layout.coop_research);

       // 初始化控件和声明事件  
		//mTabResource = (MyImageButton) findViewById(R.id.coop_resource);  
		//mTabPaper = (MyImageButton) findViewById(R.id.coop_paper);  
        //mTabResource.setOnClickListener(this);  
        //mTabPaper.setOnClickListener(this);  
 
        coop_header = (MyHeader) findViewById(R.id.coop_header);
        OnClickListener on_click_listener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
        	
        };
        coop_header.SetHeaderText("合作研究");
        coop_header.SetHeaderButtons(new String[]{"资源","论文"});
        coop_header.SetOnHeadButtonClickListener(on_click_listener, 0);
        coop_header.SetOnHeadButtonClickListener(on_click_listener, 1);
        coop_header.SetSelected(0);
       // 设置默认的Fragment  
        setDefaultFragment();  
		
	}
	
	private void setDefaultFragment()
	{
		FragmentManager fm = getFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		mResource = new CoopResourceFragment();
		transaction.replace(R.id.id_content, mResource);
		transaction.commit();
	}

	/*
	@Override
	public void onClick(View v)
	{
		FragmentManager fm = getFragmentManager();
		// 开启Fragment事务
		FragmentTransaction transaction = fm.beginTransaction();

		switch (v.getId())
		{
		case R.id.coop_resource:
			if (mResource == null)
			{
				mResource = new FragmentOfCoopResource();
				
			}
			
			// 使用当前Fragment的布局替代id_content的控件
			transaction.replace(R.id.id_content, mResource);
			
			Toast.makeText(CoopResearchActivity.this,"click",Toast.LENGTH_SHORT).show();
			break;
		case R.id.coop_paper:
			if (mPaper == null)
			{
				mPaper = new FragmentOfCoopPaper();
				Log.i("mresource","isNULL");
			}
			else Log.i("mresource","!isNULL");
			transaction.replace(R.id.id_content, mPaper);
			
			break;
		}
		// transaction.addToBackStack();
		// 事务提交
		transaction.commit();
	}
*/
	


}
