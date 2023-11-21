import ru.sber.filesystem.VFilesystem
import ru.sber.filesystem.VPath
import java.io.IOException
import java.net.ServerSocket

class FileServer {

    private val path = "path"
    private val regex = "^GET (?<$path>(/[\\da-zA-Z.]+)+) HTTP/1\\.1$".toRegex()
    private val resOK = "HTTP/1.0 200 OK\r\nServer: FileServer\r\n\r\n%s\r\n"
    private val resNo = "HTTP/1.0 404 Not Found\r\nServer: FileServer\r\n\r\n"
    @Throws(IOException::class)
    fun run(socket: ServerSocket, fs: VFilesystem) {
        while (true) {
            socket.accept().use { s ->
                val request = s.getInputStream().bufferedReader().readLine()

                val path = regex.matchEntire(request)!!
                    .groups[path]?.value.let { VPath(it) }

                val writer = s.getOutputStream().bufferedWriter()
                fs.readFile(path)?.let { content ->
                    writer.write(resOK.format(content))
                } ?: writer.write(resNo)
                writer.flush()
            }
        }
    }
}