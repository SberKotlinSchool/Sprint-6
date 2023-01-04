import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket


/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    private val requestLineRegex = "^GET (.+) HTTP/1.1$".toRegex()

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
        while (true) {
            socket.accept().use { // блокирующий вызов

                // читаем от клиента сообщение GET /path/to/file HTTP/1.1
                val reader = it.getInputStream().bufferedReader()
                val clientRequest = reader.readLine()
                var serverResponse = "HTTP/1.0 404 Not Found\r\n" +
                        "Server: FileServer\r\n" +
                        "\r\n"
                requestLineRegex.find(clientRequest)?.groupValues?.get(1)?.let { path ->
                    fs.readFile(VPath(path))?.let { content ->
                        serverResponse = "HTTP/1.0 200 OK\r\n" +
                                "Server: FileServer\r\n" +
                                "\r\n" +
                                "$content\r\n"
                    }
                }

                // отправляем ответ
                val writer = PrintWriter(it.getOutputStream())
                writer.write(serverResponse)
                writer.flush()
            }
        }
    }
}