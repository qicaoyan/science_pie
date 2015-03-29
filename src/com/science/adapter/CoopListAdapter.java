package com.science.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.science.R;
import com.science.model.Cooper;






	public class CoopListAdapter extends BaseAdapter
	{

		private Context context;
		private List<Cooper> cooper_list;
		public CoopListAdapter(Context context,List<Cooper> list)
		{
			this.context = context;
			this.cooper_list = list;
		}
		
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(cooper_list != null)
			return cooper_list.size();
			
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
			
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.coop_fragment_common_list_item, null);
            ImageView photo_iv = (ImageView) view.findViewById(R.id.coop_cooper_photo);
            TextView base_info_tv = (TextView)view.findViewById(R.id.coop_cooper_base_info);
            TextView brief_intro_tv = (TextView)view.findViewById(R.id.coop_cooper_brief_introduction);
           // if(cooper_list == null)
            //	return null;
            
            Cooper cooper = cooper_list.get(position);
            String base_info =  (String) cooper.getCooperProperty(Cooper.NAME);
            int pos1 = 0,pos2 = base_info.length();        
            base_info += " " + cooper.getCooperProperty(Cooper.IDENTITY);
            int pos3 = pos2 + 1,pos4 = base_info.length();
            base_info += " " + cooper.getCooperProperty(Cooper.INFO);
            int pos5 = pos4 + 1,pos6 = base_info.length();
            
            SpannableString span_base_info = new SpannableString(base_info);
            
            span_base_info.setSpan(new TextAppearanceSpan(context,R.style.coop_cooper_text_name_style), pos1, pos2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            span_base_info.setSpan(new TextAppearanceSpan(context,R.style.coop_cooper_text_identity_style), pos3, pos4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
           
            span_base_info.setSpan(new TextAppearanceSpan(context,R.style.coop_cooper_text_additional_style), pos5, pos6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //span_base_info.setSpan(new RelativeSizeSpan(15), pos1, pos2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //span_base_info.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_light_gray)), 
            	//	pos1, pos2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            
           // span_base_info.setSpan(new RelativeSizeSpan(13), pos3, pos4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
           // span_base_info.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_light_gray)), 
            	//	pos3, pos4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
          
            
           // span_base_info.setSpan(new RelativeSizeSpan(15), pos5, pos6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //span_base_info.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_dark_gray)), 
            	//	pos5, pos6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            
            base_info_tv.setText(span_base_info);
            
            
            String brief_intro = (String) cooper.getCooperProperty(Cooper.INTRODUCTION);
            brief_intro_tv.setText(brief_intro);
            
            convertView = view;
            
            return convertView;
		}

		
	  public void updateCooperList(List<Cooper> list)
	  {
		  this.cooper_list = list;
		  this.notifyDataSetChanged();
		  
	  }
		

      public void clearList(List<File> f) {
          int size = f.size();
          if (size > 0) {
                  f.removeAll(f);
                 // coop_list_adapter.notifyDataSetChanged();
          }
          
      
          
  }
 
	
	
}
