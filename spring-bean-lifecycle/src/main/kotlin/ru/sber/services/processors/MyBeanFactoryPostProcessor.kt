package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.beanDefinitionNames.forEach { name ->
            val beanDefinition = beanFactory.getBeanDefinition(name)
            beanDefinition.beanClassName?.let {
                Class.forName(it)
                        .interfaces
                        .map { it.methods.find { it.isAnnotationPresent(PostConstruct::class.java) } }
                        .firstOrNull()
                        ?.let {
                            beanDefinition.initMethodName = it.name
                        }
            }
        }
    }
}