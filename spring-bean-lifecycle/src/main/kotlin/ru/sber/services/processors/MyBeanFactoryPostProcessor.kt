package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {

        val beanNames = beanFactory.beanDefinitionNames

        for (beanName in beanNames) {
            if ("beanFactoryPostProcessorBean" == beanName) {
                // Пример изменения значения свойства "name" у бина MyBean
                beanFactory.getBeanDefinition(beanName).propertyValues.add("preConfiguredProperty", "Done!")
            }
        }

    }
}