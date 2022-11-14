package ru.sber.services

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.processors.MyBeanPostProcessor
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class CallbackBean : InitializingBean, DisposableBean {
    var greeting: String? = "What's happening?"

    override fun afterPropertiesSet() {
        this.greeting = "Hello! My name is callbackBean!"
    }

    override fun destroy() {
        greeting = "Sorry, but I really have to go."
    }
}


class CombinedBean : InitializingBean, MyBeanPostProcessor() {
    var postProcessBeforeInitializationOrderMessage: String? = null
    var postConstructOrderMessage: String? = null
    var customInitOrderMessage: String? = null
    var afterPropertiesSetOrderMessage: String? = null
    var postProcessAfterInitializationOrderMessage: String? = null

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        postProcessBeforeInitializationOrderMessage = "postProcessBeforeInitialization() is called"
        return super.postProcessBeforeInitialization(bean, beanName)
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        postProcessAfterInitializationOrderMessage = "postProcessAfterInitialization() is called"
        return super.postProcessAfterInitialization(bean, beanName)
    }

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
class BeanFactoryPostProcessorBean : BeanFactoryPostProcessorInterface {
    var preConfiguredProperty: String? = "I'm not set up yet"

    override fun postConstruct() {
        preConfiguredProperty = "Done!"
    }

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        postConstruct()
    }
}

interface BeanFactoryPostProcessorInterface : BeanFactoryPostProcessor {
    @PostConstruct
    fun postConstruct()
}

