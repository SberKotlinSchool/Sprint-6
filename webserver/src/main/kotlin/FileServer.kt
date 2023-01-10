
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

            val sock = socket.accept()

            sock.use {
                val request = sock.getInputStream().bufferedReader().readLine().split(" ")

                sock.getOutputStream().bufferedWriter().use { output ->
                    if (request[0] == "GET") {
                        val content = fs.readFile(VPath(request[1]))

                        if (content != null) {
                            output.append("HTTP/1.1 200 OK")
                                .appendLine("Server: FileServer")
                                .appendLine("Content-Type: text/html; charset=utf-8")
                                .appendLine("Content-Length: ${content.length}")
                                .appendLine("Connection: close")
                                .appendLine()
                                .appendLine(content)
                                .appendLine()
                        } else {
                            output.append("HTTP/1.1 404 Not Found")
                                .appendLine("Server: FileServer")
                                .appendLine()
                        }
                    } else {
                        output.append("HTTP/1.1 405 Method Not Allowed")
                            .appendLine("Server: FileServer")
                            .appendLine()
                    }
                    output.flush()
                }
            }

            if (Thread.interrupted()) {
                break;
            }
        }
    }
}