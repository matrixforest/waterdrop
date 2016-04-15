package com.matrixdroplet.waterdrop.web.handle;

import com.google.common.base.Preconditions;
import com.matrixdroplet.waterdrop.ioc.ApplicationContext;
import com.matrixdroplet.waterdrop.ioc.config.Config;
import com.matrixdroplet.waterdrop.web.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by li on 2016/4/15.
 */
public class StaticFileHandler implements Handler{
    private Config config;

    public View handle(HttpServletRequest request, HttpServletResponse response) {
        ApplicationContext applicationContext = (ApplicationContext) request.getSession().getServletContext().getAttribute("ApplicationContext");
        Preconditions.checkNotNull(applicationContext);
        this.config=applicationContext.getConfig();
        String[] staticFilePaths = config.getStaticFilePaths();
        String requestURI = request.getRequestURI();
        boolean isStatic=false;
        for (String staticFilePath : staticFilePaths) {
            if (requestURI.startsWith(staticFilePath.substring(0,staticFilePath.length()-2))){
                isStatic=true;
                break;
            }
        }
        return new View(request.getRequestURI(),isStatic);
    }
}
