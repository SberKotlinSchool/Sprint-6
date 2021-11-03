Необходимо поправить код проекта таким образом, чтобы все тесты отработали корректно.
P.S. 
1) Код тестов править не нужно.
2) При починке теста `severalBeanInjectionService should have two dependencies` класс SeveralServicesConfig изменять нельзя и в сервисе SeveralBeanInjectionService нельзя использовать метод @PostConstruct.
3) При починке теста `qualifierBeanInjectionService should return only SecondQualifierServiceImpl dependency` свойство аннотации @Qualifier классе QualifierBeanInjectionService менять нельзя.