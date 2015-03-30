package com.science.activity;

import com.example.science.R;
import com.science.adapter.CommonFragmentPagerAdapter;
import com.science.fragment.CoopReleaseSourceFragment;
import com.science.view.MyHeaderView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class CoopReleaseActivity extends FragmentActivity{

	private ViewPager                   coop_release_view_pager;
	private MyHeaderView                    coop_release_header_view;
	private CommonFragmentPagerAdapter  coop_release_frgament_adapter;
	private CoopReleaseSourceFragment   coop_release_source_fragment;
	
     @Override
     public void onCreate(Bundle savedInstanceState){
    	 super.onCreate(savedInstanceState);
    	 setContentView(R.layout.coop_release);
    	 initView();
    	 
     }
     
     public void initView(){
    	 
    	 
    	 
    	 coop_release_header_view = (MyHeaderView) findViewById(R.id.coop_release_header);
    	 coop_release_header_view.SetHeaderText("合作研究");
    	 coop_release_header_view.SetHeaderButtons(new String[]{"发布资源","发布需求"});
    	 coop_release_header_view.SetSelected(0);
    	 
    	 
    	 coop_release_source_fragment = new CoopReleaseSourceFragment();
    	 coop_release_frgament_adapter = new CommonFragmentPagerAdapter(this.getSupportFragmentManager(),
    			                                                        new Fragment[]{coop_release_source_fragment});
    	
    	 coop_release_view_pager = (ViewPager) findViewById(R.id.coop_release_view_pager);
    	 coop_release_view_pager.setAdapter(coop_release_frgament_adapter);
    	 coop_release_view_pager.setCurrentItem(0);
     }
    
}
