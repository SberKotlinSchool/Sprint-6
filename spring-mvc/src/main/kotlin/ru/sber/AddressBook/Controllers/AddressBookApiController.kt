package ru.sber.AddressBook.Controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.sber.AddressBook.Model.CustomerApiModel
import ru.sber.AddressBook.Model.CustomerModel
import ru.sber.AddressBook.Model.ResponseDescription
import ru.sber.AddressBook.Services.AddressBookService

@RestController
@RequestMapping("/api")
class AddressBookApiController @Autowired constructor(val addressBookService: AddressBookService) {

    @PostMapping("/add")
    fun addRow(@RequestBody body: CustomerApiModel) : ResponseEntity<ResponseDescription> {
        try{
            addressBookService.addRow(
                CustomerModel(
                    firstName = body.firstName,
                    lastName = body.lastName,
                    middleName = body.middleName,
                    phone = body.phone,
                    address = body.address,
                    email = body.email
                )
            )
        }
        catch (exc: Exception) {
            println(exc.message)
            return ResponseEntity.internalServerError().body(ResponseDescription("Error", "Cannot add row: ${exc.message}"))
        }
        return ResponseEntity.ok(ResponseDescription("OK", "Added row"))
    }

    @GetMapping("/list")
    fun getRows(): ResponseEntity<Map<Int,CustomerModel>> = ResponseEntity.ok(addressBookService.getAllRows())


    @GetMapping("/view")
    fun getRow(@RequestParam(required = true) id: Int): ResponseEntity<CustomerModel> =
        ResponseEntity.ok(addressBookService.getById(id))

    @PutMapping("/edit/{id}")
    fun editRow(@PathVariable(required = true) id: Int, @RequestBody body: CustomerApiModel) : ResponseEntity<ResponseDescription> {
        try{
            addressBookService.updateRow (id,
                CustomerModel(
                    firstName = body.firstName,
                    lastName = body.lastName,
                    middleName = body.middleName,
                    phone = body.phone,
                    address = body.address,
                    email = body.email
                )
            )
        }
        catch (exc: Exception) {
            println(exc.message)
            return ResponseEntity.internalServerError().body(ResponseDescription("Error", "Cannot edit row: ${exc.message}"))
        }
        return ResponseEntity.ok(ResponseDescription("OK", "Edited row for client $id"))
    }

    @DeleteMapping("/delete/{id}")
    fun deleteRow(@PathVariable(required = true) id: Int): ResponseEntity<ResponseDescription> {
        try {
            addressBookService.deleteRow(id)
        } catch (exc: java.lang.Exception) {
            return ResponseEntity.internalServerError().body(ResponseDescription("Error", "Cannot delete row: ${exc.message}"))
        }
        return ResponseEntity.ok(
            ResponseDescription("OK", "Deleted $id")
        )
    }
}