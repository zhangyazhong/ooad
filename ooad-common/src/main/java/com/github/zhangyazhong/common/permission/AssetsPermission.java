package com.github.zhangyazhong.common.permission;

import java.lang.annotation.*;

/**
 * @author zhangyazhong
 * @version 1.0
 */

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AssetsPermission {
    String[] value() default {};
}
