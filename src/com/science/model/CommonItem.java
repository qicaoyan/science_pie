package com.science.model;

public class CommonItem {

	private String title;
	private int like_num;
	private int comment_num;
	
	public CommonItem(String title,int like_num,int comment_num){
		this.title = title;
		this.like_num = like_num;
		this.comment_num = comment_num;
	}
	
	public String getTitle(){
		return this.title;
	}
	public int getLikeNum(){
		return this.like_num;
	}
	
	public int getCommentNum(){
		return this.comment_num;
	}
}
