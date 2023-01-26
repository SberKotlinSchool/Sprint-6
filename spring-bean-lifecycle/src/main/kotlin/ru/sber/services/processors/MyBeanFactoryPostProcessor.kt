package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorInterface
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        with(beanFactory) {
            beanDefinitionNames.asSequence()
                .map(::getBeanDefinition)
                .filter { it.beanClassName != null }
                .map { it to Class.forName(it.beanClassName) }
                .filter { it.second.interfaces.contains(BeanFactoryPostProcessorInterface::class.java) }
                .forEach { (definition, beanClass) ->
                    beanClass.interfaces.flatMap { it.methods.asIterable() }
                        .firstOrNull { it.isAnnotationPresent(PostConstruct::class.java) }
                        ?.let { definition.initMethodName = it.name }
                }
        }
    }
}
