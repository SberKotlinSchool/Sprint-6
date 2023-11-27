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
                val reader = clientSocket.getInputStream().bufferedReader()
                val (method, path, _) = reader.readLine().split(" ")
                if (method == "GET") {
                    val file = fs.readFile(VPath(path))
                    clientSocket.getOutputStream().bufferedWriter().use { result ->
                        if (file != null) {
                            result.append("HTTP/1.0 200 OK")
                                .appendLine("Server: FileServer")
                                .appendLine()
                                .appendLine(file)
                        } else {
                            result.append("HTTP/1.0 404 Not Found")
                                .appendLine("Server: FileServer")
                                .appendLine()
                        }
                        result.flush()
                    }
                } else {
                    throw UnsupportedOperationException()
                }
            }
        }
    }
}