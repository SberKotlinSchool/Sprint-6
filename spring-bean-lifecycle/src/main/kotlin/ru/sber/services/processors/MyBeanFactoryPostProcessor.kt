package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component()
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {

        beanFactory.beanDefinitionNames
            .forEach {
                val bean = beanFactory.getBeanDefinition(it)
                if (bean.beanClassName == null) return@forEach
                Class.forName(bean.beanClassName)
                    .interfaces
                    .forEach { face ->
                        val pcMethod = face.methods.find {
                            it.isAnnotationPresent(PostConstruct::class.java)
                        }

                        if (pcMethod != null) {
                            bean.initMethodName = pcMethod.name
                        }
                    }
            }
    }
}