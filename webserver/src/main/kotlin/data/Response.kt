package data

class Response(
    private val header: ResponseHeader,
    private val body: String
) {
    fun buildOkResponse(): String {
        return header.getHeader() + "\n" +
                "Content-Length: ${body.length}" +
                "\n\n" + body
    }
    fun buildErrorResponse(): String {
        return header.getHeader() + "\n" +
                "Content-Length: ${body.length}" +
                "\n\n" + body
    }
}
