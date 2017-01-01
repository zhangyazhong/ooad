package com.github.zhangyazhong.aspect;

import com.github.zhangyazhong.aspect.util.CommonUtil;
import com.github.zhangyazhong.common.exception.PermissionDeniedException;
import com.github.zhangyazhong.common.permission.SparePermission;
import com.github.zhangyazhong.common.session.Session;
import com.github.zhangyazhong.model.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Aspect
public class SparePermissionValidate {
    @Pointcut("execution(public * com.github.zhangyazhong.service.ISpareService.*(..))")
    private void aspectJMethod() {
    }
    
    @Before("aspectJMethod()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        Method sourceMethod = CommonUtil.getSourceMethod(joinPoint);
        if (sourceMethod != null) {
            SparePermission permission = sourceMethod.getAnnotation(SparePermission.class);
            if (permission != null) {
                Employee employee = Session.getAttribute("employee");
                String[] roles = permission.value();
                if (employee == null || !CommonUtil.contains(roles, employee.getRole().getDescription())) {
                    throw new PermissionDeniedException("抱歉，您无权操作");
                }
            }
        }
    }
}
