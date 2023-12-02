import mu.KotlinLogging
import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.net.ServerSocket

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    companion object {
        private val LOG = KotlinLogging.logger {}
    }

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

            val connectionSocket = socket.accept()
            LOG.info("client connected:${connectionSocket.remoteSocketAddress}")
            connectionSocket.use { s ->
                s.getInputStream().use { inputStream ->
                    val reader = inputStream.bufferedReader()
                    val clientRequest = reader.readLine()
                    LOG.info("Receive from ${connectionSocket.remoteSocketAddress}  > clientRequest $clientRequest")
                    val filePath = clientRequest.substringAfter("GET").substringBefore("HTTP").trim()

                    val data = fs.readFile(VPath(filePath))

                    val output =
                        if (data == null) {
                            "HTTP/1.0 404 Not Found\r\nServer: FileServer\r\n\r\n"
                        } else {
                            "HTTP/1.0 200 OK\r\nServer: FileServer\r\n\r\n$data\r\n"
                        }
                    s.getOutputStream().use { outputStream ->
                        outputStream.write(output.toByteArray())
                    }
                    LOG.info("Send to ${connectionSocket.remoteSocketAddress} > $output")
                }
            }
        }
    }
}