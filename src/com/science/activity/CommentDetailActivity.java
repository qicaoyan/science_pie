package com.science.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.science.R;
import com.example.science.R.layout;
import com.example.science.R.menu;
import com.science.interfaces.OnShowMoreListener;
import com.science.json.JsonCommentListHandler;
import com.science.util.CommentUtil;
import com.science.util.DefaultUtil;
import com.science.util.Url;
import com.science.view.MyImageButton;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommentDetailActivity extends Activity {

	private ListView comment_list_view;
	private int comment_num;
	private CommentListAdapter comment_list_adapter;
	private String comment_list_url;
	private String comment_theme;//评论的标题
	private int article_type;//表明评论的类型
	private int article_id;//表明评论项目或论文或热点的id号
	private int comment_id;//评论的id
	//private List<Map<String,Object>> comment_list;
	private List<CommentUtil> comment_list;
	private JsonCommentListHandler json;
	private final int LOADING_COMMENT_SUCCEED = 0;
	private final int LOADING_COMMENT_FAIL = 1;
	private MyImageButton back_btn;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.comment_list);
        initVariable();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_detail, menu);
        return true;
    }
    
    public void initVariable()
    {
    	comment_num = 5;
    	comment_list_adapter = new CommentListAdapter();
    	comment_list = new ArrayList<CommentUtil>();
    	json = new JsonCommentListHandler();
    	Intent intent = this.getIntent();
    	comment_theme = intent.getStringExtra("theme");
    	article_type = 0;//intent.getIntExtra("article_type", DefaultUtil.INAVAILABLE);
    	article_id = 1;//intent.getIntExtra("article_id", DefaultUtil.INAVAILABLE);
    	comment_id = DefaultUtil.MAX_VALUE;
    	requestData();
    }
    
    
    public void requestData()
    {
    	LoadingCommentThread thread = new  LoadingCommentThread();
    	new Thread(thread).start();
    }
    
    public void initViews()
    {
    	comment_list_view = (ListView)findViewById(R.id.comment_list_view);
    	//comment_list_view.setAdapter(comment_list_adapter);
    	TextView tv = (TextView) findViewById(R.id.comment_theme_title);
    	tv.setText(comment_theme);
    	back_btn = (MyImageButton)findViewById(R.id.go_back);
    	back_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommentDetailActivity.this.finish();
			}
    		
    	});
    }
    
    
    
    
    
    
    
    
    private class CommentListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(comment_list != null)
				return  comment_list.size();
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = getLayoutInflater().inflate(R.layout.comment_item, null);
            convertView = v;
			
			//final TextView comment_show_more = (TextView)v.findViewById(R.id.show_more);
			TextView customer_name_tv = (TextView) v.findViewById(R.id.customer_name_tv);
			TextView date_tv = (TextView) v.findViewById(R.id.date_tv);
			final TextView comment_detail_tv = (TextView) v.findViewById(R.id.comment_detail_tv);
			
			CommentUtil cu = comment_list.get(position);
			if(cu == null)
				return null;
			String customer_name = cu.customer_name;
			String comment_time = cu.comment_time;
			String comment_content = cu.comment_content;
			SpannableString span_comment_content = cu.span_comment_content;
//			String customer_name = (String) comment_list.get(position).get(CommentUtil.customername);
//			int customer_id = (Integer) comment_list.get(position).get(CommentUtil.customerid);
//			int comment_id = (Integer)comment_list.get(position).get(CommentUtil.commentid);
//			String sec_customer_name = (String)comment_list.get(position).get(CommentUtil.sec_commentname);
//			String sec_content = (String)comment_list.get(position).get(CommentUtil.sec_content);
//			String thir_customer_name = (String)comment_list.get(position).get(CommentUtil.thir_commentname);
//			String thir_content = (String)comment_list.get(position).get(CommentUtil.thir_content);
//			String time = (String)comment_list.get(position).get(CommentUtil.time);
//			String sec_time = (String) comment_list.get(position).get(CommentUtil.sec_time);
//			String thir_time = (String) comment_list.get(position).get(CommentUtil.thir_time);
//			String content = (String)comment_list.get(position).get(CommentUtil.content);
		
			customer_name_tv.setText(customer_name);
			date_tv.setText(comment_time);
			comment_detail_tv.setClickable(true);
			//comment_detail_tv.setFocusable(false);
			comment_detail_tv.setMovementMethod(LinkMovementMethod.getInstance());  
			//cu.setSpannableCommentContent(comment_detail_tv);
			OnShowMoreListener listener = new OnShowMoreListener(){

				@Override
				public void showMore() {
					// TODO Auto-generated method stub
					//v = comment_detail_tv;
					comment_list_adapter.notifyDataSetChanged();
					
				}
				
			};
			//comment_detail_tv.setText(span_comment_content);
			
			cu.setSpannableCommentContent(comment_detail_tv,listener);
			
			return convertView;
		}
    	
    }
    
    
    private class LoadingCommentThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			comment_list_url = Url.composeCommentListUrl(article_type, article_id, comment_id);
			Log.i("comment_list_url", comment_list_url);
				URL url;
				try {
					url = new URL(comment_list_url);
					URLConnection conn = url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					List<CommentUtil> temp = null;
					temp = json.getListItems(is);
					if(temp != null)
					{
						//comment_list = temp;
						Collections.sort(temp);
						comment_list.addAll(0, temp);
						handler.sendEmptyMessage(LOADING_COMMENT_SUCCEED);
						Log.i("LoadingInfo", "加载评论成功");
						
					}
					else
					{
						handler.sendEmptyMessage(LOADING_COMMENT_FAIL);
					}
					
					
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			
		}
    	
    };
    
    

    
    
    private Handler handler = new Handler()
    {
    	@Override
    	public void handleMessage(Message msg)
    	{
    		switch(msg.what)
    		{
    		case LOADING_COMMENT_SUCCEED:
    			comment_list_adapter.notifyDataSetChanged();
    			comment_list_view.setAdapter(comment_list_adapter);
    			break;
    		case LOADING_COMMENT_FAIL:
    			
    			break;
    		}
    	}
    	
    };
    
    
    

    
    
    

    

}
