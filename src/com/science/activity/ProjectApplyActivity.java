package com.science.activity;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.science.R;
import com.science.adapter.CommonFragmentPagerAdapter;
import com.science.fragment.ProjectApplyFragment;

import com.science.model.FirstClassItem;
import com.science.model.ResourceDefine;
import com.science.model.SecondClassItem;
import com.science.view.MyHeader;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectApplyActivity extends FragmentActivity {
	


	
	
	private LinearLayout main=null;
    private MyHeader proj_apply_header;
    private ProjectApplyFragment proj_fragment_hot;//热点项目
    private ProjectApplyFragment proj_fragment_poineer;//创业项目
	private ProjectApplyFragment proj_fragment_expire;//即将到期
	private ProjectApplyFragment proj_fragment_unscramble;//解读项目
	private ViewPager            proj_view_pager;
	private CommonFragmentPagerAdapter proj_fragment_adapter;

	
	
	public static final int PROJ_HOT = 0;
	public static final int PROJ_POINEER = 1;
	public static final int PROJ_EXPIRE = 2;
	public static final int PROJ_UNSCRAMBLE = 3;
	
	private final   int        ADD_SELECT_VIEW = 0;
	private final    int       RM_SELECT_VIEW = 2;
	private boolean                removed;//标记select_view是否被移除
	private int                added;  //标记select_view是否被加上
	
	
	
	
	
	private View            proj_select_view;
    private RelativeLayout  proj_main_layout;
	
	//	//项目类型和来源菜单
//	private ListView  proj_src_list_view;
//	private ListView  proj_src_next_list_view;
//	private ListView  proj_type_list_view;
//	private View      proj_src_pop_view;
//	private View      proj_type_pop_view;
//	private View      proj_src_btn;
//	private View      proj_type_btn;
//	private PopupWindow  proj_src_pop_win;
//	private PopupWindow   proj_type_pop_win;
//	private List<FirstClassItem> proj_src_list;
//	private List<SecondClassItem> proj_src_next_list;
//    private List<FirstClassItem> proj_type_list;
   private List<ResourceDefine> ref_def_list;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		LayoutInflater inflater = getLayoutInflater();  
		main=(LinearLayout)inflater.inflate(R.layout.project_apply, null); 
		setContentView(main);
		initVariable();
		initView();

	}
	

	
    
    private void initVariable(){
	    proj_src_list = new ArrayList<FirstClassItem>();


        ArrayList<SecondClassItem> proj_src_next_list1 = new ArrayList<SecondClassItem>();
        proj_src_next_list1.add(new SecondClassItem(100, "全部"));
        proj_src_next_list1.add(new SecondClassItem(101, "美国"));
        proj_src_next_list1.add(new SecondClassItem(102, "加拿大"));
        proj_src_next_list1.add(new SecondClassItem(103, "英国"));
        proj_src_list.add(new FirstClassItem(1, "国际", proj_src_next_list1));
        //2
        
        ArrayList<SecondClassItem> proj_src_next_list2 = new ArrayList<SecondClassItem>();
        ref_def_list = ResourceDefine.defined_area_resource;
	    for(int i = 0;i < ref_def_list.size();i++)
	    {
	    	proj_src_next_list2.add(new SecondClassItem(ref_def_list.get(i).getCode(),ref_def_list.get(i).getValue()));
	    	Log.i("MyResDef", ref_def_list.get(i).getValue());
	    }
	    
	    
//        
//        proj_src_next_list2.add(new SecondClassItem(200, "全部"));
//        proj_src_next_list2.add(new SecondClassItem(201, "天津"));
//        proj_src_next_list2.add(new SecondClassItem(202, "北京"));
//        proj_src_next_list2.add(new SecondClassItem(203, "秦皇岛"));
//        proj_src_next_list2.add(new SecondClassItem(204, "沈阳"));
//        proj_src_next_list2.add(new SecondClassItem(205, "大连"));
//        proj_src_next_list2.add(new SecondClassItem(206, "哈尔滨"));
//        proj_src_next_list2.add(new SecondClassItem(207, "锦州"));
//        proj_src_next_list2.add(new SecondClassItem(208, "上海"));
//        proj_src_next_list2.add(new SecondClassItem(209, "杭州"));
//        proj_src_next_list2.add(new SecondClassItem(210, "南京"));
//        proj_src_next_list2.add(new SecondClassItem(211, "嘉兴"));
//        proj_src_next_list2.add(new SecondClassItem(212, "苏州"));
        proj_src_list.add(new FirstClassItem(2, "国家", proj_src_next_list2));
        //3
        ArrayList<SecondClassItem> proj_src_next_list3 = new ArrayList<SecondClassItem>();
        proj_src_next_list3.add(new SecondClassItem(300, "全部"));
        proj_src_next_list3.add(new SecondClassItem(301, "南开区"));
        proj_src_next_list3.add(new SecondClassItem(302, "和平区"));
        proj_src_next_list3.add(new SecondClassItem(303, "河西区"));
        proj_src_next_list3.add(new SecondClassItem(304, "河东区"));
        proj_src_next_list3.add(new SecondClassItem(305, "滨海新区"));
        proj_src_list.add(new FirstClassItem(3, "各部", proj_src_next_list3));
        //4
        ArrayList<SecondClassItem> proj_src_next_list4 = new ArrayList<SecondClassItem>();
        proj_src_next_list4.add(new SecondClassItem(400, "全部"));
        proj_src_next_list4.add(new SecondClassItem(401, "天津"));
        proj_src_next_list4.add(new SecondClassItem(402, "北京"));
        proj_src_next_list4.add(new SecondClassItem(403, "秦皇岛"));
        proj_src_next_list4.add(new SecondClassItem(404, "沈阳"));
        proj_src_next_list4.add(new SecondClassItem(405, "大连"));
        proj_src_next_list4.add(new SecondClassItem(406, "哈尔滨"));
        proj_src_next_list4.add(new SecondClassItem(407, "锦州"));
        proj_src_next_list4.add(new SecondClassItem(408, "上海"));
        proj_src_next_list4.add(new SecondClassItem(409, "杭州"));
        proj_src_next_list4.add(new SecondClassItem(410, "南京"));
        proj_src_next_list4.add(new SecondClassItem(411, "嘉兴"));
        proj_src_next_list4.add(new SecondClassItem(412, "苏州"));
        proj_src_list.add(new FirstClassItem(4, "地域", proj_src_next_list4));

        proj_type_list = new ArrayList<FirstClassItem>();
        proj_type_list.add(new FirstClassItem(1,"全部",null));
        proj_type_list.add(new FirstClassItem(2,"社会科学",null));
        proj_type_list.add(new FirstClassItem(3,"自然科学",null));
        
        
        
        
        /*设置select_view被移除的状态*/
        removed = false;
        
    }
    
    
    

	private void initView()
	{

		initPopMenuView();
		proj_select_view = main.findViewById(R.id.proj_select_layout);
		proj_main_layout = (RelativeLayout) main.findViewById(R.id.proj_main_layout);
	    proj_fragment_hot = new ProjectApplyFragment();//热点项目
	    proj_fragment_poineer = new ProjectApplyFragment();//创业项目
		proj_fragment_expire = new ProjectApplyFragment();//即将到期
		proj_fragment_unscramble = new ProjectApplyFragment();//解读项目
		proj_apply_header = (MyHeader) findViewById(R.id.proj_apply_header);
		
		proj_fragment_adapter = new CommonFragmentPagerAdapter(this.getSupportFragmentManager(),
				                                               new Fragment[]{proj_fragment_hot,proj_fragment_poineer,
			                                                                  proj_fragment_expire,proj_fragment_unscramble});

		
		proj_view_pager = (ViewPager) findViewById(R.id.proj_view_pager);
		proj_view_pager.setAdapter(proj_fragment_adapter);
		proj_view_pager.setCurrentItem(0);
		//设置proj_apply的header
		proj_apply_header.SetHeaderText("项目申请");
		String[] header_btn_strs = {"热点项目","创业项目","项目解读","即将到期"};
		proj_apply_header.SetHeaderButtons(header_btn_strs);
		
		proj_apply_header.SetSelected(0);
		OnClickListener proj_header_tab_listener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				proj_view_pager.setCurrentItem(v.getId());
				if(v.getId() == PROJ_POINEER)
					handler.sendEmptyMessage(RM_SELECT_VIEW);
				else
					handler.sendEmptyMessage(ADD_SELECT_VIEW);
			}
			
		};
		
		proj_apply_header.SetOnHeadButtonClickListener(proj_header_tab_listener, 0);
		proj_apply_header.SetOnHeadButtonClickListener(proj_header_tab_listener, 1);
		proj_apply_header.SetOnHeadButtonClickListener(proj_header_tab_listener, 2);
		proj_apply_header.SetOnHeadButtonClickListener(proj_header_tab_listener, 3);
		
		
		proj_view_pager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				proj_apply_header.SetSelected(position);
				if(position == PROJ_POINEER)
					handler.sendEmptyMessage(RM_SELECT_VIEW);
				else
					handler.sendEmptyMessage(ADD_SELECT_VIEW);
			}
			
		});
		
		
	}
	

	
	
	
	
	/**
	 * 初始化ProjSourcePopView和ProjTypePopView
	 */
	 ListView  proj_src_list_view;
	 ListView  proj_src_next_list_view;
	 ListView  proj_type_list_view;
	 View      proj_src_pop_view;
	 View      proj_type_pop_view;
	 View      proj_src_btn;
	 View      proj_type_btn;
	 PopupWindow  proj_src_pop_win;
	 PopupWindow   proj_type_pop_win;
	 ProjSourceAdapter first_adapter;
	 ProjSourceNextAdapter second_adapter;
    ProjTypeAdapter proj_type_adapter;
	
	 List<Map<String,String>> proj_list;
	 List<FirstClassItem> proj_src_list;
	 List<SecondClassItem> proj_src_next_list;
    List<FirstClassItem> proj_type_list;
    String  str_url_proj_src;
	
    
    
    
    
	public void initPopMenuView(){
		
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		int screen_width = wm.getDefaultDisplay().getWidth();
		
		
		/**
		 * 项目来源和项目类型菜单设置
		 */
		proj_src_btn = findViewById(R.id.proj_src_layout);
		proj_type_btn = findViewById(R.id.proj_type_layout);
		
		proj_src_pop_view = getLayoutInflater().inflate(R.layout.common_secondary_pop_view, null);
		proj_type_pop_view = getLayoutInflater().inflate(R.layout.common_stair_pop_view, null);
		proj_src_list_view = (ListView) proj_src_pop_view.findViewById(R.id.common_secondary_pop_list);
	    
		first_adapter = new ProjSourceAdapter(this,proj_src_list);
		proj_src_list_view.setAdapter(first_adapter);
		
	     proj_src_next_list_view = (ListView) proj_src_pop_view.findViewById(R.id.common_secondary_pop_next_list);
	     proj_src_next_list = new ArrayList<SecondClassItem>();
	     proj_src_next_list.addAll(proj_src_list.get(0).getSecondList());
	     
	     second_adapter = new ProjSourceNextAdapter(this, proj_src_next_list);
	     proj_src_next_list_view.setAdapter(second_adapter);
	     
	     
	     //proj_type处理
	     proj_type_list_view = (ListView)proj_type_pop_view.findViewById(R.id.common_stair_pop_view_list);
	     proj_type_adapter = new ProjTypeAdapter(this,proj_type_list);
	     proj_type_list_view.setAdapter(proj_type_adapter);
	   //左侧ListView点击事件
	     proj_src_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                //二级数据
	                List<SecondClassItem> list2 = proj_src_list.get(position).getSecondList();
	                
                    String selectedName = proj_src_list.get(position).getName();
                    //str_url_proj_src  = application.ComposeToken(str_url_proj_src);
                    try {
						str_url_proj_src += "&projSource1=" + URLEncoder.encode(selectedName,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                //如果没有二级类，则直接跳转
	                if (list2 == null || list2.size() == 0) {
	                    proj_src_pop_win.dismiss();

	                    int firstId = proj_src_list.get(position).getId();

	                    handleResult(firstId, -1, selectedName);
	                    return;
	                }

	                ProjSourceAdapter adapter = (ProjSourceAdapter) (parent.getAdapter());
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
	     proj_src_next_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                //关闭popupWindow，显示用户选择的分类
	                proj_src_pop_win.dismiss();

	                int firstPosition = first_adapter.getSelectedPosition();
	                int firstId = proj_src_list.get(firstPosition).getId();
	                int secondId = proj_src_list.get(firstPosition).getSecondList().get(position).getId();
	                String selectedName = proj_src_list.get(firstPosition).getSecondList().get(position)
	                        .getName();
	                try {
						str_url_proj_src += "&projSource2=" + URLEncoder.encode(selectedName,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	                if(proj_list != null)
	                proj_list.clear();
	  
	                second_adapter.setSelectedPosition(position);
	                second_adapter.notifyDataSetChanged();
	                
	  
	            
	                handleResult(firstId, secondId, selectedName);
	            }
	        });
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
		   //项目类型ListView点击事件
	     proj_type_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                //关闭popupWindow，显示用户选择的分类
	                proj_type_pop_win.dismiss();             
	                String selectedName = proj_type_list.get(position).getName();
	                
	                try {
						str_url_proj_src += "&projType=" + URLEncoder.encode(selectedName,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                
	               
	
	                //proj_list_view.removeFooterView(proj_more_view);

	                
	                proj_type_adapter.notifyDataSetChanged();
	                proj_type_adapter.setSelectedPosition(position);
	  
	               // handleResult(firstId, secondId, selectedName);
	            }
	        });
	     
	     
	     
	     
	     
	    
	     
		//项目来源popupwindow
		proj_src_pop_win = new PopupWindow(proj_src_pop_view,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		proj_src_pop_win.setOutsideTouchable(true); 
		
		proj_src_pop_win.setBackgroundDrawable(new BitmapDrawable());
		proj_src_pop_win.setFocusable(true);
		proj_src_pop_win.setOnDismissListener(new PopupWindow.OnDismissListener(){

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				proj_src_list_view.setSelection(0);
				proj_src_next_list_view.setSelection(0);
				View vv = findViewById(R.id.proj_src_arrow);
				RotateAnimation rot_anim = new RotateAnimation(180,0,(float) (vv.getWidth()*0.5),(float) (vv.getHeight()*0.5));
				rot_anim.setFillAfter(true);
				rot_anim.setDuration(100);
				vv.startAnimation(rot_anim);
			}
			
		});
	
		
		
		//项目类型popupwindow
		proj_type_pop_win = new PopupWindow(proj_type_pop_view,screen_width/2,LayoutParams.WRAP_CONTENT);
		proj_type_pop_win.setOutsideTouchable(true); 
		
		proj_type_pop_win.setBackgroundDrawable(new BitmapDrawable());
		proj_type_pop_win.setFocusable(true);
		proj_type_pop_win.setOnDismissListener(new PopupWindow.OnDismissListener(){

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				proj_type_list_view.setSelection(0);
				View vv = findViewById(R.id.proj_type_arrow);
				RotateAnimation rot_anim = new RotateAnimation(180,0,(float) (vv.getWidth()*0.5),(float) (vv.getHeight()*0.5));
				rot_anim.setFillAfter(true);
				rot_anim.setDuration(100);
				vv.startAnimation(rot_anim);
			}
			
		});
		
		
		setOnMenuClickListener();
	}
	
	
	
	

	private class ProjSourceAdapter extends BaseAdapter{
		private Context context;
	    private List<FirstClassItem> list;
		public ProjSourceAdapter(Context context, List<FirstClassItem> list) {
	        this.context = context;
	        this.list = list;
	    }
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return proj_src_list.size();
			
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
			LinearLayout layout = new LinearLayout(ProjectApplyActivity.this);
			//layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			if (position == selectedPosition){
				ImageView iv = new ImageView(ProjectApplyActivity.this);
				iv.setImageResource(R.drawable.icon_green_point);
				layout.addView(iv);
				layout.setBackground(getResources().getDrawable(R.drawable.shape_dark_blue));
			  }else{
	            layout.setBackground(getResources().getDrawable(R.drawable.shape_light_blue));
			}
			layout.setGravity(Gravity.CENTER);
			TextView tv = new TextView(ProjectApplyActivity.this);
			tv.setTextColor(getResources().getColor(R.color.white));
			tv.setTextSize(15);
			tv.setText(proj_src_list.get(position).getName());
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
	
