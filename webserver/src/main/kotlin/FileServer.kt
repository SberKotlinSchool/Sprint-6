import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.net.ServerSocket

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    private val SERVER_NAME: String = "Server: FileServer"
    private val HTTP_1_1: String = "HTTP/1.1"
    private val METHOD_GET: String = "GET"
    private val NEW_LINE_SYMBOLS: String = "\r\n"
    private val CODE_200: String = "200 OK"
    private val CODE_404: String = "404 Not Found"

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
            val serverSocket = socket.accept()
            val inputStreamReader = serverSocket.getInputStream().bufferedReader()
            val inputString = inputStreamReader.readLine()
            val (method, path, httpVersion) = inputString.toString().split(" ")

            if (method != METHOD_GET || httpVersion != HTTP_1_1)
                throw UnsupportedOperationException();

            if (path.isNotEmpty()) {
                val outputStreamWriter = serverSocket.getOutputStream().bufferedWriter()
                outputStreamWriter.write(checkFileContents(fs, path))
                outputStreamWriter.close()
            }
        }
    }

    private fun checkFileContents(fileSystem: VFilesystem, path: String): String {
        val content = fileSystem.readFile(VPath(path))

        if (content != null) {
            return HTTP_1_1 + " " + CODE_200 + NEW_LINE_SYMBOLS +
                    SERVER_NAME + NEW_LINE_SYMBOLS +
                    NEW_LINE_SYMBOLS +
                    content + NEW_LINE_SYMBOLS
        }

        return HTTP_1_1 + " " + CODE_404 + NEW_LINE_SYMBOLS +
                SERVER_NAME + NEW_LINE_SYMBOLS +
                NEW_LINE_SYMBOLS
    }
}