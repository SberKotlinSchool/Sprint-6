package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import ru.sber.services.CombinedBean

@Component
class MyBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (beanName == "combinedBean" && bean is CombinedBean) {
            bean.postProcessBeforeInitialization()
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (beanName == "combinedBean" && bean is CombinedBean) {
            bean.postProcessAfterInitialization()
        }
        return bean
    }
}