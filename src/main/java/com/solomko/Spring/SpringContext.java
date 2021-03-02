package com.solomko.Spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {
    public static AnnotationConfigApplicationContext getApplicationContext(){
        return applicationContext;
    }

    private static AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
            AppConfig.class
    );
}

