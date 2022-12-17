package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorBean
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        BeanFactoryPostProcessorBean::class.java.interfaces.forEach { interFace->
            interFace.methods.forEach { interfaceMethod ->
                interfaceMethod.annotations.filterIsInstance<PostConstruct>().forEach { _ ->
                    when (interfaceMethod.name){
                        "postConstruct" -> beanFactory.getBean(BeanFactoryPostProcessorBean::class.java).postConstruct()
                    }
                }
            }
        }
    }
}