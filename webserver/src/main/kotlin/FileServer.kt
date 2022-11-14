import mu.KotlinLogging
import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket


/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */

fun main() {
    FileServer().run(ServerSocket(8888), VFilesystem())
}

class FileServer {

    /**
     * Main entrypoint for the basic file server.
     *
     * @param socket Provided socket to accept connections on.
     * @param fs     A proxy filesystem to serve files from. See the VFilesystem
     *               class for more detailed documentation of its usage.
     * @throws IOException If an I/O error is detected on the server. This
     *                     should be a fatal error, your file server
     *                     implementation is not expected to ever throw
     *                     IOExceptions during normal operation.
     */
    @Throws(IOException::class)
    fun run(socket: ServerSocket, fs: VFilesystem) {

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {
            // TODO 1) Use socket.accept to get a Socket object
            val skt = socket.accept()
            handle(skt, fs)
        }
    }

    private fun handle(socket: Socket, fs: VFilesystem) {
        LOG.info { "client connected:${socket.remoteSocketAddress}" }
        socket.use { s ->
            val reader = s.getInputStream().bufferedReader()
            val clientRequest = reader.readLine()
            LOG.info { "received from ${socket.remoteSocketAddress}  > clientRequest $clientRequest" }

            val requestElements = clientRequest.split(" ")

            val httpMethod = requestElements[0]
            val filePath = requestElements[1]
            val httpProtocol = requestElements[2]
            validateHeader(httpMethod, filePath, httpProtocol)

            val content = fs.readFile(VPath(filePath))

            val serverResponse = prepareResponse(content)

            val writer = PrintWriter(s.getOutputStream())
            writer.write(serverResponse)
            writer.flush()
            LOG.info { "sent to ${socket.remoteSocketAddress} > $serverResponse" }
        }
    }

    private fun prepareResponse(content: String?) =
        if (content != null) {
            "${ResponseHeader.OK.value}$HEADER_SECOND_LINE$content"
        } else {
            "${ResponseHeader.NOT_FOUND.value}$HEADER_SECOND_LINE"
        }

    private fun validateHeader(httpMethod: String, filePath: String, httpProtocol: String) {
        if (httpMethod != "GET") throw RuntimeException("Unsupported HTTP method : $httpMethod")
        if (httpProtocol != "HTTP/1.1") throw RuntimeException("Unsupported HTTP protocol : $httpProtocol")
        if (!filePath.startsWith("/")) throw RuntimeException("Path to file must starts with '/' ")
        LOG.info { "Http header validation passed correctly!" }
    }

    companion object {
        val LOG = KotlinLogging.logger {}
        const val HEADER_SECOND_LINE = "Server: FileServer\r\n\r\n"
    }
}

enum class ResponseHeader(val value: String) {
    OK("HTTP/1.0 200 OK\r\n"),
    NOT_FOUND("HTTP/1.0 404 Not Found\r\n"),
}