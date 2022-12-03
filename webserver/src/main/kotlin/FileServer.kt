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

    companion object {
        const val SERVER_NAME = "Server: FileServer"
        val LINE_SEPARATOR: String = System.lineSeparator()
    }

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

            socket.accept().use { s ->

                val reader = s.getInputStream().bufferedReader()
                val (method, path) = reader.readLine().split(" ")
                if (method != "GET") {
                    return
                }

                val content:String? = fs.readFile(VPath(path))?.let { it +  LINE_SEPARATOR}
                val headers = if (content != null) { "HTTP/1.0 200 OK" } else { "HTTP/1.0 404 NOT FOUND" }

                val serverResponse =
                    "${headers}${LINE_SEPARATOR}" +
                    "${SERVER_NAME}${LINE_SEPARATOR}${LINE_SEPARATOR}" +
                    content

                PrintWriter(s.getOutputStream()).use { w ->
                    w.print(serverResponse)
                    w.flush()
                }
            }
        }
    }
}