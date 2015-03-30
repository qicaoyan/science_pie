package com.science.activity;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.science.R;
import com.science.adapter.CommonFragmentPagerAdapter;
import com.science.adapter.CommonPopMenuListAdapter;
import com.science.adapter.SubjectPopMenuListAdapter;
import com.science.fragment.ProjectApplyFragment;

import com.science.model.FirstClassItem;
import com.science.model.Resource;
import com.science.model.ResourceDefine;
import com.science.model.SecondClassItem;
import com.science.view.MyHeaderView;

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
    private MyHeaderView proj_apply_header;
    private ProjectApplyFragment proj_fragment_hot;//热点项目
    private ProjectApplyFragment proj_fragment_poineer;//创业项目
	private ProjectApplyFragment proj_fragment_expire;//即将到期
	private ProjectApplyFragment proj_fragment_unscramble;//解读项目
	private ViewPager            proj_view_pager;
	private CommonFragmentPagerAdapter proj_fragment_adapter;

	private CommonPopMenuListAdapter proj_src_first_list_adapter;
	private CommonPopMenuListAdapter proj_src_second_list_adapter;
	private CommonPopMenuListAdapter proj_type_list_adapter;
	private Resource                 curr_proj_src;
	private List<Resource> proj_src_all_list;
	private List<Resource> proj_type_list;
	public static final int PROJ_HOT = 0;
	public static final int PROJ_POINEER = 1;
	public static final int PROJ_EXPIRE = 2;
	public static final int PROJ_UNSCRAMBLE = 3;
	
	private final   int        ADD_SELECT_VIEW = 0;
	private final    int       RM_SELECT_VIEW = 2;
	private boolean                removed;//标记select_view是否被移除
	
	
	
	
	
	private View            proj_select_view;
    private RelativeLayout  proj_main_layout;
    
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

        proj_src_all_list = ResourceDefine.defined_resource_proj_src_list;
        proj_type_list = ResourceDefine.defined_resource_proj_type_list;
        proj_src_first_list_adapter = new CommonPopMenuListAdapter(this,proj_src_all_list);
        proj_type_list_adapter = new CommonPopMenuListAdapter(this,proj_type_list);
   
        
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
		proj_apply_header = (MyHeaderView) findViewById(R.id.proj_apply_header);
		
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
     String  str_url_proj_src;
	
    
    
    
    
	public void initPopMenuView(){
		
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		final int screen_width = wm.getDefaultDisplay().getWidth();
		final int screen_height = wm.getDefaultDisplay().getHeight();
		
		/**
		 * 项目来源和项目类型菜单设置
		 */
		proj_src_btn = findViewById(R.id.proj_src_layout);
		proj_type_btn = findViewById(R.id.proj_type_layout);
		
		proj_src_pop_view = getLayoutInflater().inflate(R.layout.common_secondary_pop_view, null);
		proj_type_pop_view = getLayoutInflater().inflate(R.layout.common_stair_pop_view, null);
		proj_src_list_view = (ListView) proj_src_pop_view.findViewById(R.id.common_secondary_pop_list);
	    
		proj_src_list_view.setAdapter(proj_src_first_list_adapter);
	    proj_src_next_list_view = (ListView) proj_src_pop_view.findViewById(R.id.common_secondary_pop_next_list);

	     
	     //proj_type处理
	     proj_type_list_view = (ListView)proj_type_pop_view.findViewById(R.id.common_stair_pop_view_list);
	     proj_type_list_view.setAdapter(proj_type_list_adapter);
	   //左侧ListView点击事件
	     proj_src_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            	
	            	
	            	proj_src_first_list_adapter.setSelectedPosition(position);
	            	proj_src_first_list_adapter.notifyDataSetChanged();
	            	curr_proj_src = proj_src_all_list.get(position);
	            	List<Resource> sub_proj_src_list;
	            	if(curr_proj_src != null)
	            	{
	            	  sub_proj_src_list = curr_proj_src.getSubResList();
	            	  proj_src_second_list_adapter = new CommonPopMenuListAdapter(ProjectApplyActivity.this,sub_proj_src_list);
	            	  proj_src_next_list_view.setAdapter(proj_src_second_list_adapter);
	            	}
          


	            	/*设置二级菜单的高度*/
                    int height = proj_src_list_view.getHeight();
    	                LayoutParams params = proj_src_next_list_view.getLayoutParams();
    	                params.height = (int) (2*height) ;
    	                proj_src_next_list_view.setLayoutParams(params);
    	                proj_src_next_list_view.setMinimumHeight(height);
	            }
	        });

	     
	     
	   //右侧ListView点击事件
	     proj_src_next_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                //关闭popupWindow，显示用户选择的分类
	            	
	            	
	            	proj_src_second_list_adapter.setSelectedPosition(position);
	            	proj_src_second_list_adapter.notifyDataSetChanged();
	            	
	                proj_src_pop_win.dismiss();

                    

	            }
	        });
	     
	     
	     
	     
	     
	     
	     
	     
	     
	     
		   //项目类型ListView点击事件
	     proj_type_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    

	                proj_type_list_adapter.setSelectedPosition(position);
	                proj_type_list_adapter.notifyDataSetChanged();
	                //关闭popupWindow，显示用户选择的分类
	                proj_type_pop_win.dismiss();         
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
