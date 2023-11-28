package domain

import util.HttpStatus
import util.Protocol.PROTOCOL_RESPONSE

class Response(
        val protocol: String = PROTOCOL_RESPONSE,
        val status: HttpStatus,
        val server: String,
        val content: String = "") {

    companion object {
        const val LINES_SEPARATOR = "\r\n"
    }

    fun toRaw(): String {
        val buffer = StringBuffer()
        appendStatusLine(buffer)
        appendServerName(buffer)
        buffer.append(LINES_SEPARATOR)
        if (this.status.code != HttpStatus.NOT_FOUND.code)
            appendContent(buffer)
        return buffer.toString()
    }

    private fun appendStatusLine(buffer: StringBuffer) =
            buffer.append("$PROTOCOL_RESPONSE ${status.code} ${status.description}$LINES_SEPARATOR")

    private fun appendServerName(buffer: StringBuffer) =
            buffer.append("ServerName: ${this.server}$LINES_SEPARATOR")

    private fun appendContent(buffer: StringBuffer) = buffer.append("${this.content}$LINES_SEPARATOR")
}