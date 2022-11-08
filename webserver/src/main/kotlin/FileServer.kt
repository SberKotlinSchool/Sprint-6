import data.HttpCodes
import data.Request
import data.Response
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

        socket.use { serverSocket ->
            /**
             * Enter a spin loop for handling client requests to the provided
             * ServerSocket object.
             */
            while (true) {
                processRequest(serverSocket.accept(), fs)
            }
        }
    }

    private fun processRequest(socket: Socket, fs: VFilesystem) {
        LOG.info { "client connected:${socket.remoteSocketAddress}" }
        socket.use { s ->

            // читаем от клиента сообщение
            val reader = s.getInputStream().bufferedReader()
            val clientRequest = reader.readLine()
            LOG.info { "receive from ${socket.remoteSocketAddress}  > clientRequest $clientRequest" }

            val request = Request(
                clientRequest.split(" ")[0],
                clientRequest.split(" ")[1],
                clientRequest.split(" ")[2]
            )

            val httpResponse = Response()

            try {
                val file = fs.readFile(VPath(request.requestBody))
                LOG.info("File found, creating response 200")
                httpResponse.httpReplyText = "HTTP/1.0 ${HttpCodes.HTTP_OK.code} ${HttpCodes.HTTP_OK.status}" +
                        "\nServer: FileServer"
                httpResponse.body = file

            } catch (e: java.lang.NullPointerException) {
                LOG.info("File not found, creating response 404")
                httpResponse.httpReplyText = "HTTP/1.0 ${HttpCodes.HTTP_ERROR.code} ${HttpCodes.HTTP_ERROR.status}" +
                        "\nServer: FileServer\r\n"
            }

            val writer = PrintWriter(s.getOutputStream())
            writer.println(httpResponse.getResponse())
            writer.flush()
            LOG.info { "Send to ${socket.remoteSocketAddress} > $httpResponse" }
        }
    }

    companion object {
        val LOG = KotlinLogging.logger {}
    }
}