package com.matrixdroplet.waterdrop.ioc;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 2016/4/13.
 */
public class SimpleIoc implements Ioc {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ioc.class);
    private final Map<String, BeanDefinition> pool = Maps.newHashMap();

    public void addBeanDefinition(BeanDefinition beanDefinition) {
        Class<?>[] interfaces = beanDefinition.getType().getInterfaces();
        for (Class<?> anInterface : interfaces) {
            this.addBeanDefinition(anInterface.getName(),beanDefinition);
        }
        this.addBeanDefinition(beanDefinition.getType().getName(),beanDefinition);
    }

    public void addBeanDefinition(String name, BeanDefinition beanDefinition) {
        pool.put(name,beanDefinition);
        LOGGER.info("addBean[{}] to Ioc pool",name);
    }

    public Object getBean(String name) {
        return pool.get(name).getBean();
    }

    public <T> T getBean(Class<T> type) {
        return (T) pool.get(type.getName()).getBean();
    }

    public BeanDefinition getBeanDifinition(String name) {
        return pool.get(name);
    }

    public List<BeanDefinition> getBeanDifinitions() {
        return new ArrayList<BeanDefinition>(pool.values());
    }

    public List<Object> getBeans() {
        ArrayList<BeanDefinition> bds = new ArrayList<BeanDefinition>(pool.values());
        //thanks guava
        List<Object> beans = Lists.transform(bds, new Function<BeanDefinition, Object>() {
            public Object apply(BeanDefinition input) {
                return input.getBean();
            }
        });
        return beans;
    }

    public List<String> getBeanNames() {
        return new ArrayList<String>(pool.keySet());
    }
}
