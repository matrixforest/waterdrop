package com.matrixdroplet.waterdrop.web.view;

import com.matrixdroplet.waterdrop.ioc.ApplicationContext;
import com.matrixdroplet.waterdrop.ioc.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by li on 2016/4/14.
 */
public class DefaultViewResolve implements ViewResolve{
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultViewResolve.class);
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Config config;

    public DefaultViewResolve(HttpServletRequest request, HttpServletResponse response){
        this.request=request;
        this.response=response;
        ApplicationContext applicationContext= (ApplicationContext) request.getSession().getServletContext().getAttribute("ApplicationContext");
        this.config=applicationContext.getConfig();
    }

    public void resolve(View view){
        try {
            String viewPath = view.getView();
            request.getRequestDispatcher(config.getView_pref()+viewPath+config.getView_suffix()).forward(request,response);
        }  catch (Exception e) {
            LOGGER.warn("视图[{}]解析出错",view.getView());
            e.printStackTrace();
        }
    }

}
