package com.solomko.Spring;

import com.solomko.SQL.SqlHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SqlHelper.initDB();
        SpringContext.getApplicationContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
