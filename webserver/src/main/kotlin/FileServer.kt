import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.regex.Pattern

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    /**
     * Main entrypoint for the basic file server.
     *
     * @param serverSocket Provided socket to accept connections on.
     * @param fs     A proxy filesystem to serve files from. See the VFilesystem
     *               class for more detailed documentation of its usage.
     * @throws IOException If an I/O error is detected on the server. This
     *                     should be a fatal error, your file server
     *                     implementation is not expected to ever throw
     *                     IOExceptions during normal operation.
     */
    @Throws(IOException::class)
    fun run(serverSocket: ServerSocket, fs: VFilesystem) {

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        serverSocket.use {
            while (true) {
                println("Сервер запущен на порту: ${serverSocket.localPort}")
                val socket = it.accept()
                handle(socket, fs)
            }
        }
    }

    private fun handle(socket: Socket, fs: VFilesystem) {
        println("Подключен клиент:${socket.remoteSocketAddress}")
        socket.use { s ->
            // читаем от клиента сообщение
            val reader = s.getInputStream().bufferedReader()
            val clientRequest = reader.readLine()
            val path: String
            var serverResponse: String
            try {
                path = clientRequest.split(Pattern.compile(" "), 3)[1]

                serverResponse = if (path.isBlank()) {
                    responseBadRequest
                } else {
                    val file = fs.readFile(VPath(path))
                    if (file == null) responseNotFount else responseSuccess.plus(file)
                }
            } catch (e: Exception) {
                serverResponse = responseBadRequest
            }

            val writer = PrintWriter(s.getOutputStream())
            writer.println(serverResponse)
            writer.flush()
        }
    }

    companion object {
        val responseSuccess = "HTTP/1.0 200 OK\r\nServer: FileServer\r\n\r\n"
        val responseNotFount = "HTTP/1.0 404 Not Found\r\nServer: FileServer\r\n\r\n"
        val responseBadRequest = "HTTP/1.0 400 Bad Request\r\nServer: FileServer\r\n\r\n"
    }
}
