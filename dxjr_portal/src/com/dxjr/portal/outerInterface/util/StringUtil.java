package com.dxjr.portal.outerInterface.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.dxjr.portal.outerInterface.vo.WdtyCnd;



public class StringUtil {
	
////------------------验证方法        
    /**  
     * 判断字段是否为空 符合返回ture  
     * @param str  
     * @return boolean  
     */  
    public static synchronized boolean StrisNull(String str) {   
        return null == str || str.trim().length() <= 0 ? true : false ;   
    }   
    /**  
     * 判断字段是非空 符合返回ture  
     * @param str  
     * @return boolean  
     */  
    public static  boolean StrNotNull(String str) {   
        return !StrisNull(str) ;   
    }   
    /**  
     * 字符串null转空  
     * @param str  
     * @return boolean  
     */  
    public static  String nulltoStr(String str) {   
        return StrisNull(str)?"":str;   
    }      
    /**  
     * 字符串null赋值默认值   
     * @param str    目标字符串  
     * @param defaut 默认值  
     * @return String  
     */  
    public static  String nulltoStr(String str,String defaut) {   
        return StrisNull(str)?defaut:str;   
    }   
    
    public static String toString(Object obj){
    	return obj==null?"":obj.toString();
    }
    
    public static boolean isEmpty(Object obj){
    	return obj==null || "".equals(obj);
    }
    
    
    /**
	 * 参数做一个字母小写升序排序
	 * @param strParam
	 * @return
	 */
	public static String paramSort(String strParam){
		if(null != strParam){
			String[] str = strParam.split("&");
			StringBuilder sb = new StringBuilder();
			if(null != str){
				List<String> list = new ArrayList<String>();
				Map<String, String> map = new HashMap<String, String>();
				for(int i=0;i<str.length;i++){
					String str1[] = str[i].split("=");
					if(null != str1){
						if(str1.length>1){
							map.put(str1[0], str1[1]);
						}else{
							map.put(str1[0], "");
						}
						list.add(str1[0].toLowerCase()); // 全部转成小写字母
					}
				}
				// 排序后
				list = StringUtil.sort(list);
				for(int i=0;i<list.size();i++){
					sb.append(list.get(i)+"="+map.get(list.get(i)));
					if(list.size()-1 != i){
						sb.append("&");
					}
				}
			}else{
				return null;
			}
			return sb.toString();
		}
		return null;
	}
	
	/**
	 * 按字母升序排序 ac,ba,c,d
	 * @param list
	 * @return
	 */
	public static List<String> sort(List<String> list){
		Collections.sort(list);
		return list;
	}
	
	
	/**
	 * 得到参数数据封装到Map
	 * @param strParam
	 * @return
	 */
	public static Map<String,String> paramMap(String strParam){
		Map<String,String> map = new TreeMap<String,String>();
		if(null != strParam){
			String[] str = strParam.split("&");
			if(null != str){
				for(int i=0;i<str.length;i++){
					String str1[] = str[i].split("=");
					if(null != str1){
						if(str1.length>1){
							map.put(str1[0], str1[1]);
						}else{
							map.put(str1[0], "");
						}
					}
				}
				return map;
			}
		}
		return null;
	}
	
	/**
	 * 获取自动生成的uuid
	 * @author WangQianJin
	 * @title @return
	 * @date 2015年12月16日
	 */
	public static String getUuid(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取标ID和类型
	 * @author WangQianJin
	 * @title @param loadId
	 * @title @return
	 * @date 2016年1月6日
	 */
	public static WdtyCnd getLoadIdAndType(String loadId){
		WdtyCnd wdtyCnd=new WdtyCnd();
		// 债权转让项目
		if (loadId.contains("_")) {
			final String[] string = loadId.split("_");
			if (null != string && string.length == 2) {
				wdtyCnd.setBorrowId(string[1]);
				wdtyCnd.setBorrowType(2);
			}
		}else{
			wdtyCnd.setBorrowId(loadId);
			if(loadId.length()>=9){				
				wdtyCnd.setBorrowType(3);
			}else{				
				wdtyCnd.setBorrowType(1);
			}
		}
		return wdtyCnd;		
	}
}
