package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorInterface
import java.util.*
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.getBeanNamesForType(BeanFactoryPostProcessorInterface::class.java)
            .map { beanFactory.getBeanDefinition(it) }.map { it.initMethodName = "postConstruct" }
    }
}