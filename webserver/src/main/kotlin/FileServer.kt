import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets

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

            /*
            * TODO 2) Using Socket.getInputStream(), parse the received HTTP
            * packet. In particular, we are interested in confirming this
            * message is a GET and parsing out the path to the file we are
            * GETing. Recall that for GET HTTP packets, the first line of the
            * received packet will look something like:
            *
            *     GET /path/to/file HTTP/1.1
            */


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
            // TODO 1) Use socket.accept to get a Socket object
            val clientSocket: Socket = socket.accept()

            try {
                // TODO 2) Using Socket.getInputStream(), parse the received HTTP packet.
                val inputStream = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                val requestLine = inputStream.readLine()

                // Parse the request line
                val requestComponents = requestLine.split(" ")
                if (requestComponents.size != 3 || requestComponents[0] != "GET") {
                    // Invalid request, respond with 400 Bad Request
                    val outputStream = DataOutputStream(clientSocket.getOutputStream())
                    val response = "HTTP/1.0 400 Bad Request\r\nServer: FileServer\r\n\r\n"
                    outputStream.write(response.toByteArray(StandardCharsets.UTF_8))
                    outputStream.close()
                    continue
                }

                // Extract the path from the request
                val filePath = requestComponents[1]

                // TODO 3) Using the parsed path to the target file, construct an HTTP reply
                val fileContents = fs.readFile(VPath(filePath))
                val outputStream = DataOutputStream(clientSocket.getOutputStream())

                if (fileContents != null) {
                    // File found, respond with 200 OK and file contents
                    val response = "HTTP/1.0 200 OK\r\nServer: FileServer\r\n\r\n$fileContents\r\n"
                    outputStream.write(response.toByteArray(StandardCharsets.UTF_8))
                } else {
                    // File not found, respond with 404 Not Found
                    val response = "HTTP/1.0 404 Not Found\r\nServer: FileServer\r\n\r\n"
                    outputStream.write(response.toByteArray(StandardCharsets.UTF_8))
                }

                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                clientSocket.close()
            }
        }
    }
}