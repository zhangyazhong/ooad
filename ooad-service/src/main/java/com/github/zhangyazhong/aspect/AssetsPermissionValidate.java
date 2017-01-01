package com.github.zhangyazhong.aspect;

import com.github.zhangyazhong.aspect.util.CommonUtil;
import com.github.zhangyazhong.common.exception.PermissionDeniedException;
import com.github.zhangyazhong.common.permission.AssetsPermission;
import com.github.zhangyazhong.common.session.Session;
import com.github.zhangyazhong.model.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Aspect
public class AssetsPermissionValidate {
    @Pointcut("execution(public * com.github.zhangyazhong.service.IAssetsService.*(..))")
    private void aspectJMethod() {
    }
    
    @Before("aspectJMethod()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        Method sourceMethod = CommonUtil.getSourceMethod(joinPoint);
        if (sourceMethod != null) {
            AssetsPermission permission = sourceMethod.getAnnotation(AssetsPermission.class);
            if (permission != null) {
                String[] roles = permission.value();
                Employee employee = Session.getAttribute("employee");
                if (employee == null || !CommonUtil.contains(roles, employee.getRole().getDescription())) {
                    throw new PermissionDeniedException("抱歉，您无权操作");
                }
            }
        }
    }
}
