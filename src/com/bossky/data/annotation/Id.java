package com.bossky.data.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Id {
	public String name() default "";
}
