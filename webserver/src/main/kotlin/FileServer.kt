import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets
import mu.KotlinLogging

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
        LOG.info("Сервер запущен.")
        while (true) {
            val clientS: Socket = socket.accept()
            LOG.info("Принято подключение с адреса: " + clientS.inetAddress)
            clientS.use {
                val ips = BufferedReader(InputStreamReader(it.getInputStream()))
                val ops = DataOutputStream(it.getOutputStream())
                val reqLine = ips.readLine()
                LOG.info("Запрос от " + clientS.inetAddress + ":\n" + reqLine)

                val request = reqLine.split(" ")

                val path = request[1]
                val content = fs.readFile(VPath(path))
                val response: String = if (request.size != 3 || request[0] != "GET" || content == null) {
                    "HTTP/1.0 404 Not Found\r\nServer: FileServer\r\n\r\n"

                } else {
                    "HTTP/1.0 200 OK\r\nServer: FileServer\r\n\r\n$content\r\n"
                }
                LOG.info("Ответ "+ clientS.inetAddress + ":\n" + content)
                ops.write(response.toByteArray(StandardCharsets.UTF_8))
                ops.close()
            }
        }
    }

    companion object{
        val LOG = KotlinLogging.logger{}
    }

}