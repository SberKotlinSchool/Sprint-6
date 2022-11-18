package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ru.sber.services.CombinedBean

@Component
class MyBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (beanName == "combinedBean") {
            bean as CombinedBean
            bean.postProcessBeforeInitializationOrderMessage = "postProcessBeforeInitialization() is called"
//            bean.init()
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (beanName == "combinedBean") {
            bean as CombinedBean
            bean.postProcessAfterInitializationOrderMessage = "postProcessAfterInitialization() is called"
//            bean.init()
        }
        return bean
    }
}