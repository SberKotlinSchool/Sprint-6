import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.net.ServerSocket

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    private companion object {

        const val LINE_SEPARATOR = "\r\n"

        const val REQUEST_TYPE_GET = "GET"

        const val RESPONSE_STATUS_SUCCESS = "200 OK"
        const val RESPONSE_STATUS_NOT_FOUND = "404 Not Found"
    }

    /**
     * Main entrypoint for the basic file server.
     *
     * @param serverSocket Provided socket to accept connections on.
     * @param fs     A proxy filesystem to serve files from. See the VFilesystem
     *               class for more detailed documentation of its usage.
     * @throws IOException If an I/O error is detected on the server. This
     *                     should be a fatal error, your file server
     *                     implementation is not expected to ever throw
     *                     IOExceptions during normal operation.
     */
    @Throws(IOException::class)
    fun run(serverSocket: ServerSocket, fs: VFilesystem) {

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {

            // TODO Delete this once you start working on your solution.
            //throw new UnsupportedOperationException();

            // TODO 1) Use socket.accept to get a Socket object
            val socket = serverSocket.accept()

            /*
            * TODO 2) Using Socket.getInputStream(), parse the received HTTP
            * packet. In particular, we are interested in confirming this
            * message is a GET and parsing out the path to the file we are
            * GETing. Recall that for GET HTTP packets, the first line of the
            * received packet will look something like:
            *
            *     GET /path/to/file HTTP/1.1
            */
            socket.use {
                val reader = socket.getInputStream().bufferedReader()
                val (requestType, path, protocol) = reader.readLine().split(' ')
                /*
                 * TODO 3) Using the parsed path to the target file, construct an
                 * HTTP reply and write it to Socket.getOutputStream(). If the file
                 * exists, the HTTP reply should be formatted as follows:
                 *
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
                if (requestType == REQUEST_TYPE_GET) {
                    val writer = socket.getOutputStream().bufferedWriter()

                    val fileContents: String? = fs.readFile(VPath(path))

                    val status = buildResponseStatus(fileContents)

                    writer.write(buildResponse(protocol, status, fileContents))
                    writer.flush()
                    writer.close()
                }
                reader.close()
            }
        }
    }

    private fun buildResponseStatus(fileContents: String?) =
        if (fileContents == null) RESPONSE_STATUS_NOT_FOUND else RESPONSE_STATUS_SUCCESS

    private fun buildResponse(protocol: String, status: String, fileContents: String?): String {
        val header = "$protocol $status"
        val server = "Server: ${this::class.java.simpleName}"


        return if (fileContents == null) {
            listOf(header, server).joinToString(separator = LINE_SEPARATOR, postfix = LINE_SEPARATOR)
        } else {
            listOf(header, server, LINE_SEPARATOR, fileContents)
                .joinToString(separator = LINE_SEPARATOR, postfix = LINE_SEPARATOR)
        }
    }
}