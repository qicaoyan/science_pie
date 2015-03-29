package com.science.activity;

import com.example.science.R;
import com.science.services.FunctionManage;
import com.science.services.MyApplication;
import com.science.services.ToastProxy;
import com.science.util.DefaultUtil;
import com.science.util.ShoucangUtil;
import com.science.view.MyHeader;
import com.science.view.MyImageButton;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
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
    private String url;
    //function fm
    private FunctionManage fm;
    private ShoucangUtil shoucang_util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = getLayoutInflater();  
		main=(LinearLayout)inflater.inflate(R.layout.common_content, null); 
		
		intent = this.getIntent();
		url = intent.getStringExtra("url");
		act_class = intent.getStringExtra("act_class");
		theme = intent.getStringExtra("theme");
		article_type = intent.getIntExtra("article_type", DefaultUtil.INAVAILABLE);
		article_id = intent.getIntExtra("article_id", DefaultUtil.INAVAILABLE);
		
		fm = new FunctionManage(this);
		
		
		setContentView(main);
		initView();
		setListener();

				

		setWebView();
		
		webView.loadUrl(url);
		
		//初始化未收藏
		shoucang_util = new ShoucangUtil(this);
		shoucang = checkShoucang();
		shoucang_btn.updateButtonState(shoucang);
		

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

		//LayoutInflater inflater = getLayoutInflater();
	}
	
	private void setListener()
	{
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
				
				
				
				MyApplication application = MyApplication.getInstance();
				if(!application.IsLogin())
				{
					fm.Login();
				}
				else
				{
				

				ShoucangUtil.OnShoucangListener listener = new ShoucangUtil.OnShoucangListener() {
					
					@Override
					public void onShoucang(int result) {
						// TODO Auto-generated method stub
						switch(result){
						case ShoucangUtil.RESULT_ADD_OK:
							shoucang = true;
							shoucang_btn.updateButtonState(true);
							//收藏至本地
							shoucang_util.addToLocalShoucang(article_type, article_id, theme, "四天前", url);
							toastAtBottom("收藏成功");
							break;
						case ShoucangUtil.RESULT_ADD_FAIL:
							toastAtBottom("收藏失败");
							break;
						case ShoucangUtil.RESULT_DELETE_OK:
							shoucang = false;
							shoucang_btn.updateButtonState(false);
							shoucang_util.dropFromLocalShoucang(article_type, article_id);
							toastAtBottom("取消收藏");
							break;
						case ShoucangUtil.RESULT_DELETE_FAIL:
							toastAtBottom("无法取消收藏");
							break;
						}
					}
				};
				shoucang_util.setOnShoucangListener(listener);
				if(!shoucang)
			    {
			    	
					shoucang_util.addShoucang(article_type, article_id, url, theme);
			    }
			    else
			    {
			    	shoucang_util.deleteShoucang(article_type, article_id);
			    	//shoucang = true;
			    }
			    
			   
			    	
			  }
			
			}
		});
		
		
		//点击喜欢按钮出来悬浮窗
		like_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				toastAtBottom("喜欢    +  1");

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
	    
	    
	    
	    share_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//FunctionManage fm = new FunctionManage(CommonContentActivity.this);
				fm.showShare();
			}
	    	
	    });
	    
	    
	    
	    
	    email_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText edit_box = new EditText(CommonContentActivity.this);
				//edit_box.setText(other_tag_str);
				edit_box.setHint("请输入邮箱");
				new AlertDialog.Builder(CommonContentActivity.this)
				.setTitle("发送文章到邮箱")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(edit_box)
				.setPositiveButton("发送", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//other_tag_str = other_input_ed.getText().toString();
						//other_tag_tv.setText(other_tag_str);
					}
				})
				.setNegativeButton("取消",null)
				.show();
			}
	    	
	    });
	}
	
	
	public boolean checkShoucang()
	{
		return shoucang_util.containInShoucang(article_type, article_id);
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
	
	
	public void toastAtBottom(CharSequence info){
		
		View v = findViewById(R.id.common_bottom_layout);
		v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), 
				  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		int height = v.getMeasuredHeight();
		ToastProxy toast = new ToastProxy(CommonContentActivity.this);
		toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 0, height);
		toast.setToastView(CommonContentActivity.this, info, ToastProxy.LENGTH_SHORT);
		toast.show();
	}
	
}
