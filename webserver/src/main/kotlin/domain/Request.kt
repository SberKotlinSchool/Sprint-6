package domain

import util.Protocol.PROTOCOL_REQUEST
import util.RequestMethod
import exception.RequestParseException

class Request(
        val method: RequestMethod,
        val path: String,
        val protocol: String) {

    companion object {
        private val pattern = "^(.+) (.+) (.+)$".toRegex()

        fun fromRaw(input: String): Request {
            val (methodRaw, path, protocol) = pattern.find(input)?.destructured
                    ?: throw RequestParseException("Invalid request")
            val method = RequestMethod.values().firstOrNull { it.name == methodRaw }
                    ?: throw RequestParseException("Method $methodRaw is not supported")
            if (protocol != PROTOCOL_REQUEST) {
                throw RequestParseException("util.Protocol $PROTOCOL_REQUEST is not supported")
            }
            return Request(method, path, protocol)
        }
    }
}
