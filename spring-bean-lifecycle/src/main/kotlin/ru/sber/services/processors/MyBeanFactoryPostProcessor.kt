package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.annotation.ScannedGenericBeanDefinition
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorBean
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.getBeanDefinition("beanFactoryPostProcessorBean").also { beanDefinition ->
            (beanDefinition as ScannedGenericBeanDefinition)
                    .resolveBeanClass(BeanFactoryPostProcessorBean::class.java.classLoader)
                    ?.also {
                        val postContructInterface = it.interfaces.first {
                            it.declaredMethods.any {
                                it.isAnnotationPresent(PostConstruct::class.java)
                            }
                        }
                        val method = postContructInterface.declaredMethods.first {
                            it.isAnnotationPresent(PostConstruct::class.java)
                        }
                        beanDefinition.initMethodName = method.name
                    }
        }
    }
}