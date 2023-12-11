package ru.sber.services.processors

import javax.annotation.PostConstruct
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.beanDefinitionNames.map { beanFactory.getBeanDefinition(it) }
            .forEach { bean ->
                if (bean.beanClassName != null) {
                    Class.forName(bean.beanClassName).interfaces.forEach { clazz ->
                        clazz.methods.forEach { method ->
                            if (method.isAnnotationPresent(PostConstruct::class.java)) {
                                bean.initMethodName = method.name
                            }
                        }
                    }
                }
            }
    }
}