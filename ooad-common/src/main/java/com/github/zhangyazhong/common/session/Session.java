package com.github.zhangyazhong.common.session;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class Session {
    private static Map<String, Object> sessionMap;
    
    static {
        sessionMap = Maps.newHashMap();
    }
    
    public static <T> T getAttribute(String key) {
        if (sessionMap.containsKey(key)) {
            return (T) sessionMap.get(key);
        }
        return null;
    }
    
    public static <T> void setAttribute(String key, T t) {
        sessionMap.put(key, t);
    }
    
    public static void removeAttribute(String key) {
        sessionMap.remove(key);
    }
}
