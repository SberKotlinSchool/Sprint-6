import domain.Request
import domain.Response
import exception.RequestParseException
import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import util.HttpStatus
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */

fun main() {
    val socket = ServerSocket(9999)
    socket.setReuseAddress(true)
    val fs = VFilesystem()
    fs.addFile(VPath("/static/A.txt"), "sdfghj")
    FileServer().run(socket, fs)
}

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
    private val serverName = this.javaClass.name

    @Throws(IOException::class)
    fun run(server: ServerSocket, fs: VFilesystem) {
        server.use {
            while (true) {
                println("Server started at port ${it.localPort}. Listening for client connections...")
                handle(it, fs)
            }
        }
    }

    private fun handle(server: ServerSocket, fs: VFilesystem) {
        server.accept().use { socket ->
            val reader = socket.getInputStream().bufferedReader()
            val rawRequest = reader.readLine()

            println("Receive from ${socket.remoteSocketAddress}  > clientRequest $rawRequest")

            val writer = PrintWriter(socket.getOutputStream())

            rawRequest.runCatching {
                val request = Request.fromRaw(rawRequest)
                val response = (fs.readFile(VPath(request.path))?.let { content ->
                    Response(status = HttpStatus.OK, server = serverName, content = content)
                } ?: Response(status = HttpStatus.NOT_FOUND, server = serverName)).toRaw()
                writer.println(response)
                writer.flush()
                println("Send to ${socket.remoteSocketAddress} > $response")
            }.onFailure { ex ->
                when (ex) {
                    is RequestParseException -> {
                        val response = Response(
                                status = HttpStatus.INTERNAL_SERVER_ERROR,
                                server = serverName,
                                content = ex.localizedMessage).toRaw()
                        writer.println(Response)
                        writer.flush()
                        println("Send to ${socket.remoteSocketAddress} > $response")
                    }

                    else -> throw ex
                }
            }
        }
    }
}




