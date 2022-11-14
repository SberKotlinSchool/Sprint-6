import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
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
            socket.accept().use {
                val reader = it.getInputStream().bufferedReader()
                val request = reader.readLine()
                if (!request.matches(Regex("GET .+"))){
                    return@use
                }

                val fileName = request.split(" ")[1]
                val fileContent = fs.readFile(VPath(fileName))

                it.getOutputStream().bufferedWriter().use { writer ->
                    if (fileContent == null ) {
                        writer
                            .appendLine("HTTP/1.0 404 Not Found")
                            .appendLine("Server: FileServer")
                            .appendLine("")
                    } else {
                        writer
                            .appendLine("HTTP/1.0 200 OK")
                            .appendLine("Server: FileServer")
                            .appendLine()
                            .appendLine(fileContent)
                            .appendLine()
                    }
                }
            }
        }
    }
}