package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorInterface
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val methods = BeanFactoryPostProcessorInterface::class.java.declaredMethods
        val annotatedMethod = methods.firstOrNull { it.getAnnotation(PostConstruct::class.java) != null }
        annotatedMethod?.let {
            beanFactory.getBeanDefinition("beanFactoryPostProcessorBean").initMethodName = it.name
        }
    }
}