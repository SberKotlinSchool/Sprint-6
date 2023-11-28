package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils


@Component
class MyBeanPostProcessor : BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        bean.setFieldValue("postProcessBeforeInitializationOrderMessage",
                "postProcessBeforeInitialization() is called")
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        bean.setFieldValue("postProcessAfterInitializationOrderMessage",
                "postProcessAfterInitialization() is called")
        return bean
    }
}

fun Any.setFieldValue(name: String, value: String) {
    val fields = this.javaClass.declaredFields
    fields.iterator().forEach { field ->
        if (field.name.equals(name)) {
            field.isAccessible = true
            ReflectionUtils.setField(field, this, value)
        }
    }
}