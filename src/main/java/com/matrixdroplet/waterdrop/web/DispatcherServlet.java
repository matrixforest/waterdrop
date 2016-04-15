package com.matrixdroplet.waterdrop.web;

import com.matrixdroplet.waterdrop.ioc.ApplicationContext;
import com.matrixdroplet.waterdrop.web.handle.RequestHandler;
import com.matrixdroplet.waterdrop.web.load.RequestMappingPool;
import com.matrixdroplet.waterdrop.web.view.DefaultViewResolve;
import com.matrixdroplet.waterdrop.web.view.View;
import com.matrixdroplet.waterdrop.web.view.ViewResolve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by li on 2016/4/14.
 */
public class DispatcherServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp, "{{get}}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp, "{{post}}");
    }

    //请求下发
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response, String method) {
        try {
            ApplicationContext applicationContext = this.getApplicationContext(request);

            /*//静态资源拦截(该实现存在问题，转发请求会被再次拦截)
            View viewByStaticHandler = new StaticFileHandler().handle(request, response);
            if(viewByStaticHandler.getIsStaticFile()){
                new StaticFileViewResolve(request, response).resolve(viewByStaticHandler);
                return;
            }*/


            RequestMappingPool requestMappingPool = applicationContext.getRequestMappingPool();
            RequestHandler requestHander = requestMappingPool.getRequestHander(method + request.getServletPath());
            if(requestHander==null){
                LOGGER.warn("请求[{}]未匹配到对应的RequestHandler",request.getRequestURL().toString());
                return;
            }
            View view = requestHander.handle(request, response);
            ViewResolve viewResolve = new DefaultViewResolve(request, response);
            viewResolve.resolve(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApplicationContext getApplicationContext(HttpServletRequest request) {
        return (ApplicationContext) request.getSession().getServletContext().getAttribute("ApplicationContext");
    }
}
