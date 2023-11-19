package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorInterface
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val initMethod = BeanFactoryPostProcessorInterface::class.java
            .declaredMethods
            .find { it.isAnnotationPresent(PostConstruct::class.java) }!!
        beanFactory.getBeanDefinition("beanFactoryPostProcessorBean").initMethodName = initMethod.name
    }
}