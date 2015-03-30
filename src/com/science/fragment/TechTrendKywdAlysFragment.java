package com.science.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.science.R;
import com.science.adapter.TechTrendKywdAlysListAdapter;
import com.science.model.KywdAlys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TechTrendKywdAlysFragment extends Fragment {
    
	private View        view;
	private Activity    activity;
	private ListView    kywd_alys_list_view;
	private List<KywdAlys> kywd_alys_list;
	private TechTrendKywdAlysListAdapter kywd_alys_list_adapter;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		
		view = inflater.inflate(R.layout.tech_trendency_fragment_kywd_alys, null);
		activity = this.getActivity();
		initVariable();
		initView();
		return view;
	}
	
	
	public void initVariable(){
		
		kywd_alys_list = new ArrayList<KywdAlys>();
		for(int i = 0;i < 10;i++)
		kywd_alys_list.add(new KywdAlys("城市化","城市化的关键词分析"));
	}
	public void initView(){
		kywd_alys_list_view = (ListView) view.findViewById(R.id.tech_trend_kywd_alys_list);
	    kywd_alys_list_adapter  = new TechTrendKywdAlysListAdapter(activity);
	    kywd_alys_list_adapter.updateKywdAlysList(kywd_alys_list);
	    kywd_alys_list_view.setAdapter(kywd_alys_list_adapter);
	    
	}
}
