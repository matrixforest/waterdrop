package com.matrixdroplet.waterdrop.web.handle;

import com.google.common.collect.Lists;
import com.matrixdroplet.waterdrop.web.model.Model;
import com.matrixdroplet.waterdrop.web.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by li on 2016/4/14.
 */
public class RequestHandler implements Handler{
    private MethodInvoke methodInvoke;
    private Method method;

    public RequestHandler(Object object, Method method) {
        methodInvoke = new MethodInvoke(object, method);
        this.method = method;
    }

    public View handle(HttpServletRequest request, HttpServletResponse response, Model model) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<Object> argsList = Lists.newArrayList();
        for (Class<?> parameterType : parameterTypes) {
            if (HttpServletRequest.class.getName().equals(parameterType.getName())) {
                argsList.add(request);
            }
            if (HttpServletResponse.class.getName().equals(parameterType.getName())) {
                argsList.add(response);
            }
            if (Model.class.getName().equals(parameterType.getName())) {
                argsList.add(model);
            }
        }
        String view = methodInvoke.invoke(argsList.toArray());
        return new View(view);
    }

    public View handle(Object[] args) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<Object> argsList = Lists.newArrayList();
        for (Class<?> parameterType : parameterTypes) {
            for (Object arg : args) {
                //此处调用存在问题
                if (arg.getClass().getName().equals(parameterType.getName())) {
                    argsList.add(arg);
                }
            }
        }
        String view = methodInvoke.invoke(argsList.toArray());
        return new View(view);
    }

    public View handle(HttpServletRequest request, HttpServletResponse response) {
        return handle(request, response,new Model(request));
    }
}
