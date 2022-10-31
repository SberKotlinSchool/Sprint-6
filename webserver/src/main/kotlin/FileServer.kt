import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
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

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {
            handle(socket.accept(), fs)
        }
    }

    private fun handle(socket: Socket, fs: VFilesystem) {

        socket.use {
            it.getInputStream().bufferedReader().use { reader ->
                val line = reader.readLine()

                //GET /path/to/file HTTP/1.1
                val groups = "(?<method>\\w+)\\s+(?<path>.+)\\s+(?<protocol>HTTP)*/(?<version>.+)".toRegex()
                    .matchEntire(line)?.groups

                if (groups != null && "GET" == groups["method"]?.value && "HTTP" == groups["protocol"]?.value) {

                    val readFile = fs.readFile(VPath(groups["path"]?.value))

                    it.getOutputStream().bufferedWriter().use { writer ->
                        if (readFile == null) {
                            writer
                                .appendLine("HTTP/1.0 404 Not Found")
                                .appendLine("Server: FileServer")
                        } else {
                            writer
                                .appendLine("HTTP/1.0 200 OK")
                                .appendLine("Server: FileServer")
                                .appendLine()
                                .appendLine(readFile)
                                .appendLine()
                        }
                    }
                }
            }
        }
    }
}