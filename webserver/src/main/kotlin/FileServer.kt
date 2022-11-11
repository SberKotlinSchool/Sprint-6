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
            socket.accept().use { s ->
                s.getInputStream().bufferedReader().use {
                    val writer = PrintWriter(s.getOutputStream())
                    val filePath = VPath(getFilePath(it.readLine()))

                    val result = fs.readFile(filePath)?.let {
                        ResponseStatus.FILE_FOUND.responseText + "${it}\r\n"
                    } ?: ResponseStatus.FILE_NOT_FOUND.responseText

                    writer.use { w ->
                        w.println(result)
                        w.flush()
                    }
                }
            }
        }
    }
    private fun getFilePath(string: String): String? = string.split(" ").getOrNull(1)
}

enum class ResponseStatus(val responseText: String) {
    FILE_NOT_FOUND("HTTP/1.0 404 Not Found\r\n" + "Server: FileServer\r\n" + "\r\n"),
    FILE_FOUND("HTTP/1.0 200 OK\r\n" + "Server: FileServer\r\n" + "\r\n")
}