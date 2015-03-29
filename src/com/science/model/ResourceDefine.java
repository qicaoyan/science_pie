package com.science.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;


public class ResourceDefine {
	
	private String abbr;//英文简称
	private int    code;        //数字编码
	private String value;       //表示的值    
    
	public static DocumentBuilderFactory factory;
	public static List<ResourceDefine> defined_area_resource ;
	public ResourceDefine(String abbr,int code,String value){
		
		this.abbr = abbr;
		this.code = code;
		this.value = value;
	}
	
	public static void deriveResourceDefine(Context context){
		
		if(defined_area_resource == null)
			defined_area_resource = new ArrayList<ResourceDefine>();
	    
		factory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			InputStream is = context.getAssets().open("ResourceDefine.xml");
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			
			NodeList links = root.getElementsByTagName("province");
			
			for(int i = 0;i < links.getLength();i++)
			{
				Element link  = (Element) links.item(i);
				NodeList child_links = link.getChildNodes();
				String abbr = "";
				int code = 0;
				String value = "";
				for(int j = 0;j < child_links.getLength();j++)
				{
					Node node = child_links.item(j);//判断是否为元素类型
					if(node.getNodeType() == Node.ELEMENT_NODE){
						
						Element child_node = (Element)node;

						if("abbr".equals(child_node.getNodeName())){
							abbr = child_node.getFirstChild().getNodeValue();
						}
						
						if("code".equals(child_node.getNodeName()))
						{
							code = Integer.parseInt(child_node.getFirstChild().getNodeValue());
						}
						
						if("value".equals(child_node.getNodeName()))
						{
							value = child_node.getFirstChild().getNodeValue();
						}
	
					}
				}
				
				
				ResourceDefine res_def = new ResourceDefine(abbr,code,value);
				defined_area_resource.add(res_def);
				//String abbr = link.getElementsByTagName("abbr").item(0).getFirstChild().getNodeValue();
				//int code = Integer.parseInt(link.getElementsByTagName("code").item(0).getFirstChild().getNodeValue());
				//String value = link.getElementsByTagName("value").item(0).getFirstChild().getNodeName();

			}
			is.close();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public String getAbbr(){
		return this.abbr;
	}
	
	public int getCode(){
		return this.code;
	}
	
	public String getValue(){
		return this.value;
	}
}
