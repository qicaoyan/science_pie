package com.science.activity;

import com.example.science.R;
import com.example.science.R.id;
import com.example.science.R.layout;
import com.example.science.R.menu;
import com.science.view.MyHeaderView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebView;

public class CoopDetailActivity extends Activity {

	private MyHeaderView header_view;
	private WebView      web_view;
	private String       url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.coop_detail);
		
		Intent intent = this.getIntent();
		url = intent.getStringExtra("url");
		header_view = (MyHeaderView) findViewById(R.id.coop_detail_header);
		header_view.SetHeaderText("合作研究");
		web_view = (WebView) findViewById(R.id.coop_detail_web);
		web_view.loadUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.coop_detail, menu);
		return true;
	}

}
