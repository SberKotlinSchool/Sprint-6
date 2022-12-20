import java.net.http.HttpRequest

fun getHttpRequest(httpRequestBody: String): HttpRequest {
    val (method, path, version) = httpRequestBody.split(" ")
    return HttpRequest.newBuilder().GET().build()
}