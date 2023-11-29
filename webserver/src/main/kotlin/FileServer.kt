import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.BufferedWriter
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

private const val HTTP_METHOD_GET = "GET"
private const val HTTP_PROTOCOL = "HTTP/1.1"
private const val HTTP_RESPONSE_OK = "HTTP/1.0 200 OK"
private const val HTTP_RESPONSE_NOT_FOUND = "HTTP/1.0 404 Not Found"
private const val SERVER_APPENDER = "Server: FileServer"

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
            socket.accept().use { handleRequest(it, fs) }
        }
    }

    private fun handleRequest(socket: Socket, fs: VFilesystem) {
        socket.use {
            val requestSplitted = it.getInputStream().bufferedReader().readLine().split(" ")
            if (requestSplitted.size == 3 && requestSplitted[0] == HTTP_METHOD_GET && requestSplitted[2] == HTTP_PROTOCOL) {
                val content = fs.readFile(VPath(requestSplitted[1]))
                it.getOutputStream().bufferedWriter().use { writer ->
                    if (content == null) {
                        createErrorResponse(writer)
                    } else {
                        createOkResponse(writer, content)
                    }
                }
            }
        }
    }

    private fun createErrorResponse(writer: BufferedWriter) {
        writer
                .appendLine(HTTP_RESPONSE_NOT_FOUND)
                .appendLine(SERVER_APPENDER)
    }

    private fun createOkResponse(writer: BufferedWriter, content: String) {
        writer
                .appendLine(HTTP_RESPONSE_OK)
                .appendLine(SERVER_APPENDER)
                .appendLine()
                .appendLine(content)
                .appendLine()
    }
}