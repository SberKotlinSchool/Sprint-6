package sber.mvc.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class AddressBookApp

fun main(args: Array<String>) {
    runApplication<AddressBookApp>(*args)
}
