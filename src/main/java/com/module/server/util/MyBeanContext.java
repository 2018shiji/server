package com.module.server.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class MyBeanContext implements ApplicationContextAware, ResourceLoaderAware {

    /** Spring容器注入 **/
    private static ApplicationContext applicationContext;
    private static ResourceLoader resourceLoader;

    private static ResourcePatternResolver resolver;
    private static MetadataReaderFactory readerFactory;
    private static MetadataReader reader;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MyBeanContext.applicationContext = applicationContext;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /** 获取指定包路径（相对类路径）下所有的类 **/
    public static Set<Class<?>> getPackageClasses(String packagePath) throws IOException {

        Set<Class<?>> classes = new HashSet<>();
        String finalPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                .concat(ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(packagePath)))
                .concat("/**/*.class");

        resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        readerFactory = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources = resolver.getResources(finalPath);

        Arrays.stream(resources)
                .filter(resource -> resource.isReadable())

                .filter(resource -> {
                    try{
                        reader = readerFactory.getMetadataReader(resource);
                    }catch(IOException e){ e.printStackTrace(); }

                    return reader.getClassMetadata().isConcrete(); })

                .forEach((resource) -> {
                    try {
                        classes.add(Class.forName(reader.getClassMetadata().getClassName()));
                    } catch (ClassNotFoundException e) { e.printStackTrace(); }
                });
        return classes;
    }


}
