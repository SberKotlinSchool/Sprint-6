package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorBean
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory
            .getBeanNamesForType(BeanFactoryPostProcessorBean::class.java)
            .map { beanName -> beanFactory.getBeanDefinition(beanName) }
            .forEach { bean ->
                Class.forName(bean.beanClassName).interfaces.forEach { beanInterface ->
                    beanInterface.methods.find { method -> method.isAnnotationPresent(PostConstruct::class.java) }
                        ?.apply { bean.initMethodName = name }
                }
            }
    }
}