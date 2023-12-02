package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ru.sber.services.CombinedBean


@Component
class MyBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        val message = "postProcessBeforeInitialization() is called"
        if (bean is CombinedBean) {
            bean.postProcessBeforeInitializationOrderMessage = message
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val message = "postProcessAfterInitialization() is called"
        if (bean is CombinedBean) {
            bean.postProcessAfterInitializationOrderMessage = message
        }
        return bean
    }
}