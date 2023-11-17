package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.beanNamesIterator.forEach { beanName ->
            beanFactory.getType(beanName)?.interfaces?.forEach { interfaceOfBean ->
                interfaceOfBean.declaredMethods.iterator().forEach { method ->
                    method.declaredAnnotations.forEach { annotation ->
                        if (annotation.annotationClass.java == PostConstruct::class.java) {
                            beanFactory.getBeanDefinition(beanName).initMethodName = method.name
                        }
                    }
                }
            }
        }
    }
}