package com.kotakotik.chaoscraftfabric.utils;

import com.kotakotik.chaoscraftfabric.ChaoscraftFabric;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

import static org.reflections.ReflectionUtils.forNames;
import static org.reflections.ReflectionUtils.withAnnotation;
import static org.reflections.util.Utils.filter;
import static org.reflections.util.Utils.names;

public abstract class CCReflections {
    public static final Reflections reflections = new Reflections(ChaoscraftFabric.class.getPackage().getName());

    public static  <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
        return reflections.getSubTypesOf(type);
    }

    public static Set<Class<?>> getTypesAnnotatedWith(final Annotation annotation) {
        return reflections.getTypesAnnotatedWith(annotation, true);
    }

    public static Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation, true);
    }
}
