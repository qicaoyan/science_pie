package com.science.activity;

import com.example.science.R;
import com.science.services.MyApplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MyInfoActivity extends Activity{
	private MyApplication myApplication=null;
	private EditText nickName=null;
	private EditText userName=null;
	private EditText password=null;
	private EditText repassword=null;
	private Spinner diyu=null;
	private Spinner zhiye=null;
	private static final String[] strDiYu={"河北","山东","海南","河南","山西"};
	private static final String[] strZhiYe={"学生","学者","科研人员"};
	private ArrayAdapter<String> adapterDiYu;
	private ArrayAdapter<String> adapterZhiYe;
	
	private ImageButton headerback=null;
	private TextView headertitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		myApplication=(MyApplication)this.getApplication();
		
		// 去掉顶部灰条
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myinfo);
		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		
		InitVariable();
		InitViews();
		InitData();
	}
	
	private void InitVariable()
	{
		
	}
	
	private void InitViews()
	{
		headerback=(ImageButton)findViewById(R.id.settingback);
		headertitle=(TextView)findViewById(R.id.settingheadertitle);
		diyu=(Spinner)findViewById(R.id.myinfordiyu);
		zhiye=(Spinner)findViewById(R.id.myinfoshenfen);
	}
	
	private void InitData()
	{
		adapterDiYu=new ArrayAdapter<String>(this,android.R.layout.browser_link_context_header,strDiYu);
		adapterZhiYe=new ArrayAdapter<String>(this,android.R.layout.browser_link_context_header,strZhiYe);
		adapterDiYu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterZhiYe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		diyu.setAdapter(adapterDiYu);
		zhiye.setAdapter(adapterZhiYe);
		
		headertitle.setText("我的资料");
		headerback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
}
