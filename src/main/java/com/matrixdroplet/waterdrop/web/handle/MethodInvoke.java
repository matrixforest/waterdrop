package com.matrixdroplet.waterdrop.web.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by li on 2016/4/14.
 */
public class MethodInvoke {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodInvoke.class);
    private Method method;
    private Object object;

    public MethodInvoke(Object object, Method method) {
        this.method = method;
        this.object = object;
    }

    public String invoke(Object[] args) {
        String view = "";
        try {
            view = (String) method.invoke(object, args);
        } catch (Exception e) {
            LOGGER.warn("调用方法出错:{}", e.getMessage());
            e.printStackTrace();
        }
        return view;
    }
}
