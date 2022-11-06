package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val beanDefinitionNames = beanFactory.beanDefinitionNames
        for (name in beanDefinitionNames) {
            val beanDefinition: BeanDefinition = beanFactory.getBeanDefinition(name);
            beanDefinition.beanClassName?.let {
                val interfaces = Class.forName(it).interfaces
                var postConstructMethod: Method? = null
                for (ifc in interfaces) {
                    val method = ifc.methods.find { it.isAnnotationPresent(PostConstruct::class.java) }
                    if (method != null) {
                        postConstructMethod = method
                        break
                    }
                }
                if (postConstructMethod != null) {
                    beanDefinition.initMethodName = postConstructMethod.name
                }
            }
        }
    }
}