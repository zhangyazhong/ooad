package com.github.zhangyazhong.aspect.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author zhangyazhong
 * @version 1.0
 */
public class CommonUtil {
    public static Method getSourceMethod(JoinPoint joinPoint) {
        Method proxyMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        try {
            return joinPoint.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Boolean contains(String[] strings, String string) {
        for (String s: strings) {
            if (s.equals(string)) {
                return true;
            }
        }
        return false;
    }
}
