package com.science.activity;

import com.example.science.R;
import com.science.services.FunctionManage;
import com.science.services.ToastProxy;
import com.science.util.DefaultUtil;
import com.science.view.MyHeader;
import com.science.view.MyImageButton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class CommonContentActivity extends Activity{
	private View main=null;
	ProgressBar bar;
	private WebView webView=null;
	private MyImageButton go_back_btn;
	private MyImageButton shoucang_btn;
	private MyImageButton comment_btn;
	private MyImageButton share_btn;
	private MyImageButton like_btn;
	private MyImageButton email_btn;
	private boolean shoucang;//true表示已收藏，false表示未收藏
	private boolean like;//true表示点赞，false取消点赞
    private String  act_class;
    private String title;
    private Intent intent;
    private String theme;
    private int article_type;
    private int article_id;
    //function fm
    private FunctionManage fm;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = getLayoutInflater();  
		main=(LinearLayout)inflater.inflate(R.layout.commoncontent, null); 
		
		intent = this.getIntent();
		String value = intent.getStringExtra("url");
		act_class = intent.getStringExtra("act_class");
		theme = intent.getStringExtra("theme");
		article_type = intent.getIntExtra("article_type", DefaultUtil.INAVAILABLE);
		article_id = intent.getIntExtra("article_id", DefaultUtil.INAVAILABLE);
		setContentView(main);
		initView();
		setListener();

				

		setWebView();
		
		webView.loadUrl(value);
		
		//初始化未收藏
		shoucang = false;

	}
	
	private void initView()
	{
		
		webView=(WebView)main.findViewById(R.id.documentcontentwebview);
		bar = (ProgressBar)main.findViewById(R.id.documentcontentmyProgressBar);
		
		MyHeader header_view = (MyHeader) main.findViewById(R.id.item_header);
		if(act_class.equals("document"))
			header_view.SetHeaderText("文献速递");
		else if(act_class.equals("project"))
			header_view.SetHeaderText("项目申报");
		else if(act_class.equals("热点新闻"))
		{
			title=intent.getStringExtra("title");
			header_view.SetHeaderText(title);
		}
		go_back_btn = (MyImageButton)findViewById(R.id.go_back);
		shoucang_btn = (MyImageButton)findViewById(R.id.shoucang);
		comment_btn = (MyImageButton)findViewById(R.id.common_comment_btn);
		shoucang_btn.setDrawable(getResources().getDrawable(R.drawable.shoucang_a), getResources().getDrawable(R.drawable.shoucang_b));
		share_btn = (MyImageButton)findViewById(R.id.share);
		like_btn = (MyImageButton) findViewById(R.id.common_like_btn);
		email_btn = (MyImageButton) findViewById(R.id.common_email_btn);
		go_back_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonContentActivity.this.finish();
			}
			
		});
		
		shoucang_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    if(shoucang)
			       shoucang = false;
			    else
			    	shoucang = true;
			    
			    shoucang_btn.updateButtonState(shoucang);
			    	
			}
			
		});
		
		
		//点击喜欢按钮出来悬浮窗
		like_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				View v = findViewById(R.id.common_bottom_layout);
				v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), 
						  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
				int height = v.getMeasuredHeight();
				ToastProxy toast = new ToastProxy(CommonContentActivity.this);
				toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 0, height);
				toast.setToastView(CommonContentActivity.this, "喜欢  + 1", ToastProxy.LENGTH_SHORT);
				toast.show();
			}
			
		});
		
		
	    comment_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("theme",theme);
				intent.putExtra("article_type", article_type);
				Log.i("article_type", ""+article_type);
				intent.putExtra("article_id", article_id);
				intent.setClass(CommonContentActivity.this, CommentDetailActivity.class);
				startActivity(intent);
			}
	    	
	    });
	    
	    
	    fm = new FunctionManage(this);
	    share_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//FunctionManage fm = new FunctionManage(CommonContentActivity.this);
				fm.showShare();
			}
	    	
	    });
	    
		//LayoutInflater inflater = getLayoutInflater();
	}
	
	private void setListener()
	{
		
	}
	
	private void setWebView()
	{
		//支持javascript
		webView.getSettings().setJavaScriptEnabled(true); 
		// 设置可以支持缩放 
		webView.getSettings().setSupportZoom(false); 
		// 设置出现缩放工具 
		webView.getSettings().setBuiltInZoomControls(false);
		//扩大比例的缩放
		//webView.getSettings().setUseWideViewPort(false);
		//自适应屏幕
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setLoadWithOverviewMode(false);
		
		
		//将图片调整到适合webview的大小
		webView.getSettings().setUseWideViewPort(false);
		webView.requestFocusFromTouch();
		
		webView.setWebChromeClient(new WebChromeClient() {
	        @Override
	        public void onProgressChanged(WebView view, int newProgress) {
	            if (newProgress == 100) {
	                bar.setVisibility(View.INVISIBLE);
	            } else {
	                if (View.INVISIBLE == bar.getVisibility()) {
	                    bar.setVisibility(View.VISIBLE);
	                }
	                bar.setProgress(newProgress);
	            }
	            super.onProgressChanged(view, newProgress);
	        }
	        
	    });
	}
	
	
	
}
