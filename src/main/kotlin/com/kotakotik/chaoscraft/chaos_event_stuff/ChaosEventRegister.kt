package com.kotakotik.chaoscraft.chaos_event_stuff

import com.kotakotik.chaoscraft.ChaoscraftKotlin
import com.kotakotik.chaoscraft.utils.CCReflections
import java.lang.reflect.InvocationTargetException

import java.util.ArrayList


object ChaosEventRegister {
    val eventClasses: MutableList<Class<ChaosEvent>> = ArrayList()
    val events: MutableList<ChaosEvent> = ArrayList()

    fun reg() {
        ChaoscraftKotlin.log.info("registering events",)
        val implem: Set<Class<out ChaosEvent>> = CCReflections.getSubTypesOf(ChaosEvent::class.java)
        val annot: Set<Class<*>> = CCReflections.getTypesAnnotatedWith(ChaosEventReg::class.java)
        val classes: MutableList<Class<*>> = ArrayList(annot)
        classes.addAll(implem)
        for (clazz in classes) {
            if (ChaosEvent::class.java.isAssignableFrom(clazz)) {
                eventClasses.add(clazz as Class<ChaosEvent>)
                var name: String = "?"
                try {
                    val ev: ChaosEvent = clazz.getConstructor().newInstance() as ChaosEvent
                    name = ev.getId()
                    events.add(ev)
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                }
                ChaoscraftKotlin.log.info("registered event ${name} (${clazz.name})")
            } else {
                ChaoscraftKotlin.log.warn("class ${clazz.simpleName} is annotated with ChaosEventReg but does not implement ChaosEvent (${clazz.name})")
            }
        }
        ChaoscraftKotlin.log.info("registered ${events.size} events",)
    }
}