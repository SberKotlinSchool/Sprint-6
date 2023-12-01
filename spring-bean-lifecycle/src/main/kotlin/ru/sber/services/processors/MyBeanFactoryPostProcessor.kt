package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.beanDefinitionNames.forEach { beanName ->
            val bean = beanFactory.getBeanDefinition(beanName)
            val className = bean.beanClassName
            className?.let {
                Class.forName(it).interfaces.forEach { i ->
                    val method = i.methods.find { method -> method.isAnnotationPresent(PostConstruct::class.java) }
                    method?.apply { bean.initMethodName = name }
                }
            }
        }
    }
}