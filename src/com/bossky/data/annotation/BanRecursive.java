package com.bossky.data.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 禁止递归
 * 
 * @author bo
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface BanRecursive {

}
