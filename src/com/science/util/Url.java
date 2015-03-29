package com.science.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

import com.science.services.MyApplication;

public class Url {
	//public static String BASEURL="http://192.168.1.102:8080/";
	public static String BASEURL="http://123.57.207.9/";
	public static String HOTPAGEURL0=Url.BASEURL+"hot/hotList?typeid=0&classid=";
	public static String HOTPAGEURL1=Url.BASEURL+"hot/hotList?typeid=0&classid=";
	public static String HOTPAGEURL2=Url.BASEURL+"hot/hotList?typeid=0&classid=";
	public static String HOTPAGEURL3=Url.BASEURL+"hot/hotList?typeid=0&classid=";
	
	public static String HOTPAGEDETIALURL0=Url.BASEURL+"hot/hotList?typeid=1&classid=";
	public static String HOTPAGEDETIALURL1=Url.BASEURL+"hot/hotList?typeid=1&classid=";
	public static String HOTPAGEDETIALURL2=Url.BASEURL+"hot/hotList?typeid=1&classid=";
	public static String HOTPAGEDETIALURL3=Url.BASEURL+"hot/hotList?typeid=1&classid=";
	
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
	public static String getMessage=Url.BASEURL+"/message/getMessageInBox?";
	public static String updateCid=Url.BASEURL+"/Getui/setcid?cid=";
	public static String updateTags=Url.BASEURL+"/getui/getPushtags?";
	
	public static String CommentList = Url.BASEURL + "comment/getComment?";
	public static String ReleaseComment = Url.BASEURL + "comment/setComment?";
	public static String DeleteComment = Url.BASEURL + "comment/deleteComment?";
	public static final int MAX_ID = Integer.MAX_VALUE;
	
	public static String RegisterURL = Url.BASEURL + "Register/register?";
	
	public static String AddShoucangURL = Url.BASEURL + "collection/addCollection?";
	public static String DeleteShoucangURL = Url.BASEURL + "collection/deleteCollection?";
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
    
    /***
     * 
     * @param comment_id (INT 这是第二层和第三层回复需要提供的,第一层可填-1) 
     * @param root_id    (INT 同上，这是第一层回复的customerid吗,第一层可填-1) 
     * @param article_type (INT) 
     * @param article_id  (INT) 
     * @param mark        (INT 0表示前面无回复，1表示前面有一个回复。2表示前面有两个回复) 
     * @param content      (INT 0表示前面无回复，1表示前面有一个回复。2表示前面有两个回复) 
     * @return
     */
//    public static String compseReleaseCommentUrl(int comment_id,int root_id,int article_type,int article_id,int mark,String content){
//    	
//    	content = URLEncoder.encode(content);
//    	return composeUrl(Url.ReleaseComment,"sid=",MyApplication.sidString,"&commentid=",Integer.toString(comment_id),
//    			           "&rootid=",Integer.toString(root_id),"&articleType=",Integer.toString(article_type),
//    			           "&articleId=",Integer.toString(article_id),"&mark=",Integer.toString(mark),
//    			           "&content=",content);
//    	
//    }
    
    
    
  /**
   * 
   * @param upper_comment (INT 对文章评论，提供-1;对评论评论，提供相应commentid)
   * @param upper_customer (INT 对文章评论，提供-1;对评论评论，提供相应customerid) 
   * @param article_type
   * @param article_id
   * @param content
   * @return
   */
    
  public static String compseReleaseCommentUrl(int upper_comment,int upper_customer,int article_type,int article_id,String content){
	
	content = URLEncoder.encode(content);
	return composeUrl(Url.ReleaseComment,"sid=",MyApplication.sidString,"&upper_comment=",Integer.toString(upper_comment),
			           "&upper_customer=",Integer.toString(upper_customer),"&articleType=",Integer.toString(article_type),
			           "&articleId=",Integer.toString(article_id),
			           "&content=",content);
	
}
    
    
    
    
    
    
    
    /**
     * 
     * @param comment_id 表明删除的评论（源评论）
     *
     * @return
     */
    public static String composeDeleteCommentUrl(int comment_id)
    {
    	return composeUrl(Url.DeleteComment,"sid=",MyApplication.sidString,"&commentid=",
    			          Integer.toString(comment_id));
    }
    
    
    public static String composeRegisterUrl(String nickname,String password,String email){
    	
    	return composeUrl(Url.RegisterURL,"sid=",MyApplication.sidString,"&username=",email,
    			         "&nickname=",nickname,"&password=",password);
    }
    
    
    
    
    public static String composeAddShoucangUrl(int article_type,int article_id,String url,String title){
    	
    	try {
			url = URLEncoder.encode(url,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return composeUrl(Url.AddShoucangURL,"sid=",MyApplication.sidString,
    			          "&articleType=",Integer.toString(article_type),
    			          "&articleId=",Integer.toString(article_id),
         		          "&url=",url,"&title=",title);
    
    }
    
    
    public static String composeDeleteShoucangUrl(int article_type,int article_id)
    {
       	return composeUrl(Url.DeleteShoucangURL,"sid=",MyApplication.sidString,
		          "&articleType=",Integer.toString(article_type),
		          "&articleId=",Integer.toString(article_id));
    }
    
    
    
    
}
