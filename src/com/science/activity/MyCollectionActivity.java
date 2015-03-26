package com.science.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.science.R;
import com.science.services.MyApplication;
import com.science.util.ShoucangUtil;
import com.science.view.MyListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyCollectionActivity extends Activity {


	private MyApplication myApplication=null;
	private ImageButton headerback=null;
	private TextView headertitle;
	private ListView myListView=null;
	private List<Map<String, Object>> myList;
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
	}
	
	private void InitVariable()
	{
		//myListView=new MyListView(MyCollectionActivity.this);
		//myList=new ArrayList<Map<String,Object>>();
		ShoucangUtil shoucang_util = new ShoucangUtil(this);
		myList = shoucang_util.getLocalShoucangList();
		//Map<String , Object> map1=new HashMap<String, Object>();
		//map1.put("title", "北京市关于征集2014年中央文化产业发展专项资金一般项目的通知");
		//map1.put("time", "4天前");
		//myList.add(map1);

	}
	
	private void InitViews()
	{
		headerback=(ImageButton)findViewById(R.id.settingback);
		headertitle=(TextView)findViewById(R.id.settingheadertitle);
		myListView=(ListView)findViewById(R.id.myCollectionList);
	}
	
	private void InitData()
	{
		myListView.setAdapter(new myAdapte());
		headertitle.setText("我的收藏");
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
			
			String strtitle=(String) myList.get(position).get("title");
			String strTime=(String)myList.get(position).get("description");
			title.setText(strtitle);
			time.setText(strTime);
			return convertView;
		}
		
	}
}
