package com.science.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.science.services.MyApplication;

public class Url {
	//public static String BASEURL="http://192.168.1.102:8080/";
	public static String BASEURL="http://123.57.207.9:80/";
	public static String HOTPAGEURL0=Url.BASEURL+"hot/hotList?typeId=0&plateId=";
	public static String HOTPAGEURL1=Url.BASEURL+"hot/hotList?typeId=0&plateId=";
	public static String HOTPAGEURL2=Url.BASEURL+"hot/hotList?typeId=0&plateId=";
	public static String HOTPAGEURL3=Url.BASEURL+"hot/hotList?typeId=0&plateId=";
	
	public static String HOTPAGEDETIALURL0=Url.BASEURL+"hot/hotList?typeId=1&plateId=";
	public static String HOTPAGEDETIALURL1=Url.BASEURL+"hot/hotList?typeId=1&plateId=";
	public static String HOTPAGEDETIALURL2=Url.BASEURL+"hot/hotList?typeId=1&plateId=";
	public static String HOTPAGEDETIALURL3=Url.BASEURL+"hot/hotList?typeId=1&plateId=";
	
	public static String ProgramList=Url.BASEURL+"GetProject/GetProjectByWeek?";
	public static String ProjectListBase = Url.BASEURL + "GetProject/GetTopProject?";
	public static String ProjectUsual  = Url.BASEURL + "GetProject/GetUsualProject?";
	public static String ProjectContentBaseUrl = Url.BASEURL + "GetProject/GetProjectDetail?";
	public static String programAvailableList=Url.BASEURL+"/GetProject/GetProjectAvailable?label=";
	public static String DocumentLIST = Url.BASEURL+"GetPaper/GetPaperTitle?";
	public static String DocumentContentBase=Url.BASEURL+"GetPaper/GetPaperTitle?cnkiId=";
	public static String DocumentDetailBase = Url.BASEURL + "GetPaper/GetPaperDetail?";
	public static String LOGINURL=Url.BASEURL+"/index/login?";
	public static String DOWNLOADKEYWORDS=Url.BASEURL+"personalTags/getPersonalTags?";
	public static String UpdateKeywords=Url.BASEURL+"personalTags/setPersonalTags?tagsString=";
	public static String addCollection=Url.BASEURL+"/collection/addCollection?";
	public static String removeCollection=Url.BASEURL+"/collection/deleteCollection?";
	public static String getCollection=Url.BASEURL+"/collection/getCollection?";
	
	public static String CommentList = Url.BASEURL + "comment/getComment?";
	public static final int MAX_ID = Integer.MAX_VALUE;
//	public static String HOTPAGEURL0="http://10.107.7.48:8080/hot/hotList?typeId=0&plateId=";
//	public static String HOTPAGEURL1="http://10.107.7.48:8080/hot/hotList?typeId=0&plateId=";
//	public static String HOTPAGEURL2="http://10.107.7.48:8080/hot/hotList?typeId=0&plateId=";
//	public static String HOTPAGEURL3="";
//	
//	public static String LOGINURL="http://10.107.7.48:8080/index/login?name=";
	
	
	public static String composeUrl(String ... args)
	{
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < args.length;i++)
		{
			sb.append(args[i]);
		}
		return sb.toString();
	}
	
	
    public static String composeDocListUrl(int doc_id,int doc_type,String doc_keywords)
    {
    	
        doc_keywords = URLEncoder.encode(doc_keywords);
    	return composeUrl(Url.DocumentLIST,"sid=",MyApplication.sidString,
    			   "&Id=",Integer.toString(doc_id),"&source=",Integer.toString(doc_type),
    			   "&keyWords=",doc_keywords);
    	
    }
    
    
    
    public static String composeMoreHotListUrl(int typeId,int plateId,int Id1,int Id2)
    {
    	String str_id1,str_id2;
    	if(Id1 == -1)
    		str_id1 = "null";
    	else
    		str_id1 = Integer.toString(Id1);
    	

    	if(Id2 == -1)
    		str_id2 = "null";
    	else
    		str_id2 = Integer.toString(Id2);
    	
    	
    	return composeUrl(Url.HOTPAGEDETIALURL0,"sid=",MyApplication.sidString,
    			          "&typeId=",Integer.toString(typeId),"&plateId=",str_id1,
    			          "&Id1=",Integer.toString(Id1),"&Id2=",str_id2);
    }
	
	
	
    
    public static String composeCommentListUrl(int article_type,int article_id,int comment_id)
    {
    	if(comment_id < 0)
    		comment_id = DefaultUtil.MAX_VALUE;
    	
    	return composeUrl(Url.CommentList,"sid=",MyApplication.sidString,"&articleType=",Integer.toString(article_type),
    			          "&articleId=",Integer.toString(article_id),"&id=",Integer.toString(comment_id));
    }
}
