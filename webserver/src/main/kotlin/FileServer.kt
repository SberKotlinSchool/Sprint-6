import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.*
import java.net.ServerSocket
import java.util.*

class FileServer {

    @Throws(IOException::class)
    fun run(socket: ServerSocket, fs: VFilesystem) {

        while (true) {
            socket.accept().use { client ->
                val clientMessage = client.getInputStream().bufferedReader().readLine().split(" ")[1]
                val response = fs.readFile(VPath(clientMessage))
                client.getOutputStream().bufferedWriter().use { bufferedWriter ->
                    when (response == null) {
                        true -> bufferedWriter
                            .append("HTTP/1.0 404 Not Found")
                            .appendLine("Server: FileServer")
                            .appendLine("")

                        false -> bufferedWriter
                            .append("HTTP/1.0 200 OK")
                            .appendLine("Server: FileServer")
                            .appendLine("")
                            .appendLine(response)
                            .appendLine("")
                    }
                }
            }
        }
    }
}