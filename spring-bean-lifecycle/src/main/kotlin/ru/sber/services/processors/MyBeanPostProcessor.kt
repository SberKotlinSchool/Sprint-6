package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ru.sber.services.CombinedBean

@Component
class MyBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is CombinedBean) {
            bean.customInit()
            bean.postProcessBeforeInitializationOrderMessage = "postProcessBeforeInitialization() is called"
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is CombinedBean) {
            bean.afterPropertiesSet()
            bean.postProcessAfterInitializationOrderMessage = "postProcessAfterInitialization() is called"
            bean.postConstruct()
        }

        return bean
    }
}