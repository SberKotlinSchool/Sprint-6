package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorBean


@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory
            .getBeanDefinition(BeanFactoryPostProcessorBean.beanName)
            .apply {
                initMethodName = BeanFactoryPostProcessorBean::postConstruct.name
            }
    }
}