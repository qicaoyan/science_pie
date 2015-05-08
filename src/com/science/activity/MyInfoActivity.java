package com.science.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.example.science.R;
import com.science.json.JsonCommonResponseStateHandler;
import com.science.json.JsonGetMyInfoHandler;
import com.science.model.ResourceDefine;
import com.science.services.MyApplication;
import com.science.util.Url;
import com.science.view.CircularImage;
import com.science.view.MyImageButton;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MyInfoActivity extends Activity{
	private MyApplication myApplication=null;
	private EditText nickname_edit=null;
	private EditText organization_edit = null;
	private EditText email_edit = null;
	private Spinner area_spinner=null;
	private Spinner identity_spinner=null;
	private String   nickname = "";
	private String   identity = "";
	private String   organization = "";
	private String   area = "";
	private String   email = "";
	private MyImageButton  save_btn;
//	private EditText userName=null;
//	private EditText password=null;
//	private EditText repassword=null;
    private String  edit_info_url = "";
	private static  String[] strDiYu={"河北","山东","海南","河南","山西"};
	private static  String[] strZhiYe={"学生","学者","科研人员"};
	private ArrayAdapter<String> adapterDiYu;
	private ArrayAdapter<String> adapterZhiYe;
	
	private ImageButton headerback=null;
	private TextView headertitle;
	
	private CircularImage avatar;
	private static final int IMAGE_REQUEST_CODE = 2;
	private static final int RESULT_REQUEST_CODE = 3;
	
	private final int EDIT_MY_INFO_OK = 0;
	private final int GET_MY_INFO_OK = 1;
	private Map<String,Object> my_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		myApplication=(MyApplication)this.getApplication();
		
		// 去掉顶部灰条
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.myinfo);
		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		
		InitVariable();
		InitViews();
		InitData();
	}
	
	private void InitVariable()
	{
		strDiYu = new String[ResourceDefine.defined_resource_coop_area_list.size() - 1];
		for(int i = 1;i < ResourceDefine.defined_resource_coop_area_list.size();i++){
			
			strDiYu[i - 1] = ResourceDefine.defined_resource_coop_area_list.get(i).getResName();
		}
	}
	
	private void InitViews()
	{
		headerback=(ImageButton)findViewById(R.id.settingback);
		headertitle=(TextView)findViewById(R.id.settingheadertitle);
		nickname_edit = (EditText) findViewById(R.id.setting_info_nickname_edit);
		
		area_spinner=(Spinner)findViewById(R.id.setting_info_area_spinner);
		identity_spinner=(Spinner)findViewById(R.id.setting_info_identity_spinner);
		save_btn = (MyImageButton) findViewById(R.id.setting_info_save);
		organization_edit = (EditText) findViewById(R.id.setting_info_organization_edit);
		email_edit = (EditText) findViewById(R.id.setting_info_email_edit);
		avatar = (CircularImage) findViewById(R.id.avatar);
		
		avatar.setOnClickListener(onClickListener);
		
		save_btn.setOnClickListener(onClickListener);
	}
	
	private void InitData()
	{
		

		
		
		
		adapterDiYu=new ArrayAdapter<String>(this,R.layout.my_spinner,strDiYu);
		adapterZhiYe=new ArrayAdapter<String>(this,R.layout.my_spinner,strZhiYe);
		adapterDiYu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterZhiYe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		area_spinner.setAdapter(adapterDiYu);
		identity_spinner.setAdapter(adapterZhiYe);
		
		headertitle.setText("我的资料");
		headerback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				URL url;
				String my_info_str_url = Url.composeGetMyInfoUrl();
				try {
					url = new URL(my_info_str_url);
					URLConnection conn = url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					JsonGetMyInfoHandler json = new JsonGetMyInfoHandler();
					List<Map<String,Object>> temp = json.getListItems(is);
					if(temp != null){
						my_info = temp.get(0);
						handler.sendEmptyMessage(GET_MY_INFO_OK);
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
		}).start();
		
		
	}
	
	
	
	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()){
			case R.id.avatar:
				Intent intentFromGallery = new Intent();
				intentFromGallery.setType("image/*"); // 设置文件类型
				intentFromGallery
						.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intentFromGallery,
						IMAGE_REQUEST_CODE);
				break;
				
			
			case R.id.setting_info_save:
				
				nickname = nickname_edit.getText().toString();
				identity = identity_spinner.getSelectedItem().toString();
				organization = organization_edit.getText().toString();
				area = area_spinner.getSelectedItem().toString();
				email = email_edit.getText().toString();
				if(nickname.length() < 5){
					
					Toast.makeText(MyInfoActivity.this, "昵称太短", Toast.LENGTH_SHORT).show();
				    break;
				}
				
				if(identity.isEmpty())
				{
					Toast.makeText(MyInfoActivity.this, "身份不能为空", Toast.LENGTH_SHORT).show();
					break;
				}
				
				if(organization.isEmpty() ){
					Toast.makeText(MyInfoActivity.this, "单位不能为空", Toast.LENGTH_SHORT).show();
					break;
				}
				
				if(area.isEmpty()){
					Toast.makeText(MyInfoActivity.this, "地域不能为空", Toast.LENGTH_SHORT).show();
					break;
				}
				
				
				if(!email.contains("@")){
					Toast.makeText(MyInfoActivity.this, "邮箱不合法", Toast.LENGTH_SHORT).show();
					break;
				}
				
				
				
				edit_info_url = Url.composeEditMyInfoUrl(nickname, identity, organization, area, email);
				
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						URL url;
						try {
							url = new URL(edit_info_url);
							URLConnection conn = url.openConnection();
							conn.connect();
							InputStream is = conn.getInputStream();
							JsonCommonResponseStateHandler json = new JsonCommonResponseStateHandler();
							int code = json.getResponseStateCode(is);
							if(code == 200){
								handler.sendEmptyMessage(EDIT_MY_INFO_OK);
							}
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}).start();
				
				break;
			}
		}
		
	};
	
	
	
	
	Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			
			switch(msg.what){
			case EDIT_MY_INFO_OK:
				Toast.makeText(MyInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
				finish();
				break;
			case GET_MY_INFO_OK:
				nickname_edit.setText(my_info.get("nickname").toString());
				int pos = 0;
				for(int i = 0;i < identity_spinner.getCount();i++){
					
					if(identity_spinner.getItemAtPosition(i).toString().equals(my_info.get("identity").toString())){
						pos = i;
						break;
					}
				}
				identity_spinner.setSelection(pos);
				pos = 0;
				
				String organization = (String) my_info.get("organization");
				if(organization == null)
					organization = "";
				organization_edit.setText(organization);
				
				for(int i = 0;i < area_spinner.getCount();i++){
					
					if(area_spinner.getItemAtPosition(i).toString().equals(my_info.get("area").toString())){
						pos = i;
						break;
					}
				}
				
				area_spinner.setSelection(pos);
				pos = 0;
				
				email_edit.setText(my_info.get("email").toString());
				break;
			default:
				break;
			}
				
		}
	};
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			avatar.setImageDrawable(drawable);
			Log.i("saveAvatar", "true");
		}
		Log.i("saveAvatar", "false");
	}	

	
}
