package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {

        beanFactory.beanDefinitionNames.forEach { beanName ->
            val bean = beanFactory.getBeanDefinition(beanName)
            val beanClsName = bean.beanClassName
            beanClsName?.let{ Class.forName(it).interfaces.forEach {
                int -> int.methods.forEach { meth ->
                    if ( meth.name == "postConstruct" )
                        if ( meth.isAnnotationPresent( PostConstruct::class.java)) { bean.initMethodName = meth.name } } }
            }
        }

//        Class.forName(beanFactory.getBeanDefinition(beanFactory.beanDefinitionNames[6]).beanClassName)
//            .interfaces[0]
//            .getDeclaredMethod("postConstruct").also { beanFactory.getBeanDefinition(beanFactory.beanDefinitionNames[6]).initMethodName = it.name }

//       "postConstruct".also { beanFactory.getBeanDefinition(/* beanName = */ "beanFactoryPostProcessorBean").initMethodName = it }
    }
}