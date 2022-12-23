package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
/*
        beanFactory.beanDefinitionNames.forEach { defName ->
            val currentBean = beanFactory.getBeanDefinition(defName)
            Class.forName(currentBean.beanClassName).interfaces.forEach { c ->
                val method = c.methods.find {
                    m -> m.isAnnotationPresent(PostConstruct::class.java)
                }
                println(method?.name)
                currentBean.initMethodName = method?.name
            }
        }
*/
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