private class ProjSourceNextAdapter extends BaseAdapter{
   private Context context;
   private List<SecondClassItem> list;

   public ProjSourceNextAdapter(Context context, List<SecondClassItem> list){
        this.context = context;
        this.list = list;
 }
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return proj_src_next_list.size();
			
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
			LinearLayout layout = new LinearLayout(ProjectApplyActivity.this);
			//layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			
			if (position == selectedPosition){
				ImageView iv = new ImageView(ProjectApplyActivity.this);
				iv.setImageResource(R.drawable.icon_green_point);
				iv.setPadding(40, 10, 0, 10);
				layout.addView(iv);
				layout.setBackground(getResources().getDrawable(R.color.dark_blue));
			  }else{
	            layout.setBackground(getResources().getDrawable(R.color.light_blue));
			}
			//layout.setBackground(getResources().getDrawable(R.color.light_blue));
			layout.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			TextView tv = new TextView(ProjectApplyActivity.this);
			tv.setTextColor(getResources().getColor(R.color.white));
			tv.setTextSize(15);
			tv.setText(proj_src_next_list.get(position).getName());
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



private class ProjTypeAdapter extends BaseAdapter
{
   private Context context;
   private List<FirstClassItem> list;

   public ProjTypeAdapter(Context context, List<FirstClassItem> list){
        this.context = context;
        this.list = list;
 }
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return proj_type_list.size();
			
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
			LinearLayout layout = new LinearLayout(ProjectApplyActivity.this);
			//layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			
			if (position == selectedPosition){
				ImageView iv = new ImageView(ProjectApplyActivity.this);
				iv.setImageResource(R.drawable.icon_green_point);
				iv.setPadding(40, 10, 0, 10);
				layout.addView(iv);
				
				layout.setBackground(getResources().getDrawable(R.drawable.shape_dark_blue));
			  }else{
	            layout.setBackground(getResources().getDrawable(R.drawable.shape_light_blue));
			}
			//layout.setBackground(getResources().getDrawable(R.color.light_blue));
			layout.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			TextView tv = new TextView(ProjectApplyActivity.this);
			tv.setTextColor(getResources().getColor(R.color.white));
			tv.setTextSize(15);
			tv.setText(proj_type_list.get(position).getName());
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
                              ProjSourceNextAdapter secondAdapter) {
	proj_src_next_list.clear();
	proj_src_next_list.addAll(list2);
    secondAdapter.notifyDataSetChanged();
}

private void handleResult(int firstId, int secondId, String selectedName){
    String text = "first id:" + firstId + ",second id:" + secondId;
    Toast.makeText(this,text,Toast.LENGTH_SHORT).show();

}


private void setOnMenuClickListener(){
	   
    //设置proj_src_btn点击弹出事件
    proj_src_btn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final View vv = main.findViewById(R.id.proj_src_arrow);
			
			if (proj_src_pop_win.isShowing()) {

				proj_src_pop_win.dismiss();		
	        }
			else{
			
				RotateAnimation rot_anim = new RotateAnimation(0,180,(float) (vv.getWidth()*0.5),(float) (vv.getHeight()*0.5));
				rot_anim.setFillAfter(true);
				rot_anim.setDuration(100);
				vv.startAnimation(rot_anim);
				proj_src_pop_win.showAsDropDown(main.findViewById(R.id.proj_src_layout));
			}
			

		}
    	
    });
    
    
    
    
    
    
    //设置proj_type_btn点击弹出事件
    proj_type_btn.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final View vv = main.findViewById(R.id.proj_type_arrow);
			
			if (proj_type_pop_win.isShowing()) {

				proj_type_pop_win.dismiss();		
	        }
			else{
			
				RotateAnimation rot_anim = new RotateAnimation(0,180,(float) (vv.getWidth()*0.5),(float) (vv.getHeight()*0.5));
				rot_anim.setFillAfter(true);
				rot_anim.setDuration(100);
				vv.startAnimation(rot_anim);
				proj_type_pop_win.showAsDropDown(main.findViewById(R.id.proj_type_layout));
			}
			

		}
    	
    });
}




	
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			
			switch(msg.what){
			
			case ADD_SELECT_VIEW:
				if(removed)
				{
					proj_main_layout.addView(proj_select_view);
				    removed = false;
				}
				break;
			case RM_SELECT_VIEW:
				if(!removed)
				{
					proj_main_layout.removeView(proj_select_view);
				    removed = true;
				}
				break;
			}
		}
	};


}
