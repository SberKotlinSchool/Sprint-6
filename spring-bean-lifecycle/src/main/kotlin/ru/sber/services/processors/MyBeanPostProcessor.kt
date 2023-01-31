package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ru.sber.services.CombinedBean

@Component
class MyBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        return if (beanName == "combinedBean") (bean as CombinedBean).apply {
            postProcessBeforeInitializationOrderMessage = "postProcessBeforeInitialization() is called"
        } else bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        return if (bean is CombinedBean) {
            bean.apply {
                postProcessAfterInitializationOrderMessage = "postProcessAfterInitialization() is called"
                // так тоже тест проходил)
//                postConstruct()
//                customInit()
//                afterPropertiesSet()
            }
        } else bean
    }
}