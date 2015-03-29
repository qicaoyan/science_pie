package com.science.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.science.R;
import com.science.activity.CommonContentActivity;
import com.science.activity.DocumentExpressActivity;
import com.science.interfaces.OnLoadingStateChangedListener;
import com.science.json.JsonDcumentListHandler;
import com.science.services.MyApplication;
import com.science.util.DefaultUtil;
import com.science.util.Url;
import com.science.view.MyHeader;
import com.science.view.MyImageButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ValidFragment")
public class DocumentExpressFragment extends Fragment  implements OnLoadingStateChangedListener{

	
	private Activity activity;

	private ListView  doc_list_view;
	
	
	/*与数据解析有关*/
	private JsonDcumentListHandler json = null;
	private String str_url = null;
	private List<Map<String,String>> doc_list;
    private MyApplication application;
    private DocListAdapter doc_list_adapter;
    private View view;
    
    private int type;
    private String[] keywords;
    
    
    private  final int UPDATE_TAG_VIEW = 0x00; 
    private  final int UPDATE_DOC_LIST = 0x01;
    private  final int UPDATE_DOC_LIST_FAIL = 0x02;
    
    
    
    public DocumentExpressFragment(){
    	
    }
    
    public DocumentExpressFragment(int type,String[] keywords){
    	
    	this.type = type;
    	this.keywords = keywords;
    	
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
    	
    	this.activity = this.getActivity();
    	view = inflater.inflate(R.layout.document_fragment_list, container, false);  
       
        doc_list = new ArrayList<Map<String,String>>();
        doc_list_view = (ListView) view.findViewById(R.id.doc_list_view);
        doc_list_adapter = new DocListAdapter();
        doc_list_view.setAdapter(doc_list_adapter);
        doc_list_view.setOnItemClickListener(doc_item_click_listener);
        updateDocListFragment();
        
        application = MyApplication.getInstance();
        //Toast.makeText(activity, "type", Toast.LENGTH_SHORT).show();
        return view;
    }
    
    
 
    
    
    
    /**
     * 
     * @param type  表明自己是属于哪一类型的文献
     * @param keywords  表明关键词序列
     */
    public void updateDocListFragment(){
    	
       
    	String doc_keywords_str = "";
    	for(int i = 0;i < keywords.length;i++)
    	{
    		doc_keywords_str += keywords[i];
    	}
    	str_url = Url.composeDocListUrl(DefaultUtil.MAX_VALUE, type, doc_keywords_str);
    	requestData();
    }
    
    
    
    

	
	private void requestData()
	{
		
        MyThread thread = new MyThread(0);
        new Thread(thread).start(); 
	}
	

	
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case UPDATE_DOC_LIST:
				onLoadingSucceed();
				doc_list_adapter.notifyDataSetChanged();
				doc_list_view.setAdapter(doc_list_adapter);
				break;
			case UPDATE_DOC_LIST_FAIL:
				onLoadingFail();
				break;
				
			}
		}
	};

	
	
	
	private class MyThread implements Runnable
	{
		private int index = 0;
		
		public MyThread(int temp)
		{
			index = temp;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			URL url;
			try{
				
				
				url = new URL(str_url);
				URLConnection con = url.openConnection();
				con.connect();
				InputStream input = con.getInputStream();
				List<Map<String,String>> temp = null;
				json = new JsonDcumentListHandler();
				temp = json.getListItems(input);
				if(temp != null)
				{
					
					doc_list = temp;
					handler.sendEmptyMessage(UPDATE_DOC_LIST);
				}else
				{
					handler.sendEmptyMessage(UPDATE_DOC_LIST_FAIL);
				}
			}catch(MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	private class DocListAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(doc_list != null)
			return doc_list.size();
			else
			return 0;
		}

		@Override
		public Object getItem(int item) {
			// TODO Auto-generated method stub
			return item;
		}

		@Override
		public long getItemId(int id) {
			// TODO Auto-generated method stub
			return id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.doc_list_item, null);
			TextView doc_title_text = (TextView) view.findViewById(R.id.doc_title);
			String title = doc_list.get(position).get("title");
			doc_title_text.setText(title);
			if(convertView == null)
			{
				convertView = view;
			}
			return convertView;
		}
		
	}
	
	
	
	//为ListView每一个item设置OnClick事件
	private OnItemClickListener doc_item_click_listener = new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			String url = application.ComposeToken(Url.DocumentDetailBase) + "&id=" + doc_list.get(position).get("id");
			
			
			Intent intent = new Intent();//用以传递数据
			intent.setClass(activity, CommonContentActivity.class);
			intent.putExtra("url", application.ComposeToken(url));
			intent.putExtra("act_class", "document");
			intent.putExtra("theme",doc_list.get(position).get("title"));
			startActivity(intent);
		}
		
	};
	
	
	

	
	

	
	
	

	//设置加载事件
	@Override
	public void onLoadingSucceed() {
		// TODO Auto-generated method stub
		view.findViewById(R.id.doc_loading_mask_layout).setVisibility(View.GONE);
		//Toast.makeText(activity, "加载成功", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLoadingFail() {
		// TODO Auto-generated method stub
		view.findViewById(R.id.doc_loading_mask_layout).setVisibility(View.GONE);
		//Toast.makeText(activity, "加载失败！", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLoadingBegin() {
		// TODO Auto-generated method stub
		view.findViewById(R.id.doc_loading_mask_layout).setVisibility(View.VISIBLE);
	}


	@Override
	public void setOnLoadingStateChangedListener(OnLoadingStateChangedListener l) {
		// TODO Auto-generated method stub
		l = this;
	}
	
	
	
	
	
	@Override
	public void onResume()
	{
        super.onResume();

	}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
}
