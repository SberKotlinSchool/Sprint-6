# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#web.servlet.spring-mvc.template-engines)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)

### Проверка Rest-контроллера
Создаём две записи
* curl -i -X POST -H 'Content-Type: application/json' -d '{"fullName": "Цаплин Игорь Владимирович", "postAddress": "г. Москва, ул. Гиляровского, 12, кв. 10", "phoneNumber": "+79030000000"}' http://localhost:8080/api
* curl -i -X POST -H 'Content-Type: application/json' -d '{"fullName": "Кичин Олег Всеволодович", "postAddress": "г. Владивосток, ул. Светланская, 111, кв. 6", "phoneNumber": "+79140000000"}' http://localhost:8080/api

Поиск всех записей
* curl -i -X GET -H 'Content-Type: application/json' -d '{"id": null}' http://localhost:8080/api

Поиск записи с id = 1
* curl -i -X GET -H 'Content-Type: application/json' -d '{"id": 1}' http://localhost:8080/api

Обновление записи с id = 1
* curl -i -X PUT -H 'Content-Type: application/json' -d '{"id": 1, "address": {"fullName": "Цаплин Игорь Михайлович", "postAddress": "г. Москва, ул. Гиляровского, 12, кв. 10", "phoneNumber": "+79030000000"}}' http://localhost:8080/api

Обновление записи с id = 3
* curl -i -X PUT -H 'Content-Type: application/json' -d '{"id": 3, "address": {"fullName": "Цаплин Игорь Михайлович", "postAddress": "г. Москва, ул. Гиляровского, 12, кв. 10", "phoneNumber": "+79030000000"}}' http://localhost:8080/api

Удаление записи с id = 1
* curl -i -X DELETE -H 'Content-Type: application/json' -d '{"id": 1}' http://localhost:8080/api

Удаление записи с id = 3
* curl -i -X DELETE -H 'Content-Type: application/json' -d '{"id": 3}' http://localhost:8080/api
