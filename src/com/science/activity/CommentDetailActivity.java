package com.science.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.example.science.R;
import com.example.science.R.layout;
import com.example.science.R.menu;
import com.science.interfaces.OnShowMoreListener;
import com.science.json.JsonCommentListHandler;
import com.science.services.FunctionManage;
import com.science.services.MyApplication;
import com.science.util.CommentUtil;
import com.science.util.DefaultUtil;
import com.science.util.Url;
import com.science.view.MyImageButton;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class CommentDetailActivity extends Activity {

	private ListView comment_list_view;
	private int comment_num;
	private CommentListAdapter comment_list_adapter;
	private String comment_list_url;
	private String comment_theme;//评论的标题
	private int article_type;//表明评论的类型
	private int article_id;//表明评论项目或论文或热点的id号
	private int max_id;//获取小于该id的评论
	private int comment_id;
	private int root_id;
	private int mark;
	//private List<Map<String,Object>> comment_list;
	private List<CommentUtil> comment_list;
	private JsonCommentListHandler json;
	private final int LOADING_COMMENT_SUCCEED = 0;
	private final int LOADING_COMMENT_FAIL = 1;
	private final int UPLOAD_COMMENT_SUCCEED = 2;
	private final int UPLOAD_COMMENT_FAIL = 3;
	private final int DELETE_COMMENT_SUCCEED = 4;
	private final int DELETE_COMMENT_FAIL = 5;
	
	private final int RELEASE_STATE = 0;//发布状态
	private final int REPLY_STATE = 1;//回复状态
	private final int IDLE_STATE = -1;//表示空闲状态
	private final int SELF = -1;
	private MyImageButton back_btn;
	private EditText input_box;
	private MyImageButton send_btn;
	private int comment_state;
	private String comment_content;
	
	private MyApplication application;
	private FunctionManage fm;
    private String comment_time;
  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.comment_list);
        initVariable();
        initViews();
        setOnClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_detail, menu);
        return true;
    }
    
    public void initVariable()
    {
    	application = MyApplication.getInstance();
    	fm = new FunctionManage(this);
    	comment_num = 5;
    	comment_list_adapter = new CommentListAdapter();
    	comment_list = new ArrayList<CommentUtil>();
    	json = new JsonCommentListHandler();
    	Intent intent = this.getIntent();
    	comment_theme = intent.getStringExtra("theme");
    	article_type = intent.getIntExtra("article_type", DefaultUtil.INAVAILABLE);
    	article_id = intent.getIntExtra("article_id", DefaultUtil.INAVAILABLE);
    	max_id = DefaultUtil.MAX_VALUE;
    	comment_state = IDLE_STATE;//表示
    	comment_content = null;
    	
    	
    	comment_id = SELF;
    	root_id = SELF;
    	mark = 0;
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
    	send_btn = (MyImageButton)findViewById(R.id.send);
    	
    	send_btn.setEnabled(false);//一开始发送按钮为disable
    	input_box = (EditText)findViewById(R.id.comment_input_box);
    	//comment_list_view.setAdapter(comment_list_adapter);
    	TextView tv = (TextView) findViewById(R.id.comment_theme_title);
    	if(comment_theme != null)
    	tv.setText(comment_theme);
    	back_btn = (MyImageButton)findViewById(R.id.go_back);

    	
    	
    }
    
    
    
    
    public void setOnClickListener(){
    	back_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommentDetailActivity.this.finish();
			}
    		
    	});
    	
    	input_box.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
				{
					
				}
				else
				{
					input_box.setHint("我来说两句");
				}
			}
    		
    	});
    	
    	
    	input_box.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if(application.IsLogin())
				{
				   comment_state = RELEASE_STATE;

			    }
				
				 if(!input_box.getText().toString().isEmpty())//判断input_box是否为空
				 {
					   send_btn.setEnabled(true);
					   send_btn.setImageResource(R.drawable.send_enable);
				 }
				 else
				 {
					   send_btn.setEnabled(false);
					   send_btn.setImageResource(R.drawable.send_disable);
				 }
			}
    		
    	});

