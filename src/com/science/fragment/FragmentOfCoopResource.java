package com.science.fragment;


import java.io.File;
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


import com.science.json.JsonProgramListHandler;
import com.science.json.JsonProjectSelectListHandler;
import com.science.model.FirstClassItem;
import com.science.model.SecondClassItem;
import com.science.services.MyApplication;
import com.science.util.Url;
import com.science.view.MyHeader;
import com.science.view.MyImageButton;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.RotateAnimation;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;




public class FragmentOfCoopResource extends Fragment{
	
	private View view;
	private MyApplication myApplication=null;

	
	
	private LinearLayout main=null;
	private String arrowState="";
	//private ProgressBar projectprogramProgressBar=null;
	private View moreView=null;
	private String strUrl=null;
	private String str_url_proj_src;

	private MyImageButton fold_btn1;
	private MyImageButton fold_btn2;
	private MyImageButton[] proj_btns;
	private WindowManager wm;
	private Context context;

	private MyImageButton mTabResource;  
	private MyImageButton mTabPaper;  
	private FragmentOfCoopResource mResource;  
	private FragmentOfCoopPaper mPaper;
	
	
	private int screen_width;
	private int screen_height;
	
	private ListView  coop_list_view;
	private ListView  coop_all_list_view;
	private ListView  coop_all_next_list_view;
	private ListView  coop_area_list_view;
	private View      coop_all_pop_view;
	private View      coop_area_pop_view;
	private View      coop_all_btn;
	private View      coop_area_btn;
	private View      frag_source_view;
	private MyHeader  proj_apply_header;
	private PopupWindow  coop_all_pop_win;
	private PopupWindow   coop_area_pop_win;
	private CoopListAdapter coop_list_adapter;
	private CoopAllAdapter first_adapter;
	private CoopAllNextAdapter second_adapter;
    private CoopAreaAdapter coop_area_adapter;
	private View proj_more_view;
	private JsonProgramListHandler json = null;
	private JsonProjectSelectListHandler json_select = null;
	private MyApplication application;
	private static final int UPDATE_TAG_VIEW = 0x00; 
	private static final int UPDATE_TAG_VIEW2 = 0x01; 
    private static final int UPDATE_COOP_LIST = 0x02;
    
	private List<Map<String,String>> coop_list;
	private List<FirstClassItem> coop_all_list;
	private List<SecondClassItem> coop_all_next_list;
    private List<FirstClassItem> coop_area_list;
    private List<SecondClassItem>coop_area_next_list;
    private Activity activity;
	@Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	           Bundle savedInstanceState)  
	    {  
		    this.activity = this.getActivity();
	        view = inflater.inflate(R.layout.frag_resource, container, false);  


	        initVariable();
	        initView();
	        setOnClickListener();
	        return view;
	    }  

	public View getFragmentView(){
		
		return view;
	}
	
	
	
	
	private void initVariable()
	{
		
		
		moreView = activity.getLayoutInflater().inflate(R.layout.moredata, null);
		strUrl=Url.ProjectListBase;
		str_url_proj_src = Url.ProjectUsual;
		context = activity;
		json = new JsonProgramListHandler();
		json_select = new JsonProjectSelectListHandler();
		application = (MyApplication) activity.getApplication();
		coop_list = null;
		screen_width = 0;
		screen_height = 0;

		
		 coop_all_list = new ArrayList<FirstClassItem>();
	        //1
	        ArrayList<SecondClassItem> coop_all_next_list1 = new ArrayList<SecondClassItem>();
	        coop_all_list.add(new FirstClassItem(1, "人力", coop_all_next_list1));
	        //2
	        ArrayList<SecondClassItem> coop_all_next_list2 = new ArrayList<SecondClassItem>();
	        coop_all_next_list2.add(new SecondClassItem(200, "专业设备"));
	        coop_all_next_list2.add(new SecondClassItem(201, "通用设备"));
	        coop_all_list.add(new FirstClassItem(2, "设备", coop_all_next_list2));
	        
	        
	        coop_area_list = new ArrayList<FirstClassItem>();
	        coop_area_list.add(new FirstClassItem(1,"全部",null));
	        coop_area_list.add(new FirstClassItem(2,"北京",null));
	        coop_area_list.add(new FirstClassItem(3,"上海",null));
	        coop_area_list.add(new FirstClassItem(3,"广州",null));
	        
	        
	        
	        /*设置coop_list_adapter*/
	        coop_list_adapter = new CoopListAdapter();
	}
	
	
	
