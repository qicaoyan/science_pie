package com.science.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.science.R;
import com.science.adapter.TechTrendAcdmAffrListAdapter;
import com.science.adapter.SubjectPopMenuListAdapter;
import com.science.model.CommonItem;
import com.science.model.ResourceDefine;
import com.science.model.Subject;
import com.science.view.MyImageButton;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TechTrendAcdmAffrFagment extends Fragment {

	private View view;
	private Activity      activity;
	private TextView      tecg_trend_subject_tv;
	private MyImageButton tech_trend_subject_extend_btn;
	private PopupWindow   tech_trend_subject_pop_win;
	private View          tech_trend_subject_pop_view;
	private View          tech_trend_subject_layout;
	private LayoutInflater      inflater;
	private ListView            tech_trend_acdm_affr_list_view;
	private ListView            tech_trend_first_subject_list_view;
	private ListView            tech_trend_second_subject_list_view;
	private List<Subject> tech_trend_subject_list;
	private List<CommonItem> tech_trend_acdm_affr_list;
	private SubjectPopMenuListAdapter tech_trend_first_subject_list_adapter;
	private SubjectPopMenuListAdapter tech_trend_second_subject_list_adapter;
	private TechTrendAcdmAffrListAdapter tech_trend_acdm_affr_list_adapter;
	private Subject curr_subject;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle bundle){
	
		view = inflater.inflate(R.layout.tech_trendency_fragment_academic_affair, null);
		activity = this.getActivity();
		this.inflater = inflater;
		initVariable();
		initView();
		setOnClickListener();
		setOnItemClickListener();
		return view;
	}
	
	
	private void initVariable(){
		
		tech_trend_acdm_affr_list = new ArrayList<CommonItem>();
		for(int i = 0;i < 10;i++){
			tech_trend_acdm_affr_list.add(new CommonItem("2015年\"十病十药\"研发项目征集指南",1234,1234));
		}
		tech_trend_acdm_affr_list_adapter = new TechTrendAcdmAffrListAdapter(activity,tech_trend_acdm_affr_list);
		tech_trend_subject_list = ResourceDefine.defined_resource_subject_list;
		tech_trend_first_subject_list_adapter = new SubjectPopMenuListAdapter(activity,tech_trend_subject_list);
	}
	
	public void initView(){
	

		
		tecg_trend_subject_tv = (TextView) view.findViewById(R.id.tech_trend_subject_text);
		tech_trend_subject_extend_btn = (MyImageButton) view.findViewById(R.id.tech_trend_subject_extend);
		tech_trend_subject_layout = view.findViewById(R.id.tech_trend_subject_layout);
		tech_trend_subject_pop_view = inflater.inflate(R.layout.tech_trend_academic_affair_tag_pop_view, null);
		tech_trend_acdm_affr_list_view = (ListView) view.findViewById(R.id.tech_trend_acdm_affr_list);
		tech_trend_first_subject_list_view = (ListView) tech_trend_subject_pop_view.findViewById(R.id.tech_trend_acdm_affr_tag_first_list);
		tech_trend_second_subject_list_view = (ListView) tech_trend_subject_pop_view.findViewById(R.id.tech_trend_acdm_affr_tag_second_list);
		tech_trend_subject_pop_win = new PopupWindow(tech_trend_subject_pop_view,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		
		tech_trend_acdm_affr_list_view.setAdapter(tech_trend_acdm_affr_list_adapter);
		tech_trend_first_subject_list_view.setAdapter(tech_trend_first_subject_list_adapter);   
		tech_trend_subject_pop_win.setOutsideTouchable(true);
		tech_trend_subject_pop_win.setFocusable(true);
		tech_trend_subject_pop_win.setBackgroundDrawable(new BitmapDrawable());
		tech_trend_subject_pop_win.setOnDismissListener(new PopupWindow.OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				float x = (float) (tech_trend_subject_extend_btn.getWidth()*0.5);
				float y = (float) (tech_trend_subject_extend_btn.getHeight()*0.5);
				RotateAnimation rot_anim = new RotateAnimation(180,0,x,y);
				rot_anim.setFillAfter(true);
				rot_anim.setDuration(10);
				tech_trend_subject_extend_btn.startAnimation(rot_anim);
			}
		});
		
		
		
		
		
	}
	
	public void setOnClickListener(){
		
		tech_trend_subject_extend_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				float x = (float) (v.getWidth()*0.5);
				float y = (float) (v.getHeight()*0.5);
				RotateAnimation rot_anim = new RotateAnimation(0,180,x,y);
				rot_anim.setFillAfter(true);
				rot_anim.setDuration(100);
				v.startAnimation(rot_anim);
				
				tech_trend_subject_pop_win.showAsDropDown(tech_trend_subject_layout);
				

			}
			
		});
		
		
		
		
	}
	
	public void setOnItemClickListener(){
		
		tech_trend_first_subject_list_view.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				tech_trend_first_subject_list_view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), 
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                int height = tech_trend_first_subject_list_view.getHeight();
                LayoutParams params = tech_trend_second_subject_list_view.getLayoutParams();
                params.height = height - 8;
                tech_trend_second_subject_list_view.setLayoutParams(params);
                tech_trend_second_subject_list_view.setMinimumHeight(height);
				List<Subject> subject_list = tech_trend_subject_list.get(position).getSubSbj();
				tech_trend_second_subject_list_adapter = new SubjectPopMenuListAdapter(activity,subject_list);
				tech_trend_second_subject_list_view.setAdapter(tech_trend_second_subject_list_adapter);
				
				
				//设置当前一级学科
				curr_subject = tech_trend_subject_list.get(position);
				tech_trend_first_subject_list_adapter.setSelectedPosition(position);
				tech_trend_first_subject_list_adapter.notifyDataSetChanged();

				
				//tech_trend_tag_pop_win.showAsDropDown(tech_trend_tag_layout);
			}
			
		});
		
		
		tech_trend_second_subject_list_view.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				tech_trend_second_subject_list_adapter.setSelectedPosition(position);
				tech_trend_second_subject_list_adapter.notifyDataSetChanged();
				
				Subject curr_sub_sbject;
				if(curr_subject.getSubSbj() != null){
					curr_sub_sbject = curr_subject.getSubSbj().get(position);
					tecg_trend_subject_tv.setText(curr_sub_sbject.getSbjName());
				}
				tech_trend_subject_pop_win.dismiss();
				
			}
			
		});
		
		
	}
}
