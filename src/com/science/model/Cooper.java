package com.science.model;

import android.content.Context;
import android.graphics.Bitmap;

public class Cooper {

	private Context context;
	private Bitmap  cooper_photo;
	private int     cooper_id;
	private String  cooper_name;
	private String  cooper_identity;
	private String  cooper_info;
	private String  cooper_introduction;

    public static final int PHOTO = 0;
    public static final int ID = 1; 
    public static final int NAME = 2;
    public static final int IDENTITY = 3;
    public static final int INFO = 4;
    public static final int INTRODUCTION = 5;
	public Cooper(Context context)
	{
		this.context = context;
	}
	
	
	public Cooper(Context context,Bitmap photo, int id, String name,String identity,
			String info,String introduction){
		
		this.context = context;
		this.cooper_photo = photo;
		this.cooper_id = id;
		this.cooper_name = name;
		this.cooper_identity = identity;
		this.cooper_info = info;
		this.cooper_introduction = introduction;
	}
	
	public void setCooperInformation(Bitmap photo,int id,String name,String identity,String info ,
			String introduction){
		
		this.cooper_photo = photo;
		this.cooper_id = id;
		this.cooper_name = name;
		this.cooper_identity = identity;
		this.cooper_info = info;
		this.cooper_introduction = introduction;
		
	}
	
	
   public Object getCooperProperty(int type)
   {
	   Object obj = null;
	   switch(type)
	   {
	   case PHOTO:
		   obj = this.cooper_photo;
		   break;
	   case ID:
		   obj = this.cooper_id;
		   break;
	   case NAME:
		   obj = this.cooper_name;
		   break;
	   case IDENTITY:
		   obj = this.cooper_identity;
		   break;
	   case INFO:
		   obj = this.cooper_info;
		   break;
	   case INTRODUCTION:
		   obj = this.cooper_introduction;
		   break;
	   }
	   return obj;
	   
   }
	
}
