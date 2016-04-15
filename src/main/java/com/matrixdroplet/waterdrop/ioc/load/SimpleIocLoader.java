package com.matrixdroplet.waterdrop.ioc.load;

import com.google.common.collect.Lists;
import com.matrixdroplet.waterdrop.ioc.BeanDefinition;
import com.matrixdroplet.waterdrop.ioc.Ioc;
import com.matrixdroplet.waterdrop.ioc.annotation.Component;
import com.matrixdroplet.waterdrop.ioc.annotation.Controller;
import com.matrixdroplet.waterdrop.ioc.annotation.Repository;
import com.matrixdroplet.waterdrop.ioc.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

/**
 * Created by li on 2016/4/13.
 */
public class SimpleIocLoader implements IocLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(IocLoader.class);

    private ClassReader classReader = new DefaultClassReader();
    List<Class<? extends Annotation>> annotations = Lists.newArrayList(Component.class, Controller.class, Service.class, Repository.class);
    private List<Class<?>> clazzs = Lists.newArrayList();

    public SimpleIocLoader(String[] packageNames) {
        List<String> packages=Lists.newArrayList("com.matrixdroplet.waterdrop.*");
        for (String packageName : packageNames) {
            packages.add(packageName);
        }
        //扫描自身组件
        clazzs.addAll(scanPackages(packages, annotations));
    }

    public List<Class<?>> scanPackages(List<String> packageNames, List<Class<? extends Annotation>> annotations) {
        List<Class<?>> clazzs = Lists.newArrayList();
        for (String packageName : packageNames) {
            boolean recursive = false;
            if (packageName.endsWith(".*")) {
                packageName = packageName.substring(0, packageName.length() - 2);
                recursive = true;
            }
            for (Class<? extends Annotation> annotation : annotations) {
                Set<Class<?>> clazzsByAnnotation = classReader.getClassByAnnotation(packageName, annotation, recursive);
                clazzs.addAll(clazzsByAnnotation);
            }
        }
        return clazzs;
    }

    /**
     * ioc容器加载bean
     *
     * @param ioc
     */
    public void load(Ioc ioc) {
        for (Class<?> clazz : clazzs) {
            for (Class<? extends Annotation> annotation : annotations) {
                Annotation theAnnotation = clazz.getAnnotation(annotation);
                if (theAnnotation != null) {
                    try {
                        String name = "";
                        if (theAnnotation instanceof Component) name = ((Component) theAnnotation).value();
                        if (theAnnotation instanceof Controller) name = ((Controller) theAnnotation).value();
                        if (theAnnotation instanceof Service) name = ((Service) theAnnotation).value();
                        if (theAnnotation instanceof Repository) name = ((Repository) theAnnotation).value();
                        Object bean = clazz.newInstance();
                        if (!name.equals("")) {
                            ioc.addBeanDefinition(name, new BeanDefinition(bean, clazz).setSingleton(true));
                        } else {
                            ioc.addBeanDefinition(new BeanDefinition(bean, clazz).setSingleton(true));
                        }

                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        clazzs = null;
    }
}
