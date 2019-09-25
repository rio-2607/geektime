package com.beautyboss.slogen.easy.client;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyClient {

    String name() default "";

    String qualifier() default "";

    double readTimeout() default  10;

    boolean snake() default  false;

    String url() ;

    Class<?>[] configuration() default {};
}
