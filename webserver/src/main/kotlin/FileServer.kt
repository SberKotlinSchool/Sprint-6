import FileServer.ClientHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 *
 */
class FileServer {

    private val poolSize = 1
    private val queueSize = 1

    //Создал фиксированный пул потоков для того, что бы клиент не вставал в очередь пока идет обработка запроса предыдущего клиента.
    //Так же задал ограничение очереди ожидания потоков и реализовал альтернативную стратегию ответа клиентам, если очередь заполнена.
    private val executorService = ThreadPoolExecutor(
        poolSize,
        poolSize,
        0L,
        TimeUnit.MILLISECONDS,
        LinkedBlockingQueue<Runnable>(queueSize)
    )


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
        logger.info("Сервер запущен. Ожидание подключений...")

        while (true) {
            val clientSocket: Socket = socket.accept()
            logger.info("Подключение принято от: " + clientSocket.getInetAddress())

            try {
                executorService.execute(ClientHandler(clientSocket, fs))
            } catch (e: Exception) {
                val errorMessage = "В данный момент все обработчики заняты, попробуйте повторить запрос позже..."
                logger.error(errorMessage)
                clientSocket.getOutputStream().use {
                    it.write(
                        HttpResponse(
                            "HTTP/1.0 500 Internal Server Error\r\\n" + "\r\n" + "Server: FileServer\r\n\r\n",
                            errorMessage
                        ).toString()
                            .toByteArray()
                    )
                }
            }
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(FileServer::class.java)
    }

    internal class ClientHandler(private val clientSocket: Socket, private val fs: VFilesystem) : Runnable {
        override fun run() {
            BufferedReader(InputStreamReader(clientSocket.getInputStream())).use {
                val clientHttpRequest = it.readLine()
                logger.info("Входящий запрос: $clientHttpRequest")

                val vPath = VPath(parseHttpRequest(clientHttpRequest))

                val httpResponse: HttpResponse = fs.readFile(vPath)?.let { file ->
                    HttpResponse("HTTP/1.0 200 OK" + "\r\n" + "Server: FileServer\r\n\r\n", "$file\r\n")
                } ?: run {
                    HttpResponse("HTTP/1.0 404 Not Found\r\\n" + "\r\n" + "Server: FileServer\r\n\r\n")
                }
                logger.info("Сформирован ответ клиенту: $httpResponse")
                clientSocket.getOutputStream().use { os ->
                    os.write(httpResponse.toString().toByteArray())
                }
            }
        }

        private fun parseHttpRequest(httpRequest: String): String {
            return httpRequest.let {
                val startIndex = it.indexOf(' ')
                val endIndex = it.indexOf(' ', startIndex + 1)
                it.substring(startIndex + 1, endIndex)
            }
        }

        companion object {
            val logger: Logger = LoggerFactory.getLogger(ClientHandler::class.java)
        }
    }
}

/**
 *
 *
 */
data class HttpResponse(val str: String, val body: String = "") {
    override fun toString(): String {
        return "$str$body"
    }
}