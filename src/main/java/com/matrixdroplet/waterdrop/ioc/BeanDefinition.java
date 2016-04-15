package com.matrixdroplet.waterdrop.ioc;

/**
 * Created by li on 2016/4/13.
 */
public class BeanDefinition {
    private Object bean;
    private Class<?> type;
    private boolean isSingleton=true;

    public BeanDefinition(Object bean,Class<?> type){
        this.bean=bean;
        this.type=type;
    }

    public Object getBean() {
        return bean;
    }

    public BeanDefinition setBean(Object bean) {
        this.bean = bean;
        return this;
    }

    public Class<?> getType() {
        return type;
    }

    public BeanDefinition setType(Class<?> type) {
        this.type = type;
        return this;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public BeanDefinition setSingleton(boolean singleton) {
        isSingleton = singleton;
        return this;
    }
}
