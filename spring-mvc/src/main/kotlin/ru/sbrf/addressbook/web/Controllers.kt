package ru.sbrf.addressbook.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.sbrf.addressbook.core.AddressBookService
import ru.sbrf.addressbook.core.Employee
import javax.validation.Valid

@Controller
@RequestMapping("/app")
class AddressBookMvcController @Autowired constructor(val service: AddressBookService) {

    @GetMapping(value = ["/add"])
    fun addEmployee(model: Model): String {
        model.addAttribute("employee", Employee())
        return "add"
    }

    @PostMapping(value = ["/add"])
    fun addEmployee(
        @Valid @ModelAttribute("employee") employee: Employee,
        result: BindingResult
    ): String {
        if (result.hasErrors()) {
            return "add"
        }
        service.addEmployee(employee)
        return "redirect:/app/list"
    }


    @GetMapping(value = ["/list"])
    fun listEmployees(
        @RequestParam(required = false) firstname: String?,
        @RequestParam(required = false) lastname: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) email: String?,
        model: Model
    ): String {
        model.addAttribute(
            "employees",
            service.getEmployees(firstname, lastname, phone, email)
        )
        return "list"
    }


    @GetMapping(value = ["/{id}/view"])
    fun viewEmployee(@PathVariable id: Long, model: Model): String {
        model.addAttribute("employee", service.getEmployeeById(id))
        return "view"
    }

    @GetMapping(value = ["/{id}/edit"])
    fun editEmployee(@PathVariable id: Long, model: Model): String {
        model.addAttribute("employee", service.getEmployeeById(id))
        return "edit"
    }

    @PostMapping(value = ["/{id}/edit"])
    fun editEmployee(
        @PathVariable id: Int,
        @Valid @ModelAttribute("employee") employee: Employee,
        result: BindingResult
    ): String {
        if (result.hasErrors()) {
            return "edit"
        }
        service.updateEmployee(employee)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/delete")
    fun deleteEmployee(@PathVariable id: Long, model: Model): String {
        service.deleteEmployeeById(id)
        return "redirect:/app/list"
    }


}

@RestController
@RequestMapping("/api")
class AddressBookRestController @Autowired constructor(val service: AddressBookService) {

    @PostMapping(value = ["/add"])
    fun addEmployee(@Valid @RequestBody employee: Employee): ResponseEntity<Employee> {
        service.addEmployee(employee)
        return ResponseEntity.ok(employee)
    }

    @GetMapping(value = ["/list"])
    fun listEmployees(
        @RequestParam(required = false) firstname: String?,
        @RequestParam(required = false) lastname: String?,
        @RequestParam(required = false) phone: String?,
        @RequestParam(required = false) email: String?
    ): ResponseEntity<List<Employee>> {
        return ResponseEntity.ok(service.getEmployees(firstname, lastname, phone, email))
    }

    @GetMapping(value = ["/{id}/view"])
    fun viewEmployee(@PathVariable id: Long): ResponseEntity<Employee?> {
        return ResponseEntity.ok(service.getEmployeeById(id))
    }

    @PutMapping(value = ["/{id}/edit"])
    fun editEmployee(
        @PathVariable id: Int,
        @Valid @RequestBody employee: Employee,
        result: BindingResult
    ): ResponseEntity<Employee?> {
        service.updateEmployee(employee)
        return ResponseEntity.ok(employee)
    }

    @DeleteMapping(value = ["/{id}/delete"])
    fun deleteEmployee(@PathVariable id: Long) {
        service.deleteEmployeeById(id)
    }
}