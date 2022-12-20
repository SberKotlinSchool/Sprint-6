import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.lang.StringBuilder
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

            // TODO Delete this once you start working on your solution.
            //throw new UnsupportedOperationException();

            // TODO 1) Use socket.accept to get a Socket object
            socket.accept().use { it ->
                val request = it.getInputStream().bufferedReader().readLine()

                /*
                * TODO 2) Using Socket.getInputStream(), parse the received HTTP
                * packet. In particular, we are interested in confirming this
                * message is a GET and parsing out the path to the file we are
                * GETing. Recall that for GET HTTP packets, the first line of the
                * received packet will look something like:
                *
                *     GET /path/to/file HTTP/1.1
                */
                val (method, filePath, protocol) = request.split(" ")

                if (method.equals("GET")) {
                    val content = fs.readFile(VPath(filePath))

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
                    it.getOutputStream().use {
                        val separator = System.lineSeparator()
                        val responce = StringBuilder()

                        if (content == null) {
                            responce.append("HTTP/1.0 404 Not Found")
                                .append(separator)
                                .append("Server: FileServer")
                                .append(separator)
                                .append(separator)
                        }
                        else {
                            responce.append("HTTP/1.0 200 OK")
                                .append(separator)
                                .append("Server: FileServer")
                                .append(separator)
                                .append(separator)
                                .append(content)
                                .append(separator)
                        }
                        it.write(responce.toString().toByteArray())
                    }
                }
            }
        }
    }
}