package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.beanDefinitionNames.forEach {
            val bean = beanFactory.getBeanDefinition(it)
            bean.beanClassName?.let { className ->
                Class.forName(className)
                        .interfaces
                        .forEach { clazz ->
                            clazz.methods.find { method ->
                                method.isAnnotationPresent(PostConstruct::class.java)
                            }?.let { methodName ->
                                bean.initMethodName = methodName.name
                            }
                        }
            }
        }
    }
}