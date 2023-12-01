import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.*
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
            val clientSocket = socket.accept()
            clientSocket.use {
                val reader = BufferedReader(InputStreamReader(it.getInputStream()))
                val writer = BufferedWriter(OutputStreamWriter(it.getOutputStream()))

                val requestLine = reader.readLine()
                if (requestLine != null) {
                    val requestParts = requestLine.split(" ")
                    if (requestParts.size >= 3) {
                        val httpMethod = requestParts[0]
                        val path = requestParts[1]
                        val httpVersion = requestParts[2]

                        if (httpMethod == "GET" && httpVersion == "HTTP/1.1") {
                            handleRequest(path, fs, writer)
                        }
                    }
                }
            }
        }
    }

    private fun handleRequest(path: String, fs: VFilesystem, writer: BufferedWriter) {
        if (path.isNotEmpty()) {
            val content = fs.readFile(VPath(path))
            val httpResponse = if (content != null) {
                "HTTP/1.1 200 OK\r\nServer: FileServer\r\n\r\n$content\r\n"
            } else {
                "HTTP/1.1 404 Not Found\r\nServer: FileServer\r\n\r\n"
            }
            writer.write(httpResponse)
            writer.flush()
        }
    }
}