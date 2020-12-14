package com.example.demo.config.sharding.aop;

import java.util.HashMap;
import java.util.Map;

public class RequestHolder {

    private static final ThreadLocal<Map<String,Object>> requestHolder = new ThreadLocal<Map<String,Object>>();

    public static void add(String signiture,Object param) {
    	Map<String,Object> map = requestHolder.get();
    	if(map == null) {
    		map = new HashMap<String, Object>();
    	}
    	map.put(signiture, param);
        requestHolder.set(map);
    }


    public static Map<String,Object> getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
    }
}
