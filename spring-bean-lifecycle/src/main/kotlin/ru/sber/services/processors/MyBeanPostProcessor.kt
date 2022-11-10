package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ru.sber.services.CombinedBean

@Component
class MyBeanPostProcessor : BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is CombinedBean)
            (bean as? CombinedBean)?.postProcessBeforeInitializationOrderMessage = "postProcessBeforeInitialization() is called"
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is CombinedBean)
            (bean as? CombinedBean)?.postProcessAfterInitializationOrderMessage = "postProcessAfterInitialization() is called"
        return bean
    }
}