package com.matrixdroplet.waterdrop.ioc.injector;

import com.google.common.base.Preconditions;
import com.matrixdroplet.waterdrop.ioc.Ioc;
import com.matrixdroplet.waterdrop.ioc.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by li on 2016/4/13.
 */
public class FiledInjector implements Injector {
    private static final Logger logger = LoggerFactory.getLogger(FiledInjector.class);
    private Ioc ioc;
    private Field field;

    public FiledInjector(Ioc ioc, Field field) {
        this.ioc = ioc;
        this.field = field;
    }

    public void inject(Object bean) {
        try {
            Autowired annotation = field.getAnnotation(Autowired.class);
            String name = annotation.value();
            Object value = null;
            if (!name.equals("")) {
                value = ioc.getBean(name);
            } else {
                Class<?> filedType = field.getType();
                value = ioc.getBean(filedType);
            }
            Preconditions.checkNotNull(value);
            field.setAccessible(true);
            field.set(bean, value);
        } catch (Exception e) {
            logger.error("{}-{}依赖注入失败:[{}]",bean.getClass().getName(),field.getName(),e.getMessage());
            e.printStackTrace();
        }
    }

}
