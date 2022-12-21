import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.net.ServerSocket
import java.io.PrintWriter

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
                it.getInputStream().bufferedReader().use { reader ->
                    val path = reader.readLine().split(" ")[1]
                    val file = fs.readFile(VPath(path))
                    var result =""
                    if ( file != null){
                        result = "HTTP/1.0 200 OK\r\n" +
                                "Server: FileServer\r\n" +
                                "\r\n" +
                                file
                    } else{
                        result = "HTTP/1.0 404 Not Found\r\n" +
                                "Server: FileServer\r\n" +
                                "\r\n"
                    }
                    PrintWriter(it.getOutputStream()).use { writer ->
                        writer.println(result)
                        writer.flush()
                    }
                }
            }
        }
    }
}