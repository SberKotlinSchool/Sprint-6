import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.lang.UnsupportedOperationException
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
            val clSocket = socket.accept()
            clSocket.use {
                val (httpMethod, filePath, _) = clSocket
                    .getInputStream()
                    .bufferedReader()
                    .readLine()
                    .split(" ")
                if (httpMethod == "GET") {
                    val file = fs.readFile(VPath(filePath))
                    clSocket.getOutputStream().use {
                        val response =
                            if (file != null) {
                                buildString {
                                    append("HTTP/1.1 200 OK\n")
                                    append("Server: FileServer\r")
                                    append("\r")
                                    append("${file}\r")
                                }
                            } else {
                                buildString {
                                    append("HTTP/1.1 404 Not Found\r")
                                    append("Server: FileServer\r")
                                    append("\r")
                                }
                            }
                        it.write(response.toByteArray())
                    }
                } else {
                    throw UnsupportedOperationException()
                }
            }
        }
    }
}