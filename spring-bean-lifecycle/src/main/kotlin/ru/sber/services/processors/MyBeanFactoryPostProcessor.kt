package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    private val beanName = "beanFactoryPostProcessorBean"

    private val interfaceName = "BeanFactoryPostProcessorInterface"

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.getBeanDefinition(beanName).let { beanDefinition ->
            Class.forName(beanDefinition.beanClassName).let { bean ->
                bean.interfaces
                    .find { it.simpleName == interfaceName }
                    ?.declaredMethods
                    ?.find { it.isAnnotationPresent(PostConstruct::class.java) }
                    .let { interfaceMethod ->
                        bean.declaredMethods
                            .find { it.name == interfaceMethod?.name }
                            ?.apply { beanDefinition.initMethodName = name }
                    }
            }
        }
    }
}
