package com.science.fragment;



import com.example.science.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;




public class FragmentOfCoopPaper extends Fragment{
	 @Override  
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	           Bundle savedInstanceState)  
	    {  
	        return inflater.inflate(R.layout.coop_fragment_paper, container, false);  
	    }  

}