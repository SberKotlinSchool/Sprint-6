package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {

        beanFactory.beanDefinitionNames.onEach { beanDefinitionName ->
            val beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName)

            beanDefinition.beanClassName?.let { beanClassName ->
                Class.forName(beanClassName).interfaces.onEach { interfaceClass ->
                    interfaceClass.methods.find { method -> method.isAnnotationPresent(PostConstruct::class.java) }
                            ?.let { postConstructMethod -> beanDefinition.initMethodName = postConstructMethod.name }
                }
            }
        }
    }
}