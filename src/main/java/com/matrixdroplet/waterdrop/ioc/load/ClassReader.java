package com.matrixdroplet.waterdrop.ioc.load;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by li on 2016/4/13.
 */
public interface ClassReader {
    public Set<Class<?>> getClass(String packageName, boolean recursive);

    public Set<Class<?>> getClassByAnnotation(String packageName, Class<? extends Annotation> annotation, boolean recursive);
}
