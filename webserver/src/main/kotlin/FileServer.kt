import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket

/**
 *
 * Cервер по поиску файла в виртуальной файловой системе.
 *
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    private val OK_RESPONSE = { it: String ->
        """
         HTTP/1.0 200 OK
         Server: FileServer
         
         $it
    """.trimIndent()
    }

    private val FILE_NOT_FOUND_RESPONSE = """
        HTTP/1.0 404 Not Found
        Server: FileServer
    """.trimIndent()

    private val BAD_REQUEST_RESPONSE = """
        HTTP/1.0 400 Bad Request
        Server: FileServer
    """.trimIndent()


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
    fun run(serverSocket: ServerSocket, fs: VFilesystem) {
        println("Сервер запущен на порту: ${serverSocket.localPort}")

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {

            val socket = serverSocket.accept()
            println("Открыто соединение с клиентом: ${socket.remoteSocketAddress}")

            socket.use { s ->
                val responseStr = getResponse(s.getInputStream().bufferedReader().readLine(), fs)

                s.getOutputStream().use {
                    val writer = PrintWriter(it)
                    writer.println(responseStr)
                    writer.flush()
                }
                println("Закрыто соединение с клиентом  ${socket.remoteSocketAddress}")
            }//Соединение будет закрыто с клиентом
        }
    }

    /**
     * Получение файла из виртуальной ФС
     *
     * @param request Строка, которую присылает клиент. Ожидается формат "GET данные HTTP/1.1"
     * @param fs Виртуальная файловая система для операции с файлами
     */
    private fun getResponse(request: String, fs: VFilesystem): String {
        val regex = """^GET .* HTTP/1.1$""".toRegex()
        if (!regex.containsMatchIn(request)) {
            return BAD_REQUEST_RESPONSE
        }
        val path = request.substring(4, request.length - 9)
        val file = fs.readFile(VPath(path))
        return if (file == null) {
            FILE_NOT_FOUND_RESPONSE
        } else {
            OK_RESPONSE.invoke(file)
        }
    }
}