package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.getBeansOfType
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorInterface
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.beanDefinitionNames.map {name -> beanFactory.getBeanDefinition(name) }
             .forEach { beanDef ->
                 beanDef.beanClassName?.let { clzName ->
                     Class.forName(clzName).interfaces.flatMap { inf -> inf.methods.asIterable() }
                         .firstOrNull { method -> method.isAnnotationPresent(PostConstruct::class.java) }
                         ?.let { beanDef.initMethodName = it.name }
                 }
             }
    }
}