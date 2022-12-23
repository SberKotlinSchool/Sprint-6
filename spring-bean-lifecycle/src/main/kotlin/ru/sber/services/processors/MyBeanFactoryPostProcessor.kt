package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.beanDefinitionNames.forEach {
            val currentBean = beanFactory.getBeanDefinition(it)
            val className = currentBean.beanClassName
            if (className != null) {
                Class.forName(className).interfaces.forEach { cl ->
                    val methodName = cl.methods.find { method ->
                        method.isAnnotationPresent(PostConstruct::class.java)
                    }
                    currentBean.initMethodName = methodName?.name
                }
            }
        }
    }
}
