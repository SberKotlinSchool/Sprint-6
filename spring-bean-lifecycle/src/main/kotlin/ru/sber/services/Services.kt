package ru.sber.services

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
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

@Component
class CombinedBean {
    var postProcessBeforeInitializationOrderMessage: String? = null
    var postConstructOrderMessage: String? = null
    var customInitOrderMessage: String? = null
    var afterPropertiesSetOrderMessage: String? = null
    var postProcessAfterInitializationOrderMessage: String? = null

    fun postProcessAfterInitialization() {
        postProcessAfterInitializationOrderMessage = "postProcessAfterInitialization() is called"
    }

    fun postProcessBeforeInitializationOrderMessage() {
        postProcessBeforeInitializationOrderMessage = "postProcessBeforeInitialization() is called"
    }

    fun afterPropertiesSet() {
        afterPropertiesSetOrderMessage = "afterPropertiesSet() is called"
    }

    fun customInit() {
        customInitOrderMessage = "customInit() is called"
    }

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
}

interface BeanFactoryPostProcessorInterface {
    @PostConstruct
    fun postConstruct()
}