private void requestData(){
	MyThread thread = new MyThread(0);
	new Thread(thread).start();
}
	private void initView()
	{
		wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		screen_width = wm.getDefaultDisplay().getWidth();
		screen_height = wm.getDefaultDisplay().getHeight();
		
		
		//findViewById
		frag_source_view = activity.getLayoutInflater().from(activity).inflate(R.layout.frag_resource, null);
		
		
		coop_all_btn = (LinearLayout)view.findViewById(R.id.coop_all_layout);
		coop_area_btn = (LinearLayout)view.findViewById(R.id.coop_area_layout);
		//proj_apply_header = (MyHeader) findViewById(R.id.proj_apply_header);
		
		//设置proj_apply的header
//		proj_apply_header.SetHeaderText("项目申请");
//		String[] header_btn_strs = {"热门推荐","即将到期","项目解读"};
//		proj_apply_header.SetHeaderButtons(header_btn_strs);
		//View frag_source_view = getLayoutInflater().from(this).inflate(R.layout.frag_resource, null);
		coop_list_view = (ListView)frag_source_view.findViewById(R.id.frag_resource_list_view);
		Log.i("test_coop", "~~~~~~~~~~~");
		coop_list_view.setCacheColorHint(Color.argb(255, 0, 0, 0));
		Log.i("test_coop", "~~~~000000~~~~");
		coop_list_view.setSelector(R.drawable.list_item_selector);
		Log.i("test_coop", "~~~~1111111~~~~");

		coop_list_view.setOnItemClickListener(coop_item_click_listener);
		/*加载更多按钮*/
		//proj_more_view = (LinearLayout) getLayoutInflater().inflate(R.layout.listmoredata, null);
		coop_all_pop_view = activity.getLayoutInflater().inflate(R.layout.pop_view_for_proj_src, null);
		coop_area_pop_view = activity.getLayoutInflater().inflate(R.layout.pop_view_for_proj_type, null);
		coop_all_list_view = (ListView) coop_all_pop_view.findViewById(R.id.proj_src_list_view);
		Log.i("test_coop", "~~~~2222222~~~~");
		first_adapter = new CoopAllAdapter(activity,coop_all_list);
		coop_all_list_view.setAdapter(first_adapter);
		
	     coop_all_next_list_view = (ListView) coop_all_pop_view.findViewById(R.id.proj_src_next_list_view);
	     coop_all_next_list = new ArrayList<SecondClassItem>();
	     coop_all_next_list.addAll(coop_all_list.get(0).getSecondList());
	     
	     //设置coop_all_list_view和coop_all_new_list_view的比例
	     LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	     params.weight = 1.0f;
	     coop_all_list_view.setLayoutParams(params);
	     coop_all_next_list_view.setLayoutParams(params);
	     
	     
	     second_adapter = new CoopAllNextAdapter(activity, coop_all_next_list);
	     coop_all_next_list_view.setAdapter(second_adapter);
	     Log.i("test_coop", "~~~~333333~~~~");
	     
	     //proj_type处理
	     coop_area_list_view = (ListView)coop_area_pop_view.findViewById(R.id.proj_type_list_view);
	     coop_area_adapter = new CoopAreaAdapter(activity,coop_area_list);
	     coop_area_list_view.setAdapter(coop_area_adapter);
	   //左侧ListView点击事件
	     coop_all_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                //二级数据
	                List<SecondClassItem> list2 = coop_all_list.get(position).getSecondList();
	                
                    String selectedName = coop_all_list.get(position).getName();
                    str_url_proj_src  = application.ComposeToken(str_url_proj_src);
                    try {
						str_url_proj_src += "&projSource1=" + URLEncoder.encode(selectedName,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                //如果没有二级类，则直接跳转
	                if (list2 == null || list2.size() == 0) {
	                    coop_all_pop_win.dismiss();

	                    int firstId = coop_all_list.get(position).getId();

	                    handleResult(firstId, -1, selectedName);
	                    return;
	                }

	                CoopAllAdapter adapter = (CoopAllAdapter) (parent.getAdapter());
	                //如果上次点击的就是这一个item，则不进行任何操作
	                if (adapter.getSelectedPosition() == position){
	                    return;
	                }

	                //根据左侧一级分类选中情况，更新背景色
	                adapter.setSelectedPosition(position);
	                adapter.notifyDataSetChanged();

	                //显示右侧二级分类
	                updateSecondListView(list2, second_adapter);
	            }
	        });

	     
	     
	   //右侧ListView点击事件
	     coop_all_next_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                //关闭popupWindow，显示用户选择的分类
	                coop_all_pop_win.dismiss();

	                int firstPosition = first_adapter.getSelectedPosition();
	                int firstId = coop_all_list.get(firstPosition).getId();
	                int secondId = coop_all_list.get(firstPosition).getSecondList().get(position).getId();
	                String selectedName = coop_all_list.get(firstPosition).getSecondList().get(position)
	                        .getName();
	                try {
						str_url_proj_src += "&projSource2=" + URLEncoder.encode(selectedName,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	                if(coop_list != null)
	                coop_list.clear();
	                coop_list_adapter.notifyDataSetChanged();
	                //coop_list_view.removeFooterView(proj_more_view);
	                second_adapter.setSelectedPosition(position);
	                second_adapter.notifyDataSetChanged();
	                
	                GetProjectThread get_proj_thread = new GetProjectThread(0);
	                new Thread(get_proj_thread).start();
	                handleResult(firstId, secondId, selectedName);
	            }
	        });
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
		   //项目类型ListView点击事件
	     coop_area_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                //关闭popupWindow，显示用户选择的分类
	                coop_area_pop_win.dismiss();             
	                String selectedName = coop_area_list.get(position).getName();
	                
	                try {
						str_url_proj_src += "&projType=" + URLEncoder.encode(selectedName,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	                if(coop_list != null)
	                coop_list.clear();
	                coop_list_adapter.notifyDataSetChanged();
	                //coop_list_view.removeFooterView(proj_more_view);

	                
	                coop_area_adapter.notifyDataSetChanged();
	                coop_area_adapter.setSelectedPosition(position);
	                GetProjectThread get_proj_thread = new GetProjectThread(0);
	                new Thread(get_proj_thread).start();
	               // handleResult(firstId, secondId, selectedName);
	            }
	        });
	     
	     
	     
	     

	    
	     
		//项目来源popupwindow
		coop_all_pop_win = new PopupWindow(coop_all_pop_view,screen_width/2,LayoutParams.WRAP_CONTENT);
		coop_all_pop_win.setOutsideTouchable(true); 
		
		coop_all_pop_win.setBackgroundDrawable(new BitmapDrawable());
		coop_all_pop_win.setFocusable(true);
		coop_all_pop_win.setOnDismissListener(new PopupWindow.OnDismissListener(){

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				coop_all_list_view.setSelection(0);
				coop_all_next_list_view.setSelection(0);
				View vv = view.findViewById(R.id.coop_all_arrow);
				RotateAnimation rot_anim = new RotateAnimation(180,0,(float) (vv.getWidth()*0.5),(float) (vv.getHeight()*0.5));
				rot_anim.setFillAfter(true);
				rot_anim.setDuration(100);
				vv.startAnimation(rot_anim);
			}
			
		});
	
		
		
		//项目类型popupwindow
		coop_area_pop_win = new PopupWindow(coop_area_pop_view,screen_width/2,LayoutParams.WRAP_CONTENT);
		coop_area_pop_win.setOutsideTouchable(true); 
		
		coop_area_pop_win.setBackgroundDrawable(new BitmapDrawable());
		coop_area_pop_win.setFocusable(true);
		coop_area_pop_win.setOnDismissListener(new PopupWindow.OnDismissListener(){

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				coop_area_list_view.setSelection(0);
				View vv = view.findViewById(R.id.coop_area_arrow);
				RotateAnimation rot_anim = new RotateAnimation(180,0,(float) (vv.getWidth()*0.5),(float) (vv.getHeight()*0.5));
				rot_anim.setFillAfter(true);
				rot_anim.setDuration(100);
				vv.startAnimation(rot_anim);
			}
			
		});
		

	}
	
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
					strUrl = application.ComposeToken(strUrl);
					strUrl +="&";
					//strUrl += "&date=2014-1-1" + "&class=" + URLEncoder.encode("企业","utf-8");
					url = new URL(strUrl);
					URLConnection con = url.openConnection();
					con.connect();
					InputStream input = con.getInputStream();
					List<Map<String,String>> temp = null;
					temp = json.getListItems(input);
					Log.i("strUrl", strUrl);
					if(temp != null)
					{
						coop_list = temp;
						Log.i("temp?==0","temp!=0");
						handler.sendEmptyMessage(UPDATE_COOP_LIST);
						
					}else
					{
						Log.i("temp?==0","temp==0");
						handler.sendEmptyMessage(3);
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

	  
	  
	  private class GetProjectThread implements Runnable
		{
			private int index = 0;
			
			public GetProjectThread(int temp)
			{
				index = temp;
			}

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				URL url;
				try{

					url = new URL(str_url_proj_src);
					URLConnection con = url.openConnection();
					con.connect();
					InputStream input = con.getInputStream();
					List<Map<String,String>> temp = null;
					temp = json_select.getListItems(input);
					
					if(temp != null)
					{
						coop_list = temp;
						handler.sendEmptyMessage(6);
						Log.i("temp?==0","temp!=0");
					}else
					{
						handler.sendEmptyMessage(3);
						
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

	 
	  
	  private Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				switch(msg.what)
				{
				  
					
				case UPDATE_COOP_LIST:
					Log.i("proj_apply","here!");
					
					coop_list_view.setAdapter(coop_list_adapter);
					coop_list_view.addFooterView(proj_more_view);
					Log.i("add footer view", "added");
					break;
				case 3:
					
					break;
				case 6:
					
					coop_list_adapter.notifyDataSetChanged();
					coop_list_adapter = new CoopListAdapter();
					coop_list_view.setAdapter(coop_list_adapter);
					coop_list_view.addFooterView(proj_more_view);
					break;
					
				}
			}
		};

	  public void setOnClickListener()
		{

	      //设置头部按钮的点击事件
		   OnClickListener onHeaderButtonClickListener = new OnClickListener()
		   {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			   
		   };	   

		   
	        //设置coop_all_btn点击弹出事件
	        coop_all_btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(activity,"all click",Toast.LENGTH_SHORT).show();
					
					final View vv = (ImageView)view.findViewById(R.id.coop_all_arrow);
					
					Log.i("popupwindow_test", "succeed1");
					if (coop_all_pop_win.isShowing()) {

						coop_all_pop_win.dismiss();		
			        }
					else{
					
						RotateAnimation rot_anim = new RotateAnimation(0,180,(float) (vv.getWidth()*0.5),(float) (vv.getHeight()*0.5));
						rot_anim.setFillAfter(true);
						rot_anim.setDuration(100);
						vv.startAnimation(rot_anim);
						coop_all_pop_win.showAsDropDown((LinearLayout)view.findViewById(R.id.coop_all_layout));
					}
					

				}
	        	
	        });
	        
	        
	        
	        
	        
	        
	        //设置coop_area_btn点击弹出事件
	        coop_area_btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(activity,"area click",Toast.LENGTH_SHORT).show();
					
					final View vv = (ImageView)view.findViewById(R.id.coop_area_arrow);
					
					if (coop_area_pop_win.isShowing()) {

						coop_area_pop_win.dismiss();		
			        }
					else{
					
						RotateAnimation rot_anim = new RotateAnimation(0,180,(float) (vv.getWidth()*0.5),(float) (vv.getHeight()*0.5));
						rot_anim.setFillAfter(true);
						rot_anim.setDuration(100);
						vv.startAnimation(rot_anim);
						coop_area_pop_win.showAsDropDown((LinearLayout)view.findViewById(R.id.coop_area_layout));
					}
					

				}
	        	
	        });
	        
		}
		
		
		

		private class CoopListAdapter extends BaseAdapter
		{

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return coop_list.size();
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
				View view = inflater.inflate(R.layout.proj_list_item, null);
				TextView proj_title_text = (TextView) view.findViewById(R.id.proj_title);
				String title = coop_list.get(position).get("title");
				proj_title_text.setText(title);
				
				TextView proj_start_time = (TextView) view.findViewById(R.id.start_time);
				String start_time = coop_list.get(position).get("startTime");
				proj_start_time.setText(start_time);
				
				TextView proj_end_time = (TextView) view.findViewById(R.id.end_time);
				String end_time = coop_list.get(position).get("endTime");
				proj_end_time.setText(end_time);
				
				if(convertView == null)
				{
					convertView = view;
				}
				return convertView;
			}
	
			
	   public void clearList(List<File> f) {
                int size = f.size();
                if (size > 0) {
                        f.removeAll(f);
                        coop_list_adapter.notifyDataSetChanged();
                }
        }
		}
		
		
		
		
	private OnItemClickListener coop_item_click_listener = new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				String url = application.ComposeToken(Url.ProjectContentBaseUrl) + "&id=" + coop_list.get(position).get("id");
				Log.i("proj_item_url", url);
				Intent intent = new Intent();//用以传递数据
				intent.setClass(activity, CommonContentActivity.class);
				intent.putExtra("url", url);
				intent.putExtra("act_class", "project");
				startActivity(intent);
			}
			
		};
		

		
		
		
		
		private class CoopAllAdapter extends BaseAdapter{
			private Context context;
		    private List<FirstClassItem> list;
			public CoopAllAdapter(Context context, List<FirstClassItem> list) {
		        this.context = context;
		        this.list = list;
		    }
			
			
	    @Override
		public int getCount() {
				// TODO Auto-generated method stub
				return coop_all_list.size();
				
			}

		@Override
		public Object getItem(int item) {
				// TODO Auto-generated method stub
				return item;
			}
		
		
		
		@Override
		public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

		
		
		@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ViewHolder holder;
				LinearLayout layout = new LinearLayout(activity);
				//layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				if (position == selectedPosition){
					ImageView iv = new ImageView(activity);
					iv.setImageResource(R.drawable.icon_green_point);
					layout.addView(iv);
					layout.setBackground(getResources().getDrawable(R.drawable.shape_dark_blue));
				  }else{
		            layout.setBackground(getResources().getDrawable(R.drawable.shape_light_blue));
				}
				layout.setGravity(Gravity.CENTER);
				TextView tv = new TextView(activity);
				tv.setTextColor(getResources().getColor(R.color.white));
				tv.setTextSize(15);
				tv.setText(coop_all_list.get(position).getName());
				tv.setPadding(5, 20, 0, 20);
				layout.addView(tv);
				convertView = layout;
				
				
				if(convertView == null)
				convertView = tv;
				return convertView;
			}
			
		
		
	private int selectedPosition = 0;

			    public void setSelectedPosition(int selectedPosition) {
			        this.selectedPosition = selectedPosition;
			    }

			    public int getSelectedPosition() {
			        return selectedPosition;
			    }
			    private class ViewHolder {
			        TextView nameTV;
			    }
		}
		
