package util

enum class HttpStatus(val code: Int, val description: String) {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error")
}