package ru.sber.services

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class CallbackBean : InitializingBean, DisposableBean {
    var greeting: String? = "What's happening?"

    override fun afterPropertiesSet() {
        greeting = "Hello! My name is callbackBean!"
    }

    override fun destroy() {
        greeting = "Sorry, but I really have to go."
    }
}

class CombinedBean : InitializingBean {
    var postProcessBeforeInitializationOrderMessage: String? = null
    var postConstructOrderMessage: String? = null
    var customInitOrderMessage: String? = null
    var afterPropertiesSetOrderMessage: String? = null
    var postProcessAfterInitializationOrderMessage: String? = null

    override fun afterPropertiesSet() {
        afterPropertiesSetOrderMessage = "afterPropertiesSet() is called"
    }

    fun customInit() {
        customInitOrderMessage = "customInit() is called"
    }

    @PostConstruct
    fun postConstruct() {
        postConstructOrderMessage = "postConstruct() is called"
    }
}

@Component
class BeanFactoryPostProcessorNew : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        //Для того чтобы тест отработал корректно необходимо реализовать метод postProcessBeanFactory(),
        // в котором нужно будет найти методы интерфейсов с аннотацией @PostConstruct
        // и назначить их init-методами для соответствующих классов.
        for (nameBean in beanFactory.beanDefinitionNames) {
            val beanDefinition = beanFactory.getBeanDefinition(nameBean)
            val beanClassName = beanDefinition.beanClassName ?: continue
            val clazz = Class.forName(beanClassName)
            for (oneInterface in clazz.interfaces) {
                for (method in oneInterface.methods) {
                    if (method.annotations.toList().any { it.annotationClass == PostConstruct::class }) {
                        beanDefinition.initMethodName = method.name
                    }
                }
            }
        }

    }
}

@Component
class BeanFactoryPostProcessorBean : BeanFactoryPostProcessorInterface {
    var preConfiguredProperty: String? = "I'm not set up yet"

    override fun postConstruct() {
        preConfiguredProperty = "Done!"
    }
}

interface BeanFactoryPostProcessorInterface {
    @PostConstruct
    fun postConstruct()
}

