import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.*
import java.net.ServerSocket
import java.net.Socket


/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    /**
     * Main entrypoint for the basic file server.
     *
     * @param serverSocker Provided socket to accept connections on.
     * @param fs     A proxy filesystem to serve files from. See the VFilesystem
     *               class for more detailed documentation of its usage.
     * @throws IOException If an I/O error is detected on the server. This
     *                     should be a fatal error, your file server
     *                     implementation is not expected to ever throw
     *                     IOExceptions during normal operation.
     */
    @Throws(IOException::class)
    fun run(serverSocker: ServerSocket, fs: VFilesystem) {

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {
            val socket: Socket = serverSocker.accept()

            val inputStream = BufferedReader(InputStreamReader(socket.getInputStream()))
            val outStream = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))

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
                outStream.close()
                inputStream.close()
                socket.close()
            }
        }
    }
}