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
                val inputStream = it.getInputStream().bufferedReader()
                val data = inputStream.readLine()
                val filePath = data.split(" ")[1]
                val fileContent = fs.readFile(VPath(filePath))
                it.getOutputStream().use { writer ->
                    if (fileContent == null) {
                        writer.write("HTTP/1.0 404 Not Found\n".toByteArray())
                        writer.write("Server: FileServer\n".toByteArray())
                        writer.write("\n".toByteArray())
                    } else {
                        writer.write("HTTP/1.0 200 OK\n".toByteArray())
                        writer.write("Server: FileServer\n".toByteArray())
                        writer.write("\n".toByteArray())
                        writer.write(fileContent.toByteArray())
                        writer.write("\n".toByteArray())
                    }
                }
            }
        }
    }
}