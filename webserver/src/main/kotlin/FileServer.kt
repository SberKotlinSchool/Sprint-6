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

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {
            val connection = socket.accept()
            BufferedReader(InputStreamReader(connection.getInputStream())).use { reader ->
                PrintWriter(BufferedWriter(OutputStreamWriter(connection.getOutputStream()))).use { writer ->
                    val line = reader.readLine()
                    val path = VPath(line.split(" ")[1])
                    val content = fs.readFile(path)
                    if (content!=null && content.isNotEmpty()) {
                        writer.write("HTTP/1.0 200 OK\r\n");
                        writer.write("Server: FileServer\r\n");
                        writer.write("\r\n");
                        writer.write(content + "\r\n");
                    } else {
                        writer.write("HTTP/1.0 404 Not Found\r\n");
                        writer.write("Server: FileServer\r\n");
                        writer.write("\r\n");
                    }
                }
            }
        }
    }
}