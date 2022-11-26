package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    private val beanFactoryPostProcessorBean = "beanFactoryPostProcessorBean"

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val bean = beanFactory.getType(beanFactoryPostProcessorBean)
        bean?.let {
            val interfaces = it.interfaces[0]
            val method = interfaces.methods.filter { me ->
                me.annotations.filterIsInstance<PostConstruct>().isNotEmpty()
            }
            method[0]?.let { m ->
                val beanDefinition = beanFactory.getBeanDefinition(beanFactoryPostProcessorBean)
                beanDefinition.initMethodName = m.name
            }
        }
    }
}
