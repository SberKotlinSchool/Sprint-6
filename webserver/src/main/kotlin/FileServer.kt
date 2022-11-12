import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

class FileServer {

    @Throws(IOException::class)
    fun run(socket: ServerSocket, fs: VFilesystem) {
        while (true) {
            handle(socket.accept(), fs)
        }
    }

    private fun handle(socket: Socket, fs: VFilesystem) {
        socket.use { s ->
            val reader = s.getInputStream().bufferedReader()
            val request = reader.readLine().split(" ")
            if (request.size == 3) {
                val path = request[1]

                val fileBody = fs.readFile(VPath(path))
                s.getOutputStream().bufferedWriter().use { writer ->
                    if (fileBody == null) {
                        writer
                            .appendLine("HTTP/1.0 404 Not Found")
                            .appendLine("Server: FileServer")
                    } else {
                        writer
                            .appendLine("HTTP/1.0 200 OK")
                            .appendLine("Server: FileServer")
                            .appendLine()
                            .appendLine(fileBody)
                            .appendLine()
                    }
                }
            }
        }
    }
}