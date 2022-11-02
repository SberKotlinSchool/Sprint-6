import data.Request
import data.Response
import data.ResponseHeader
import mu.KotlinLogging
import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.net.http.HttpRequest

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

        socket.use {serverSocket ->
            /**
             * Enter a spin loop for handling client requests to the provided
             * ServerSocket object.
             */
            while (true) {

                processRequest(serverSocket.accept(), fs)


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
            }
        }
    }

    private fun processRequest(socket: Socket, fs: VFilesystem) {
        LOG.info { "client connected:${socket.remoteSocketAddress}" }
        socket.use { s ->
            // читаем от клиента сообщение
            val reader = s.getInputStream().bufferedReader()
            val clientRequest = reader.readLine()
            LOG.info { "receive from ${socket.remoteSocketAddress}  > clientRequest $clientRequest" }

            val request = Request(clientRequest.split(" ")[0],
                clientRequest.split(" ")[1],
                clientRequest.split(" ")[2]
            )

            var file = fs.readFile(VPath(request.requestBody))

            // отправляем ответ
            val writer = PrintWriter(s.getOutputStream())
            val httpHeader = ResponseHeader(request.requestProtocol,
                200,
                "text/html",
                "Closed"
                )

            val httpResponse = Response(httpHeader,file).buildResponse();


            val serverResponse = "Server response: $httpResponse}"
            writer.println(httpResponse)
            writer.flush()
            LOG.info { "send to ${socket.remoteSocketAddress} > $httpResponse" }
        }
    }

    companion object {
        val LOG = KotlinLogging.logger {}
    }
}