//定义若干变量
	   private class CoopAllNextAdapter extends BaseAdapter{
	   private Context context;
       private List<SecondClassItem> list;
       private int selectedPosition = 0;
       
       
       public CoopAllNextAdapter(Context context, List<SecondClassItem> list){
            this.context = context;
            this.list = list;
       }
		
       
       
	   @Override
		public int getCount() {
				// TODO Auto-generated method stub
				return coop_all_next_list.size();
				
			}

	   
		@Override
		public Object getItem(int item) {
				// TODO Auto-generated method stub
				return item;
		}

		
		@Override
		public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
		}

		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LinearLayout layout = new LinearLayout(activity);
				//layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				
				if (position == selectedPosition){
					ImageView iv = new ImageView(activity);
					iv.setImageResource(R.drawable.icon_green_point);
					iv.setPadding(10, 10, 0, 10);
					layout.addView(iv);
					layout.setBackground(getResources().getDrawable(R.color.dark_blue));
				  }else{
		            layout.setBackground(getResources().getDrawable(R.color.light_blue));
				}
				//layout.setBackground(getResources().getDrawable(R.color.light_blue));
				layout.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
				TextView tv = new TextView(activity);
				tv.setTextColor(getResources().getColor(R.color.white));
				tv.setTextSize(15);
				tv.setText(coop_all_next_list.get(position).getName());
				if(position == selectedPosition)
					tv.setPadding(10, 10, 0, 10);
				else
					tv.setPadding(50, 10, 0, 10);
				
				layout.addView(tv);
				convertView = layout;
				return convertView;
			}
			
			
		

		public void setSelectedPosition(int selectedPosition) {
		        this.selectedPosition = selectedPosition;
		 }

		
		public int getSelectedPosition() {
		        return selectedPosition;
		   }
		
		
		
		private class ViewHolder {
		        TextView nameTV;
		    }
			
		}



 private class CoopAreaAdapter extends BaseAdapter
 {
	   private Context context;
       private List<FirstClassItem> list;

       public CoopAreaAdapter(Context context, List<FirstClassItem> list){
            this.context = context;
            this.list = list;
     }
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return coop_area_list.size();
				
			}

			@Override
			public Object getItem(int item) {
				// TODO Auto-generated method stub
				return item;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LinearLayout layout = new LinearLayout(activity);
				//layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				
				if (position == selectedPosition){
					ImageView iv = new ImageView(activity);
					iv.setImageResource(R.drawable.icon_green_point);
					iv.setPadding(40, 10, 0, 10);
					layout.addView(iv);
					
					layout.setBackground(getResources().getDrawable(R.drawable.shape_dark_blue));
				  }else{
		            layout.setBackground(getResources().getDrawable(R.drawable.shape_light_blue));
				}
				//layout.setBackground(getResources().getDrawable(R.color.light_blue));
				layout.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
				TextView tv = new TextView(activity);
				tv.setTextColor(getResources().getColor(R.color.white));
				tv.setTextSize(15);
				tv.setText(coop_area_list.get(position).getName());
				if(position == selectedPosition)
					tv.setPadding(10, 10, 0, 10);
				else
					tv.setPadding(50, 10, 0, 10);
				
				layout.addView(tv);
				convertView = layout;
				return convertView;
			}
			
			
			private int selectedPosition = 0;

		    public void setSelectedPosition(int selectedPosition) {
		        this.selectedPosition = selectedPosition;
		    }

		    public int getSelectedPosition() {
		        return selectedPosition;
		    }
		    private class ViewHolder {
		        TextView nameTV;
		    }
 }

//刷新右侧ListView
    private void updateSecondListView(List<SecondClassItem> list2,
                                  CoopAllNextAdapter secondAdapter) {
    	coop_all_next_list.clear();
    	coop_all_next_list.addAll(list2);
        secondAdapter.notifyDataSetChanged();
    }

    private void handleResult(int firstId, int secondId, String selectedName){
        String text = "first id:" + firstId + ",second id:" + secondId;
        Toast.makeText(this.getActivity(),text,Toast.LENGTH_SHORT).show();
  
    }




	
	
	
}
