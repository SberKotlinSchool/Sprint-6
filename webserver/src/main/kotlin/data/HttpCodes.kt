package data

enum class HttpCodes(val status: String, val code: Int) {
    HTTP_OK("OK", 200),
    HTTP_ERROR("Not Found", 404)
}

fun main() {
    println("${HttpCodes.HTTP_OK.status} ${HttpCodes.HTTP_ERROR.code}")
    println(HttpCodes.HTTP_ERROR.status)
}

