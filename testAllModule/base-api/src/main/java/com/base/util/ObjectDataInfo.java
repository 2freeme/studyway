package com.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectDataInfo {
	
	private static final Logger log = LoggerFactory.getLogger(ObjectDataInfo.class.getName());

	/** 
	 * 根据属性名获取属性值 
	 * */  
	   private static Object getFieldValueByName(String fieldName, Object o,Method[] methods) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
//	           Method method = o.getClass().getMethod(getter, new Class[] {}); 
	           if(!checkGetMet(methods,getter)){
	        	   return null;
	           }
	           Method method = o.getClass().getDeclaredMethod(getter, new Class[]{});
	           Object value = method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	           log.error(e.getMessage(),e);    
	           return null;    
	       }    
	   }   
	     
	   /** 
	    * 获取属性名数组 
	    * */  
	   private static String[] getFiledName(Object o){  
	    Field[] fields=o.getClass().getDeclaredFields();  
	        String[] fieldNames=new String[fields.length];  
	    for(int i=0;i<fields.length;i++){  
	        fieldNames[i]=fields[i].getName();  
	    }  
	    return fieldNames;  
	   }  
	     
	   /** 
	    * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list 
	    * */  
	   private static List<Map<String,Object>> getFiledsInfo(Object o,Method[] methods){  
	    Field[] fields=o.getClass().getDeclaredFields();  
	        String[] fieldNames=new String[fields.length];  
	        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();  
	        Map<String,Object> infoMap=null;  
	    for(int i=0;i<fields.length;i++){  
	        infoMap = new HashMap<String,Object>();  
	        infoMap.put("type", fields[i].getType().toString());  
	        infoMap.put("name", fields[i].getName());  
	        infoMap.put("value", getFieldValueByName(fields[i].getName(), o,methods));  
	        list.add(infoMap);  
	    }  
	    return list;  
	   }  
	     
	   /** 
	    * 获取对象的所有属性值，返回一个对象数组 
	    * */  
	   public static Object[] getFiledValues(Object o,Method[] methods){  
	    String[] fieldNames=getFiledName(o);  
	    Object[] value=new Object[fieldNames.length];
	    String strvlue="";
	    for(int i=0;i<fieldNames.length;i++){  
	        value[i]=getFieldValueByName(fieldNames[i], o,methods);
	        strvlue=strvlue+";"+String.valueOf(fieldNames[i])+":"+String.valueOf(value[i]);
	    }  
	    return value;  
	   } 
	   
	   
	   /** 
	     * 判断是否存在某属性的 get方法 
	     *  
	     * @param methods 
	     * @param fieldGetMet 
	     * @return boolean 
	     */  
	    public static boolean checkGetMet(Method[] methods, String fieldGetMet) {  
	        for (Method met : methods) {  
	            if (fieldGetMet.equals(met.getName())) {  
	                return true;  
	            }  
	        }  
	        return false;  
	    }  
	    
	   /** 
	    * 获取对象的所有属性值，返回一个对象字符串 
	    * */  
	  public static String getStrFiledValue(Object o){
	    Method[] methods = o.getClass().getDeclaredMethods();		  
	    String[] fieldNames=getFiledName(o);  
	    Object[] value=new Object[fieldNames.length];
	    String strvlue="";
	    for(int i=0;i<fieldNames.length;i++){  
	        value[i]=getFieldValueByName(fieldNames[i], o,methods);
	        strvlue=strvlue+";"+String.valueOf(fieldNames[i])+":"+String.valueOf(value[i]);
	    }  
	    return strvlue;  
	   } 
	   
	   
 
	
}
