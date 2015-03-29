package com.science.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public class CommonFragmentPagerAdapter extends FragmentPagerAdapter{

	FragmentManager fm ;
	Fragment[] fragments;
    //boolean[] fragments_update_flag;
	public CommonFragmentPagerAdapter(FragmentManager fm,Fragment[] fragments ) {
		super(fm);
		this.fm = fm;
		
		this.fragments = fragments;
		//this.fragments_update_flag = fragments_update_flag;
	
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return fragments[position%this.fragments.length];
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.fragments.length;
	}
	
	
	@Override
	public int getItemPosition(Object object){
		
		return POSITION_NONE;
	}

	
//	@Override
//	public Object instantiateItem(ViewGroup container,int position){
//		
//		Fragment fragment = (Fragment) super.instantiateItem(container, position);
//		String fragment_tag = fragment.getTag();
//		
//		if(fragments_update_flag[position % fragments_update_flag.length]){
//			FragmentTransaction ft = fm.beginTransaction();
//			ft.remove(fragment);
//			fragment = fragments[position % fragments.length];
//			ft.add(container.getId(), fragment,fragment_tag);
//			ft.attach(fragment);
//			ft.commit();
//			
//			fragments_update_flag[position % fragments_update_flag.length] = false;
//		}
//		
//		return fragment;
//	}
	
	
}
