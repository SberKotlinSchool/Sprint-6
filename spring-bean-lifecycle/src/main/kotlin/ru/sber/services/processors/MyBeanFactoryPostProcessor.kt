package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
//        val bean = beanFactory.getBean("beanFactoryPostProcessorBean")
//        val clazz = bean.javaClass
//        val postProcessorInterface =
//            clazz.interfaces.first { it.name == "ru.sber.services.BeanFactoryPostProcessorInterface" }
//        val postConstructMethod = postProcessorInterface.methods.first {
//            it.isAnnotationPresent(PostConstruct::class.java)
//        }
//        val name = postConstructMethod.name.toString()
//        if (name == "postConstruct") { println("Равно") }
//        val beanDefinition = beanFactory.getBeanDefinition("beanFactoryPostProcessorBean")
//        beanDefinition.initMethodName = name.trim()
//        if (beanDefinition.initMethodName == "postConstruct") { println("Равно") }
        beanFactory.getBeanDefinition("beanFactoryPostProcessorBean").initMethodName = "postConstruct"
    }
}