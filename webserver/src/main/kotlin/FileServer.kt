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
        while (true) {
            socket.accept().use { clientSocket ->
                // читаем от клиента сообщение
                val reader = clientSocket.getInputStream().bufferedReader()
                val clientRequestParams = reader.readLine().split(" ")
                val pathToFile = clientRequestParams[1]

                // отправляем ответ
                val content = fs.readFile(VPath(pathToFile))
                val serverResponse = if (!content.isNullOrBlank())
                    """
                            HTTP/1.0 200 OK
                            Server: FileServer
                            
                            $content
                        """.trimIndent()
                else
                    """
                            HTTP/1.0 404 Not Found
                            Server: FileServer
                        """.trimIndent()
                println(serverResponse)
                clientSocket.getOutputStream().use { it.write(serverResponse.toByteArray()) }
            }
        }
    }
}