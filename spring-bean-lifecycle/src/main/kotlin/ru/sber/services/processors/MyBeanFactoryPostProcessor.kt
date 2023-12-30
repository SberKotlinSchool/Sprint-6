package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorInterface

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val bean = beanFactory.getBean(BeanFactoryPostProcessorInterface::class.java)
        bean.postConstruct()
    }
}