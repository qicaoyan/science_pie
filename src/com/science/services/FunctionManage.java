package com.science.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.security.auth.login.LoginException;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

import com.example.science.R;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.science.json.JsonCommonResponseStateHandler;
import com.science.json.JsonDownLoadsKeywords;
import com.science.json.JsonUpdateGeTuiTagsHandler;
import com.science.util.Url;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class FunctionManage {
	
	public MyApplication myApplication;
	public Context context;
	public Map<String,String> keywordsMap=null;
	public JsonDownLoadsKeywords jsonDownLoadsKeywords;
	public JsonUpdateGeTuiTagsHandler jsonUpdateGeTuiTagsHandler=null;
	public String name=null;
	public String pass=null;
	public OnekeyShare oks=null;

	
	public FunctionManage(Context con)
	{
		myApplication=MyApplication.getInstance();
		context=con;
		jsonDownLoadsKeywords=new JsonDownLoadsKeywords();
		jsonUpdateGeTuiTagsHandler=new JsonUpdateGeTuiTagsHandler();
		ShareSDK.initSDK(context);
		oks=new OnekeyShare();
	}
	
	public  void Login()
	{
		//GetLoginInfo();
		//if (name==null||pass==null) {
		if(MyApplication.sidString == null)
			MyApplication.sidString = "";
		if(MyApplication.sidString.isEmpty()){
			Intent intent=new Intent();
			intent.setAction("com.science.intent.action.LOGIN");
			context.startActivity(intent);
		}
		else {
			MyThreadLogin myThreadLogin=new MyThreadLogin();
			new Thread(myThreadLogin).start();
		}		
		//UpdateCid();
	}
	
	
	
	public int Logout(){
		
		
		class LogoutThread extends Thread{
			
			int code = 0;
			@Override
			public void run(){
				String str_url = Url.composeLogoutUrl();
				URL url;
				
				try {
					url = new URL(str_url);
					URLConnection conn = url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					JsonCommonResponseStateHandler json = new JsonCommonResponseStateHandler();
				    code = json.getResponseStateCode(is);
				
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public int getCode(){
				return this.code;
			}
			
		}
		
		
		LogoutThread thread = new LogoutThread();
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
		
		return thread.getCode();
	}
	
	
	
	
	
	
	
	
	public void showShare() {

		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 
		// 分享时Notification的图标和文字
		 oks.setNotification(R.drawable.ic_launcher, context.getResources().getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 //oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://123.57.207.9/static/content/hotpage/content_38.html");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("不错不错");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://blog.sina.com.cn/s/blog_4ab761450102e8hy.html");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 //oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://123.57.207.9/static/content/hotpage/content_38.html");
		 
		// 启动分享GUI
		 oks.show(context);
		 }

	
	public void SaveLoginInfo(String userName,String userPass)
	{
		//实例化SharedPreferences对象（第一步） 
		SharedPreferences mySharedPreferences= myApplication.getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE); 
		//实例化SharedPreferences.Editor对象（第二步） 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		//用putString的方法保存数据 
		editor.putString("name", userName); 
		editor.putString("pass", userPass); 
		//editor.putString("", value)
		//提交当前数据 
		editor.commit(); 
	}
	
	public void GetLoginInfo()
	{
		//实例化SharedPreferences对象（第一步） 
		SharedPreferences mySharedPreferences= myApplication.getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE); 
	
		name=mySharedPreferences.getString("name",null);
		pass=mySharedPreferences.getString("pass", null);
	}
	
	public  void DownLoadKeywords()
	{
		MyThreadUpdateKeywords myThreadUpdateKeywords=new MyThreadUpdateKeywords();
		new Thread(myThreadUpdateKeywords).start();
	}
	
	public void UpdateCid()
	{
		MyThreadUpdateCid myThreadUpdateCid=new MyThreadUpdateCid();
		new Thread(myThreadUpdateCid).start();
		
	}
	
	public void UpdataTags()
	{
		MyThreadUpdateTags myThreadUpdateTags=new MyThreadUpdateTags();
		new Thread(myThreadUpdateTags).start();
	}
	
	private class MyThreadUpdateKeywords implements Runnable
    {
		@Override
		public void run() {
			// TODO Auto-generated method stub
						URL url;
						try {
				    		String temp="";
				    		for(int i=0;i<8;i++)
				    		{
				    			temp+=myApplication.keywords[i];
				    			temp+=" ";
				    		}
				    		temp=URLEncoder.encode(temp, "utf8");
							url = new URL(myApplication.ComposeToken(Url.UpdateKeywords+temp));
							URLConnection con = url.openConnection();
							con.connect();
							InputStream input = con.getInputStream();
							keywordsMap=jsonDownLoadsKeywords.getListItems(input);
							if (keywordsMap!=null) {
//								handler.sendEmptyMessage(2);
								//更新Application关键词数组
							}
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
    	
    }

	private class MyThreadLogin implements Runnable
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			 myApplication.Login(name, pass);
			 
			 UpdataTags();
		}
		
	}
	
	
	
	
	private class MyThreadLogout implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	
	

	public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {

		public void onShare(Platform platform, ShareParams paramsToShare) {

//			String text = platform.getContext().getString(R.string.share_title);
//			if ("WechatMoments".equals(platform.getName())) {
//				// 改写twitter分享内容中的text字段，否则会超长，
//				// 因为twitter会将图片地址当作文本的一部分去计算长度
//				text += platform.getContext().getString(R.string.share_to_wechatmoment);
//				paramsToShare.setText(text);
//			}else if("SinaWeibo".equals(platform.getName())){
//				text += platform.getContext().getString(R.string.share_to_sina);
//				paramsToShare.setText(text);			
//			}else if("TencentWeibo".equals(platform.getName())){
//				text += platform.getContext().getString(R.string.share_to_tencent);
//				paramsToShare.setText(text);			
//			}else if("ShortMessage".equals(platform.getName())){
//				text += platform.getContext().getString(R.string.share_to_sms);
//				paramsToShare.setText(text);
				
			}
		}
	
	private class MyThreadUpdateCid implements Runnable
	{

		@Override
		public void run() {
			
			try {
				URL url;
				String cidString=PushManager.getInstance().getClientid(context);
				url = new URL(myApplication.ComposeToken(Url.updateCid+PushManager.getInstance().getClientid(context)));
				Log.i("cid",PushManager.getInstance().getClientid(context));
				URLConnection con = url.openConnection();
				con.connect();
				InputStream input = con.getInputStream();
				
				//UpdataTags();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class MyThreadUpdateTags implements Runnable
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				URL url;
				url = new URL(myApplication.ComposeToken(Url.updateTags));
				URLConnection con = url.openConnection();
				con.connect();
				InputStream input = con.getInputStream();
				Map<String, String> map=jsonUpdateGeTuiTagsHandler.getListItems(input);
				if(map!=null)
				{
					String tag=map.get("pushtags");
					String [] tags=tag.split(",");
					Tag [] Tags=new Tag[tags.length];
					for (int i = 0; i < tags.length; i++) {
						Tag tempTag=new Tag();
						tempTag.setName(tags[i]);
						Tags[i]=tempTag;
					}
					
					Integer errorcode=PushManager.getInstance().setTag(context, Tags);
					Log.v("FunctionManage", "bbb"+errorcode.toString());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
