package com.kotakotik.chaoscraft.utils

import com.kotakotik.chaoscraft.ChaoscraftKotlin
import org.reflections.Reflections

object CCReflections {
    val reflections: Reflections = Reflections(ChaoscraftKotlin::class.java.getPackage().getName())
    fun <T> getSubTypesOf(type: Class<T>?): Set<Class<out T>> {
        return reflections.getSubTypesOf(type)
    }

    fun getTypesAnnotatedWith(annotation: Annotation?): Set<Class<*>> {
        return reflections.getTypesAnnotatedWith(annotation, true)
    }

    fun getTypesAnnotatedWith(annotation: Class<out Annotation?>?): Set<Class<*>> {
        return reflections.getTypesAnnotatedWith(annotation, true)
    }
}