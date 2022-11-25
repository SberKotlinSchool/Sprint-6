import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.ServerSocket

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

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

            val socket = serverSocket.accept()

            val inputStream = socket.getInputStream()
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            val filePath = bufferedReader.readLine().split(" ")[1]

            val outputStream = socket.getOutputStream()
            val file = fs.readFile(VPath(filePath))
            var responseText = "HTTP/1.0 404 Not Found\nServer: FileServer\n\n"

            if (file != null) {
                responseText = responseText.replace("404 Not Found", "200 OK")
                responseText += "$file\n"
            }
            val data = responseText.toByteArray()
            outputStream.write(data)
            outputStream.close()
            socket.close()
        }
    }
}