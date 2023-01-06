package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class MyBeanPostProcessor : BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (beanName == "combinedBean") {
            bean::class.java.getDeclaredField("postProcessBeforeInitializationOrderMessage").let { field ->
                field.isAccessible = true
                field.set(bean, "postProcessBeforeInitialization() is called")
            }
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (beanName == "combinedBean") {
            bean::class.java.getDeclaredField("postProcessAfterInitializationOrderMessage").let { field ->
                field.isAccessible = true
                field.set(bean, "postProcessAfterInitialization() is called")
            }
        }
        return bean
    }
}