package com.science.fragment;

import com.example.science.R;
import com.science.view.MyImageButton;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CoopReleaseSourceFragment extends Fragment {

	private View view;
	private Activity activity;
	
	/*定义各种宽高，单位是px，最终要转换成dip,dip = px*160/desity*/
	
	private  int width_user_name = 214;
	private  int height_user_name = 76;
	private  int width_user_college = 318;
	private  int height_user_college = 76;
	private  int width_user_identity = 216;
	private  int height_user_identity = 82;
	private  int width_user_job = 216;
	private  int height_user_job = 82;
	private  int width_user_area = 216;
	private  int height_user_area = 82;
	private  int width_user_major = 216;
	private  int height_user_major = 82;
	private  int width_user_mail = 441;
	private  int height_user_mail = 82;
	private  int width_user_experience = 672;
	private  int height_user_experience = 282;
	private  int width_team_members =    108;
	private  int height_team_members = 47;
	
	private  int width_device_name = 83;
	private  int height_device_name = 60;
	private  int width_device_type = 83;
	private  int height_device_type = 60;
	private  int width_device_version = 83;
	private  int height_device_version = 60;
	private  int width_device_num = 83;
	private  int height_device_num = 60;
	private  int width_upload_pic = 173;
	private  int height_upload_pic = 60;
	
	/*定义所有的控件*/
	
	private ImageView user_photo_iv;
	private EditText  user_name_et;
	private EditText  user_college_et;
	
	/*身份选择框*/
	private View      user_identity_select_view;
	private TextView  user_identity_select_tv;
	private MyImageButton  user_identity_select_btn;
	
	/*职业选择框*/
	private View      user_job_select_view;
	private TextView      user_job_select_tv;
	private MyImageButton user_job_select_btn;
	
	private View      user_area_select_view;
	private EditText  user_major_et;
	private EditText  user_mail_et;
	private EditText  user_experience_et;
	private MyImageButton  user_upload_pic_btn;
	private CheckBox       user_team_cb;
	private View           user_team_member_view;
	private CheckBox       user_device_cb; 
	private View           device_name_view;
	private EditText       device_type_et;
	private EditText       device_version_et;
	private View           device_num_view;
	private View           device_upload_pic_view;
	private View           device_add_tv;
	private MyImageButton  finish_release_btn;
	public CoopReleaseSourceFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		
		this.view = inflater.inflate(R.layout.coop_fragment_release_source, null);
		this.activity = this.getActivity();
		initViews();
		return this.view;
	}
	
	
	public int px2dip(int px){
		
		int dip = 0;
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		float scale = metrics.density;
		dip = (int) (px/scale + 0.5f);
		return dip;
	}
	
	public int normalize(int px)
	{
		int norm_px = 0;
		float scale = (float)activity.getWindowManager().getDefaultDisplay().getWidth()/720.0f;
		norm_px = (int) (px*scale);
		return norm_px;
	}
	
	public void initViews(){
		
		user_photo_iv = (ImageView) view.findViewById(R.id.release_resource_user_photo);
		user_name_et = (EditText) view.findViewById(R.id.release_resource_user_name);
		user_college_et = (EditText) view.findViewById(R.id.release_resource_user_college);
		
		/*身份选择框获取*/
		user_identity_select_view = view.findViewById(R.id.release_resource_user_identity);
		user_identity_select_tv = (TextView) user_identity_select_view.findViewById(R.id.release_select_text);
		user_identity_select_btn = (MyImageButton) user_identity_select_view.findViewById(R.id.release_select_btn);
		/*职业选择框获取*/
		user_job_select_view =  view.findViewById(R.id.release_resource_user_job);
		user_job_select_tv = (TextView) user_job_select_view.findViewById(R.id.release_select_text);
		user_job_select_btn = (MyImageButton) user_job_select_view.findViewById(R.id.release_select_btn);
		/*地域选择框获取*/
		user_job_select_view = view.findViewById(R.id.release_resource_user_area);
		
		user_area_select_view =  view.findViewById(R.id.release_resource_user_area);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		//params.width = width_user_name;
		//params.height = height_user_name;
		user_name_et.setLayoutParams(params);
		
		user_name_et.setWidth(normalize(width_user_name));
		user_name_et.setHeight(normalize(height_user_name));
		
		
		//params.width = width_user_college;
		//params.height = height_user_college;
		user_college_et.setLayoutParams(params);
		user_college_et.setWidth(normalize(width_user_college));
		user_college_et.setHeight(normalize(height_user_college));
		
		
		
		
		//user_identity_select_view.setLayoutParams(params);
		/*设置身份*/
		user_identity_select_view.setMinimumWidth(normalize(width_user_identity));
		user_identity_select_view.setMinimumHeight(normalize(height_user_identity));
		user_identity_select_tv.setText("身份");
		
		
		/*设置职业*/
		user_job_select_view.setMinimumWidth(normalize(width_user_job));
		user_job_select_view.setMinimumHeight(normalize(height_user_job));
		user_job_select_tv.setText("职业");
	}
}
