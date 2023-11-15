import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.net.ServerSocket
import java.nio.charset.StandardCharsets

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    private val SERVER_NAME: String = "Server: FileServer"
    private val HTTP_1_1: String = "HTTP/1.1"
    private val METHOD_GET: String = "GET"
    private val NEW_LINE_SYMBOLS: String = "\r\n"

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
            return "HTTP/1.0 200 OK" + NEW_LINE_SYMBOLS +
                    SERVER_NAME + NEW_LINE_SYMBOLS +
                    NEW_LINE_SYMBOLS +
                    content + NEW_LINE_SYMBOLS
        }

        /*
             *   HTTP/1.0 200 OK\r\n
             *   Server: FileServer\r\n
             *   \r\n
             *   FILE CONTENTS HERE\r\n
             *
             * If the specified file does not exist, you should return a reply
             * with an error code 404 Not Found. This reply should be formatted
             * as:
             *
             *   HTTP/1.0 404 Not Found\r\n
             *   Server: FileServer\r\n
             *   \r\n
             *
             * Don't forget to close the output stream.
             */
        return "HTTP/1.0 404 Not Found" + NEW_LINE_SYMBOLS +
                SERVER_NAME + NEW_LINE_SYMBOLS +
                NEW_LINE_SYMBOLS
    }
}

fun main() {

}