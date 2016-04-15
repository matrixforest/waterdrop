package com.matrixdroplet.waterdrop.ioc;

import java.util.List;

/**
 * Created by li on 2016/4/13.
 */
public interface Ioc {

    void addBeanDefinition(BeanDefinition beanDefinition);

    void addBeanDefinition(String name,BeanDefinition beanDefinition);

    Object getBean(String name);

    <T> T getBean(Class<T> type);

    BeanDefinition getBeanDifinition(String name);

    List<BeanDefinition> getBeanDifinitions();

    List<Object> getBeans();

    List<String> getBeanNames();

}
