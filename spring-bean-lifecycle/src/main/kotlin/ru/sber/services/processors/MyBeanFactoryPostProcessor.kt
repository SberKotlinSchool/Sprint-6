package ru.sber.services.processors

import javax.annotation.PostConstruct
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.beanDefinitionNames.forEach {
            val bean = beanFactory.getBeanDefinition(it)

            bean.beanClassName?.let { beanClassName ->
                Class.forName(beanClassName).interfaces.forEach { eachClass ->
                        eachClass.methods.find { method ->
                            method.isAnnotationPresent(PostConstruct::class.java)
                        }?.let { methodName ->
                            bean.initMethodName = methodName.name
                        }
                    }
            }
        }
    }
}