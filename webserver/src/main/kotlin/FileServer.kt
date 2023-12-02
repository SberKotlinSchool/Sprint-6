import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.net.ServerSocket

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

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {
            socket.accept().use { clientSocket ->
                clientSocket.getInputStream().bufferedReader().use {
                    runCatching {
                        val requestParts = it.readLine().split(" ").also { validateRequest(it) }
                        clientSocket.getOutputStream().writer().use { writer ->
                            fs.readFile(VPath(requestParts[1])).let {
                                writer.write(it.toHttpResponse())
                            }
                        }
                    }.getOrElse { println("Ошибка: ${it.localizedMessage}") }
                }
            }
        }
    }

    private fun validateRequest(requestParts: List<String>) {
        if (requestParts.size != 3) {
            throw IllegalArgumentException("Некорректно составлен запрос")
        } else if (requestParts[0] != "GET") {
            throw UnsupportedOperationException("Некорректный метод http")
        } else if (requestParts[2] != "HTTP/1.1") {
            throw IllegalArgumentException("Некорректно составлен запрос")
        }
    }

    private fun String?.toHttpResponse() = if (this == null) {
        "HTTP/1.0 404 Not Found\r\n" +
                "Server: FileServer\r\n" +
                "\r\n"
    } else {
        "HTTP/1.0 200 OK\r\n" +
                "Server: FileServer\r\n" +
                "\r\n$this\r\n"
    }
}