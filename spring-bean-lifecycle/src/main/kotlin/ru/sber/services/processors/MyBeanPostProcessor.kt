package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.util.*

@Component
class MyBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean::class.simpleName == "CombinedBean") {
            init(bean, "postProcessBeforeInitializationOrderMessage",
                "postProcessBeforeInitialization() is called")
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean::class.simpleName == "CombinedBean") {
            init(bean, "postProcessAfterInitializationOrderMessage",
                "postProcessAfterInitialization() is called")
        }
        return bean
    }

    private fun init(bean: Any, fieldName: String, value: String) {
        val messageField = Arrays.stream(bean.javaClass.declaredFields)
            .filter { it.name ==  fieldName}.findFirst().get()

        if (messageField.trySetAccessible()) {
            messageField.set(bean, value)
        }
    }
}