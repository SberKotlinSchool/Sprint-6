package ru.sber.AddressBook.Controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.sber.AddressBook.Model.CustomerApiModel
import ru.sber.AddressBook.Model.CustomerModel
import ru.sber.AddressBook.Services.AddressBookService

@Controller
@RequestMapping("/app")
class AddressBookController @Autowired constructor(val addressBookService: AddressBookService) {

    @GetMapping("/add")
    fun addRow() : String = "add"
    @PostMapping("/add")
    fun addRow(@ModelAttribute body: CustomerApiModel): String {
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
        return "redirect:/app/list"
    }
    @GetMapping("/list")
    fun getRows(
        @RequestParam(required = false)
        fio: String?, //изначально планировал поиск по фио
        model: Model
    ): String {
        val books: Map<Int,CustomerModel>

        if (fio == null) {
            books = addressBookService.getAllRows()
        } else {
            val fioList = fio.split(" ")
            books = addressBookService.getRowsByFio(
                fioList[0] //поиск только по фамилии, подразумеваю, что она будет первой всегда, до этого хотел сделать поиск по любому значению
            )
        }

        model.addAttribute("books", books)

        return "list"
    }
    @GetMapping("{id}/view")
    fun viewRow(model: Model, @PathVariable id: Int): String {

        val customer = addressBookService.getById(id)
        model.addAttribute("customer", customer)

        return "view"
    }

    @GetMapping("{id}/edit")
    fun editRow(model: Model, @PathVariable id: Int): String {

        val customer = addressBookService.getById(id)
        model.addAttribute("customer", customer)

        return "edit"
    }

    @PostMapping("{id}/edit")
    fun editRow(@PathVariable id: Int, @ModelAttribute body: CustomerApiModel): String {
        addressBookService.updateRow(
            id,
            CustomerModel(
                firstName = body.firstName,
                lastName = body.lastName,
                middleName = body.middleName,
                phone = body.phone,
                address = body.address,
                email = body.email
            )
        )
        return "redirect:/app/list"
    }

    @GetMapping("{id}/delete")
    fun deleteRow(model: Model, @PathVariable id: Int): String {
        addressBookService.deleteRow(id)
        return "redirect:/app/list"
    }
}