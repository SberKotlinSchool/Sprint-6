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
            val session = socket.accept()
            session.use {
                it.getInputStream().bufferedReader().use {
                    val path = it.readLine().split(" ")[1]
                    val result = fs.readFile(VPath(path))?.let {
                        "HTTP/1.0 200 OK\r\n" +
                        "Server: FileServer\r\n" +
                        "\r\n" +
                        "$it\r\n"
                    } ?: "HTTP/1.0 404 Not Found\r\n" +
                    "Server: FileServer\r\n" +
                    "\r\n"
                    PrintWriter(session.getOutputStream()).use { writer ->
                        writer.println(result)
                        writer.flush()
                    }
                }
            }
        }
    }
}