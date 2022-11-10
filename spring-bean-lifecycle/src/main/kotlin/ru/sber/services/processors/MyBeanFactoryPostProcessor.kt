package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val beanDefinitionNames = beanFactory.beanDefinitionNames
        beanDefinitionNames.forEach { beanName ->
            val beanDefinition = beanFactory.getBeanDefinition(beanName)
            beanDefinition.beanClassName?.let {
                val postConstructMethod = Class.forName(it)
                    .interfaces
                    .map { i -> i.methods.find { it.isAnnotationPresent(PostConstruct::class.java) } }
                    .firstOrNull()
                if (postConstructMethod != null) {
                    beanDefinition.initMethodName = postConstructMethod.name
                }
            }
        }
    }
}

