package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.beanDefinitionNames
            .map { beanFactory.getBeanDefinition(it) }
            .filter { beanDefinition -> !beanDefinition.beanClassName.isNullOrBlank() }
            .forEach { beanDefinition ->
                Class
                    .forName(beanDefinition.beanClassName)
                    .interfaces
                    .flatMap { it.methods.toList() }
                    .find { it.isAnnotationPresent(PostConstruct::class.java) }
                    .let { beanDefinition.initMethodName = it?.name }
            }
    }
}