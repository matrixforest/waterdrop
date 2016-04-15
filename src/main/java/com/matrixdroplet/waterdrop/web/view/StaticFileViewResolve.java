package com.matrixdroplet.waterdrop.web.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by li on 2016/4/15.
 */
public class StaticFileViewResolve implements ViewResolve{
    private static final Logger LOGGER = LoggerFactory.getLogger(StaticFileViewResolve.class);
    private HttpServletRequest request;
    private HttpServletResponse response;

    public StaticFileViewResolve(HttpServletRequest request, HttpServletResponse response){
        this.request=request;
        this.response=response;
    }

    public void resolve(View view) {
        try {
            request.getRequestDispatcher(view.getView()).forward(request, response);
        } catch (ServletException e) {
            LOGGER.warn("静态资源请求失败:{}",e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.warn("静态资源请求失败:{}",e.getMessage());
            e.printStackTrace();
        }
    }

}
