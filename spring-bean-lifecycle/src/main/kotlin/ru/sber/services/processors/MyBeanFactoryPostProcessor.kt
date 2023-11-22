package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorInterface
import javax.annotation.PostConstruct

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {


    /**
     *  Необходимо реализовать метод postProcessBeanFactory(),
     *  в котором нужно будет найти методы интерфейсов с аннотацией @PostConstruct
     *  и назначить их init-методами для соответствующих классов.
     */
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {


        for (beanName in beanFactory.getBeanNamesForType(BeanFactoryPostProcessorInterface::class.java)) {

            //Случай 1, если мы знаем название метода
//            val beanDef = beanFactory.getBeanDefinition(beanName)
//            beanDef.initMethodName = "postConstruct"


            //Случай 2, если мы не знаем название метода, только что он отмечен аннотацией @PostConstruct
            val beanDef = beanFactory.getBeanDefinition(beanName)
            val beanClass = Class.forName(beanDef.beanClassName)
            //Первый метод из интерфейса с аннотацией @PostConstruct
            val postConstructMethod = beanClass.interfaces
                    .filter { interfaceClass -> interfaceClass.simpleName == BeanFactoryPostProcessorInterface::class.simpleName }
                    .firstOrNull()
                    ?.declaredMethods
                    ?.first() { method -> method.isAnnotationPresent(PostConstruct::class.java) }


            if (postConstructMethod != null) {

                for (method in beanClass.declaredMethods) {
                    if (method.name == postConstructMethod.name) {
                        beanDef.initMethodName = postConstructMethod.name
                        break // initMethodName может быть только один
                    }
                }
            }
        }


    }
}