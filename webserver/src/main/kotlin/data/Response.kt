package data

data class Response(
    var httpReplyText: String = "empty reply text",
    var body: String = ""
) {

    fun getResponse(): String {
        return if (body.isNotEmpty()) {
            "$httpReplyText Content-Length: ${body.length} \n\n$body"
        } else httpReplyText
    }
}
