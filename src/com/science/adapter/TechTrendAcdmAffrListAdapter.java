package com.science.adapter;

import java.util.List;

import com.example.science.R;
import com.science.model.CommonItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TechTrendAcdmAffrListAdapter extends BaseAdapter {

	private List<CommonItem> tech_trend_acdm_affr_list;
	private Context context;
	public TechTrendAcdmAffrListAdapter(Context context,List<CommonItem> list){
		this.context = context;
		this.tech_trend_acdm_affr_list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(tech_trend_acdm_affr_list != null)
			return tech_trend_acdm_affr_list.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tech_trend_acdm_affr_list.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = ((Activity)context).getLayoutInflater().from(context);
		View view = inflater.inflate(R.layout.tech_trend_acdm_affr_list_item, null);
		
		TextView title_tv = (TextView) view.findViewById(R.id.tech_trend_acdm_affr_item_title);
		TextView like_num_tv = (TextView) view.findViewById(R.id.tech_trend_acdm_affr_item_like_num);
		TextView comment_num_tv = (TextView) view.findViewById(R.id.tech_trend_acdm_affr_item_comment_num);
		
		title_tv.setText(tech_trend_acdm_affr_list.get(position).getTitle());
		like_num_tv.setText("" + tech_trend_acdm_affr_list.get(position).getLikeNum());
		comment_num_tv.setText("" + tech_trend_acdm_affr_list.get(position).getCommentNum());
		
		return view;
	}

}
