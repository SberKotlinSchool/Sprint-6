import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket
import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath

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
                val inputStream = BufferedReader(InputStreamReader(it.getInputStream()))
                val outStream = BufferedWriter(OutputStreamWriter(it.getOutputStream()))

                val clientRequest = inputStream.readLine()
                val (request, path) = clientRequest.split(" ")

                if (request.trim() == "GET") {
                    val readFile = fs.readFile(VPath(path.trim()))
                    if (readFile != null) {
                        outStream.write("HTTP/1.0 200 OK\r\n");
                        outStream.write("Server:  FileServer\r\n");
                        outStream.write("\r\n");
                        outStream.write(readFile);
                        outStream.write("\r\n");
                        outStream.flush();
                    } else {
                        outStream.write("HTTP/1.0 404 Not Found");
                        outStream.write("Server:  FileServer\r\n");
                        outStream.write("\r\n");
                        outStream.flush();
                    }
                }
            }
        }
    }
}