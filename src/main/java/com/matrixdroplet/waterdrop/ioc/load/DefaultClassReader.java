package com.matrixdroplet.waterdrop.ioc.load;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Set;

/**
 * Created by li on 2016/4/13.
 */
public class DefaultClassReader implements ClassReader {

        private static final Logger LOGGER =LoggerFactory.getLogger(DefaultClassReader.class);

    public Set<Class<?>> getClass(String packageName, boolean recursive) {
        return getClassByAnnotation(packageName, null, recursive);
    }

    /**
     * 通过注解获取class
     *
     * @param packageName
     * @param annotation
     * @param recursive
     * @return
     */
    public Set<Class<?>> getClassByAnnotation(String packageName, Class<? extends Annotation> annotation, boolean recursive) {
        Preconditions.checkNotNull(packageName);
        Set<Class<?>> classes = Sets.newHashSet();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                //包的物理路径
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                Set<Class<?>> classByPackage = findClassByPackage(packageName, filePath, annotation, recursive, classes);
                if(classByPackage!=null&&classByPackage.size()>0){
                    classes.addAll(classByPackage);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("扫描包路径出错");
            e.printStackTrace();
        }

        return classes;
    }

    private Set<Class<?>> findClassByPackage(final String packageName, final String packagePath, final Class<? extends Annotation> annotation, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if ((!dir.exists()) || (!dir.isDirectory())) {
            LOGGER.warn("The package [{}] not found.", packageName);
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = accept(dir, recursive);
        if(dirfiles!=null&&dirfiles.length>0){
            for (File dirfile : dirfiles) {
                if(dirfile.isDirectory()){
                    findClassByPackage(packageName+"."+dirfile.getName(),dirfile.getAbsolutePath(),annotation,recursive,classes);
                }else {
                    //截掉.class
                    String className=dirfile.getName().substring(0,dirfile.getName().length()-6);
                    try {
                        Class<?> clazz = Class.forName(packageName+"." + className);
                        if(annotation!=null){
                            if(clazz.getAnnotation(annotation)!=null){
                                classes.add(clazz);
                            }
                            continue;
                        }
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        LOGGER.error("扫描自定义包路径时出错，找不到.class类文件：" + e.getMessage());
                    }
                }
            }
        }
        return classes;
    }

    private File[] accept(File dir, final boolean recursive){
        File[] files = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return (pathname.isDirectory() && recursive) || (pathname.getName().endsWith(".class"));
            }
        });
        return files;
    }
}
