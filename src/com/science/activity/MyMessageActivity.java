package com.science.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.science.R;
import com.science.json.JsonGetMyMessageHandler;
import com.science.services.MyApplication;
import com.science.util.Url;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MyMessageActivity extends Activity {
	private MyApplication myApplication=null;
	private ImageButton headerback=null;
	private TextView headertitle;
	private ListView myListView=null;
	private List<Map<String, Object>> myList;
	private JsonGetMyMessageHandler jsonGetMyMessageHandler=null;
	private MyHandler handler=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		myApplication=(MyApplication)this.getApplication();
		
		// 去掉顶部灰条
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mycollection);
		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		
		InitVariable();
		InitViews();
		InitData();
		SetListener();
		
		GetMyCollection();
	}
	
	private void InitVariable()
	{
		//myListView=new MyListView(MyCollectionActivity.this);
		jsonGetMyMessageHandler=new JsonGetMyMessageHandler();
		handler=new MyHandler();
//		myList=new ArrayList<Map<String,Object>>();
//		Map<String , Object> map1=new HashMap<String, Object>();
//		map1.put("title", "北京市关于征集2014年中央文化产业发展专项资金一般项目的通知");
//		map1.put("time", "4天前");
//		myList.add(map1);

	}
	
	private void InitViews()
	{
		headerback=(ImageButton)findViewById(R.id.settingback);
		headertitle=(TextView)findViewById(R.id.settingheadertitle);
		myListView=(ListView)findViewById(R.id.myCollectionList);
	}
	
	private void InitData()
	{
		//myListView.setAdapter(new myAdapte());
		headertitle.setText("我的消息");
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
		//myInfo.setOnClickListener(onClickListener);
	}
	
	private void GetMyCollection()
	{
		MyThreadGetMyMessage myThreadGetMyMessaage=new MyThreadGetMyMessage();
		new Thread(myThreadGetMyMessaage).start();
	}
	private class myAdapte extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (myList!=null) {
				return myList.size();
			}
			else {
				return 0;
			}
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			if (myList!=null) {
				return myList.get(arg0);
			}
			else {
				return null;
			}
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			convertView = getLayoutInflater().inflate(R.layout.mycollectionitem,
					null);
			TextView title=(TextView)convertView.findViewById(R.id.myCollectionItemTitle);
			TextView time=(TextView)convertView.findViewById(R.id.myCollectionItemsTime);
			
			String strtitle=(String) myList.get(position).get("message");
			String strTime=(String)myList.get(position).get("date");
			
			title.setText(strtitle);
			time.setText(strTime);
			return convertView;
		}
		
	}

	private class MyThreadGetMyMessage implements Runnable
    {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			URL url;
			try {
				url = new URL(myApplication.ComposeToken(Url.getMessage));
				URLConnection con = url.openConnection();
				con.connect();
				InputStream input = con.getInputStream();
				myList=jsonGetMyMessageHandler.getListItems(input);
				if (myList!=null) {
					handler.sendEmptyMessage(2);
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
	
	private class MyHandler extends Handler {

    	@Override
    	public void dispatchMessage(Message msg) {
    		// TODO Auto-generated method stub
    		super.dispatchMessage(msg);
    	}

    	@Override
    	public void handleMessage(Message msg) {
    		if (msg.what == 2) {
    			myListView.setAdapter(new myAdapte());
			} 
    		super.handleMessage(msg);
    	}

    	@Override
    	public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
    		// TODO Auto-generated method stub
    		return super.sendMessageAtTime(msg, uptimeMillis);
    	}

    	@Override
    	public String toString() {
    		// TODO Auto-generated method stub
    		return super.toString();
    	}
    	
    }
}