//    	input_box.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				if(application.IsLogin())
//				{
//				   comment_state = RELEASE_STATE;
//
//			    }
//				
//				 if(!input_box.getText().toString().isEmpty())//判断input_box是否为空
//				 {
//					   send_btn.setEnabled(true);
//					   send_btn.setImageResource(R.drawable.send_enable);
//				 }
//				 else
//				 {
//					   send_btn.setEnabled(false);
//					   send_btn.setImageResource(R.drawable.send_disable);
//				 }
//				
//			}
//    	});
//    	
    	
    	send_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!application.IsLogin())//如果未未登录，须事先登录
				{
					fm.Login();
				}
				
				
				comment_content = input_box.getText().toString();
				if(!comment_content.isEmpty())
				{
				      
					new Thread(upload_comment_thread).start();
					
				}
					
					

				
				
			}
    		
    	});
    	
    	//releaseComment(SELF);
    	
    	
    	
    	
    	
    	

    }
    
    
    /**
     * 
     * @param index表示回复评论列表的第几个,为-1表示自己发表评论
     */
    public void replyComment(int index)
    {
    	if(index == SELF)
    	{
    		return;
    	}
    	comment_id = comment_list.get(index).comment_id;
    	root_id = comment_list.get(index).customer_id;
    	mark = comment_list.get(index).mark - 1;
        String who = comment_list.get(index).customer_name;
    	
    	if(input_box.requestFocus())
    	{
    	input_box.setHint("回复" + who + ":");
    	InputMethodManager imm = (InputMethodManager)input_box.getContext().
    			                  getSystemService(Context.INPUT_METHOD_SERVICE);
    	imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    	if(application.IsLogin())
    	{
    		comment_state = REPLY_STATE;
    	}
    	}
    }
    
    
    /***
     * 
     * @param index表示回复评论列表的第几个,为-1表示自己发表评论
     */
    public void deleteComment(int index)
    {
    	comment_id = comment_list.get(index).comment_id;
    	mark = comment_list.get(index).mark - 1;
    	new Thread(delete_comment_thread).start();
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
			
			TextView customer_name_tv = (TextView) v.findViewById(R.id.customer_name_tv);
			TextView date_tv = (TextView) v.findViewById(R.id.date_tv);
			final TextView comment_detail_tv = (TextView) v.findViewById(R.id.comment_detail_tv);
			
			CommentUtil cu = comment_list.get(position);
			if(cu == null)
				return null;
			String customer_name = cu.customer_name;
			String comment_time = cu.comment_time;

			customer_name_tv.setText(customer_name);
			date_tv.setText(comment_time);
			comment_detail_tv.setClickable(true);
			comment_detail_tv.setMovementMethod(LinkMovementMethod.getInstance());  
			OnShowMoreListener listener = new OnShowMoreListener(){

				@Override
				public void showMore() {
					// TODO Auto-generated method stub
					comment_list_adapter.notifyDataSetChanged();
					
				}
				
			};
			
			cu.setSpannableCommentContent(comment_detail_tv,listener);
			
			final int index = position;
			//设置回复事件
			View rv = v.findViewById(R.id.comment_replay);
			rv.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(application.IsLogin())
					{
					   replyComment(index);
					}
					else
					{
						fm.Login();
					}
				}
				
			});
			
			
			//设置删除事件
			TextView tv = (TextView) v.findViewById(R.id.delete_reply);
			if(application.user_name.equals(customer_name))
			{
				tv.setVisibility(View.VISIBLE);
				tv.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						deleteComment(index);
					}
					
				});
			}
			else
				tv.setVisibility(View.GONE);
			
			
			
			
			//设置点赞的动画
			final Animation like_anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.comment_like_anim);
			final MyImageButton  like_btn = (MyImageButton) v.findViewById(R.id.comment_like);
			like_anim.setAnimationListener(new AnimationListener(){

				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub
					if(!comment_list.get(index).like)
						like_btn.setBackground(getResources().getDrawable(R.drawable.icon_like1));
					else
					{
						like_btn.setBackground(getResources().getDrawable(R.drawable.icon_like2));
					}
				}
				
			});
			
			like_btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
	                if(!application.IsLogin())
	                {
	                	fm.Login();
	                }
	                else
	                {
					if(!comment_list.get(index).like)
						comment_list.get(index).like = true;
					else
						comment_list.get(index).like = false;
					like_btn.startAnimation(like_anim);
	                }
	                
				}
				
			});
			
			
			
			return convertView;
		}
    	
    }
    
    
    private class LoadingCommentThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			comment_list_url = Url.composeCommentListUrl(article_type, article_id, max_id);
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
						comment_list = temp;
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
    
    
   private Runnable delete_comment_thread = new Runnable()
   {

	   
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		String str_url = Url.composeDeleteCommentUrl(comment_id);
		try {
			URL url = new URL(str_url);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			boolean state = getResultState(is);
			if(state)
				handler.sendEmptyMessage(DELETE_COMMENT_SUCCEED);
			else
				handler.sendEmptyMessage(DELETE_COMMENT_FAIL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	   
   };
    
    
    
    
    private Runnable upload_comment_thread = new Runnable()
    {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String str_url = Url.compseReleaseCommentUrl(comment_id,root_id, 
                    article_type, article_id, comment_content);
			Log.i("uploading_comment_url", str_url);
				URL url;
				try {
					url = new URL(str_url);
					URLConnection conn = url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					boolean state = getResultState(is);
					if(state)
						handler.sendEmptyMessage(UPLOAD_COMMENT_SUCCEED);
					else
						handler.sendEmptyMessage(UPLOAD_COMMENT_FAIL);
						
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
    			
    		case UPLOAD_COMMENT_SUCCEED:
    			Toast.makeText(getBaseContext(), "评论成功", Toast.LENGTH_SHORT).show();
    			input_box.setText("");
    			input_box.setHint("我来说两句");
    			if(comment_list != null)
    			    comment_list.clear();
    			comment_list_adapter.notifyDataSetChanged();
    			send_btn.setEnabled(false);
    			send_btn.setImageResource(R.drawable.send_disable);
    			requestData();
    			break;
    		case UPLOAD_COMMENT_FAIL:
    			Toast.makeText(getBaseContext(), "评论失败", Toast.LENGTH_SHORT).show();
    			if(comment_list != null)
    			    comment_list.clear();
    			comment_list_adapter.notifyDataSetChanged();
    			break;
    			
    		case DELETE_COMMENT_SUCCEED:
    			Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_SHORT).show();
    			if(comment_list != null)
    			    comment_list.clear();
    			comment_list_adapter.notifyDataSetChanged();
    			requestData();
    			break;
    			
    		case DELETE_COMMENT_FAIL:
    			Toast.makeText(getBaseContext(), "删除失败", Toast.LENGTH_SHORT).show();
    			break;
    		}
    	}
    	
    };
    
    
    
    public boolean getResultState(InputStream is)
    {
    	
		Reader reader;
		try {
			reader = new InputStreamReader(is,"utf-8");
			BufferedReader buf_reader = new BufferedReader(reader);
			String str = null;
			StringBuffer sb = new StringBuffer();
			while((str = buf_reader.readLine()) != null)
			{
				sb.append(str);
			}
			
			String str_temp = sb.toString();
			int a = str_temp.indexOf("{");
			str_temp = str_temp.substring(a);
			JSONObject obj = new JSONObject(str_temp);
			String code_str = obj.getString("code");
			if(code_str.equals(DefaultUtil.RESULT_OK))
				return true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;

    }

 
    
    
@Override
public boolean dispatchTouchEvent(MotionEvent event){
	
	if(event.getAction() == MotionEvent.ACTION_DOWN){
		
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	View v = getCurrentFocus();
    	//if(v.getId() != R.id.comment_input_box)
    	//{
    	imm.hideSoftInputFromWindow(v.getWindowToken(),  InputMethodManager.HIDE_NOT_ALWAYS);
    	input_box.clearFocus();
    	if(input_box.getText().toString().isEmpty())
    	{
    		input_box.setHint("我来说两句");
    	    if(comment_state == REPLY_STATE)
    	    {
    	    	comment_state = RELEASE_STATE;
    	    }
    	    
    	}
    	//}
    	
	}
	return super.dispatchTouchEvent(event);
}
    


}
