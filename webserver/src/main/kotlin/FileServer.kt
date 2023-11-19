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

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {
            val socketAccept = socket.accept()
            socketAccept.use {
                val reader = it.getInputStream().bufferedReader()
                val clientRequest = reader.readLine()
                val dataRequest = clientRequest.split(" ")
                if (dataRequest.size != 3 || dataRequest[0] != "GET") throw UnsupportedOperationException()
                val path = VPath(dataRequest[1])

                val file = fs.readFile(path)
                val responseData = file?.let { data ->
                    "HTTP/1.0 200 OK\r\nServer: FileServer\r\n\r\n$data\r\n"
                } ?: run {
                    "HTTP/1.0 404 Not Found\r\nServer: FileServer\r\n\r\n"
                }
                val writer = PrintWriter(it.getOutputStream())
                writer.println(responseData)
                writer.flush()
            }
        }
    }
}