package com.matrixdroplet.waterdrop.web.load;

import com.matrixdroplet.waterdrop.ioc.BeanDefinition;
import com.matrixdroplet.waterdrop.ioc.Ioc;
import com.matrixdroplet.waterdrop.web.annotation.RequestMapping;
import com.matrixdroplet.waterdrop.web.handle.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by li on 2016/4/14.
 */
public class RequestRegister implements Register {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestRegister.class);

    public void register(Ioc ioc, RequestMappingPool requestMappingPool) {
        List<BeanDefinition> beanDifinitions = ioc.getBeanDifinitions();
        for (BeanDefinition beanDifinition : beanDifinitions) {
            Class<?> aClass = beanDifinition.getType();
            if (aClass.isInterface()) continue;
            //类级@RequestMapping注解
            String baseUrl = "";
            RequestMapping rmAnnotation = aClass.getAnnotation(RequestMapping.class);
            if (rmAnnotation != null) {
                baseUrl = rmAnnotation.value();
            }
            //方法级@RequestMapping注解
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String url = baseUrl + methodAnnotation.value();
                    if (methodAnnotation.method().equals("")) {
                        requestMappingPool.setRequestHandler("{{get}}" + url, new RequestHandler(beanDifinition.getBean(), method));
                        requestMappingPool.setRequestHandler("{{post}}" + url, new RequestHandler(beanDifinition.getBean(), method));
                    }
                    if(methodAnnotation.method().toLowerCase().equals("get")){
                        requestMappingPool.setRequestHandler("{{get}}" + url, new RequestHandler(beanDifinition.getBean(), method));
                    }
                    if(methodAnnotation.method().toLowerCase().equals("post")){
                        requestMappingPool.setRequestHandler("{{post}}" + url, new RequestHandler(beanDifinition.getBean(), method));
                    }
                }
            }
        }
    }
}
