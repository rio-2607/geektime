package com.beautyboss.slogen.easy.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({EasyAutoConfiguration.class, EasyClientsRegistrar.class})
public @interface EnableEasyClients {

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

}
