package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorInterface
import java.lang.reflect.Method
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val beanNames = beanFactory.getBeanNamesForType(BeanFactoryPostProcessorInterface::class.java)
        for (beanName in beanNames) {
            val beanDefinition = beanFactory.getBeanDefinition(beanName)

            val beanClass = Class.forName(beanDefinition.beanClassName)
            val postConstructMethods = findPostConstructMethods(beanClass)

            if (postConstructMethods.isNotEmpty()) {
                beanDefinition.initMethodName = postConstructMethods.first().name
            }
        }
    }

    private fun findPostConstructMethods(beanClass: Class<*>): List<Method> {
        val postConstructMethods = mutableListOf<Method>()

        for (interfaceClass in beanClass.interfaces) {
            for (method in interfaceClass.declaredMethods) {
                if (method.isAnnotationPresent(PostConstruct::class.java)) {
                    postConstructMethods.add(method)
                }
            }
        }

        return postConstructMethods
    }
}