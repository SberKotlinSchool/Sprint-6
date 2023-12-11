import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
private const val NOT_FOUND_RESPONSE = "HTTP/1.0 404 Not Found"
private const val OK_RESPONSE = "HTTP/1.0 200 OK"
private const val SERVER_CONTENT_PREFIX = "Server: FileServer"
private const val GET_METHOD = "GET"
private const val PROTOCOL = "HTTP/1.1"
private const val FILE_PATH_DATA_INDEX = 1


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
            socket.accept().use {
                val request = it.getInputStream().bufferedReader().readLine()
                val splitData = request.split(" ")
                if (splitData.size == 3 && splitData.first() == GET_METHOD && splitData.last() == PROTOCOL) {
                    val content = fs.readFile(VPath(splitData[FILE_PATH_DATA_INDEX]))
                    if (content == null) {
                        it.sendNotFoundResponse()
                    } else {
                        it.sendSuccessResponse(content)
                    }
                }
            }
        }
    }
}

private fun Socket.sendNotFoundResponse() {
    this.getOutputStream().bufferedWriter().use {
        it.appendLine(NOT_FOUND_RESPONSE)
            .appendLine(SERVER_CONTENT_PREFIX)
    }
}

private fun Socket.sendSuccessResponse(content: CharSequence) {
    this.getOutputStream().bufferedWriter().use {
        it.appendLine(OK_RESPONSE)
            .appendLine(SERVER_CONTENT_PREFIX)
            .appendLine()
            .appendLine(content)
            .appendLine()
    }
}