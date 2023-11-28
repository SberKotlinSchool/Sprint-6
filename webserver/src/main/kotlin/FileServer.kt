import mu.KotlinLogging
import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

private const val INPUT_PARAM_SIZE = 3
private const val INPUT_PARAM_GET = "GET"
private const val RESPONSE_200 = "HTTP/1.0 200 OK\r\nServer: FileServer\r\n\r\n"
private const val RESPONSE_404 = "HTTP/1.0 404 Not Found\r\nServer: FileServer\r\n\r\n"

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
        LOG.info { "Server started at port $socket. Waiting for connections..." }
        while (true) {
            socket.accept().use { handleRequest(it, fs) }
        }
    }

    private fun handleRequest(clientSocket: Socket, fs: VFilesystem) {
        LOG.info { "Client connected: $clientSocket" }

        clientSocket.getInputStream().bufferedReader().use { bufferedReader ->
            val inputRequest = bufferedReader.readLine()
            LOG.info { "Client receive:\n$inputRequest" }

            val inputParam = inputRequest.split(" ")
            validateRequest(inputParam)
            sendResponse(clientSocket, getResponseMessage(fs, inputParam[1]))
        }
    }

    private fun validateRequest(inputParam: List<String>) {
        if (!(inputParam.size == INPUT_PARAM_SIZE && inputParam[0] == INPUT_PARAM_GET)) {
            throw UnsupportedOperationException()
        }
    }

    private fun sendResponse(clientSocket: Socket, result: String) {
        LOG.info { "Server receive to $clientSocket : $result" }
        PrintWriter(clientSocket.getOutputStream()).use { writer ->
            writer.println(result)
            writer.flush()
        }
    }

    private fun getResponseMessage(fs: VFilesystem, path: String): String {
        LOG.info { "Client search file: $path" }
        val file = fs.readFile(VPath(path))
        return if (file == null) RESPONSE_404 else RESPONSE_200 + file
    }

    companion object {
        val LOG = KotlinLogging.logger {}
    }
}