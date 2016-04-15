package com.matrixdroplet.waterdrop.web.model;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by li on 2016/4/14.
 */
public class Model {
    private HttpServletRequest request;

    public Model(HttpServletRequest request){
        this.request=request;
    }

    public Object getAttribute(String key) {
        return this.request.getAttribute(key);
    }

    public void addAttribute(String key,Object value) {
        this.request.setAttribute(key,value);
    }

    public void removeAttribute(String key) {
        this.request.removeAttribute(key);
    }
}
