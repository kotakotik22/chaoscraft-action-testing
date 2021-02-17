package com.kotakotik.chaoscraftfabric.chaos_event_stuff;

import com.kotakotik.chaoscraftfabric.ChaoscraftFabric;
import com.kotakotik.chaoscraftfabric.utils.CCReflections;
import org.reflections.Reflections;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

public class ChaosEventRegister {
    public static final List<Class<ChaosEvent>> eventClasses = new ArrayList<>();
    public static final List<ChaosEvent> events = new ArrayList<>();

    static {
        Set<Class<? extends ChaosEvent>> implem = CCReflections.getSubTypesOf(ChaosEvent.class);
        Set<Class<?>> annot = CCReflections.getTypesAnnotatedWith(ChaosEventReg.class);
        List<Class<?>> classes = new ArrayList<>(annot);
        classes.addAll(implem);
        for(Class<?> clazz : classes) {
            if(ChaosEvent.class.isAssignableFrom(clazz)) {
                eventClasses.add((Class<ChaosEvent>) clazz);
                try {
                    events.add((ChaosEvent) clazz.getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } else {
                ChaoscraftFabric.log.warn(String.format("class %s is annoted with ChaosEventReg but does not implement ChaosEvent (%s)", clazz.getSimpleName(), clazz.getName()));
            }
        }
    }
}
