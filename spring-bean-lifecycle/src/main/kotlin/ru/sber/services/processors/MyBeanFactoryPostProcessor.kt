package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {

        Class.forName("ru.sber.services.BeanFactoryPostProcessorInterface").methods.singleOrNull { method ->
            method.isAnnotationPresent(PostConstruct::class.java)
        }?.let { initMethod ->
            beanFactory.beanDefinitionNames.forEach { bdn ->
                beanFactory.getBeanDefinition(bdn)?.let { bd ->
                    bd.beanClassName?.let {beanClassName ->
                        Class.forName(beanClassName).also { clazz ->
                            clazz.genericInterfaces.singleOrNull { it.typeName == "ru.sber.services.BeanFactoryPostProcessorInterface" }
                                ?.let {
                                    bd.initMethodName = initMethod.name
                                }


                        }
                    }

                }

            }
        }


    }
}