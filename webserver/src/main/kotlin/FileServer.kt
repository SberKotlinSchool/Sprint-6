import ru.sber.filesystem.VFilesystem
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets


/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
class FileServer {

    /**
     * Main entrypoint for the basic file server.
     *
     * @param serverSocker Provided socket to accept connections on.
     * @param fs     A proxy filesystem to serve files from. See the VFilesystem
     *               class for more detailed documentation of its usage.
     * @throws IOException If an I/O error is detected on the server. This
     *                     should be a fatal error, your file server
     *                     implementation is not expected to ever throw
     *                     IOExceptions during normal operation.
     */
    @Throws(IOException::class)
    fun run(serverSocker: ServerSocket, fs: VFilesystem) {

        /**
         * Enter a spin loop for handling client requests to the provided
         * ServerSocket object.
         */
        while (true) {
            // TODO 1) Use socket.accept to get a Socket object
            val socket: Socket = serverSocker.accept()
            println("Client connected!")


            /*
            * TODO 2) Using Socket.getInputStream(), parse the received HTTP
            * packet. In particular, we are interested in confirming this
            * message is a GET and parsing out the path to the file we are
            * GETing. Recall that for GET HTTP packets, the first line of the
            * received packet will look something like:
            *
            *     GET /path/to/file HTTP/1.1
            */

            //  val inputStream = socket.getInputStream()
            val inputStream = BufferedReader(InputStreamReader(socket.getInputStream()))
            println(" что было отправлено клиентом: ${inputStream.readLine()}")
            val out = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
            // val outputStream = socket.getOutputStream()
            val response = "HTTP/1.0 200 OK\\r\\n\n" +
                    "             *   Server: FileServer\\r\\n\n" +
                    "             *   \\r\\n\n" +
                    "             *   FILE CONTENTS HERE\\r\\n"

          //  outputStream.write(response.toByteArray());
            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
            out.write("Server: Apache/0.8.4\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("Content-Length: 59\r\n");
            out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
            out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
            out.write("\r\n");
            out.flush();

            out.close()
            inputStream.close()
            socket.close()
            //  println("ПОЛУЧИЛА ЗАПРОС: ${String(inputStream.readAllBytes(), StandardCharsets.UTF_8)}")
            //      val (httpRq, path, httpVersion) = String(inputStream.readAllBytes(), StandardCharsets.UTF_8).split(" ")
            //    println(" path to the file $path ")
            //   val readFile = fs.readFile(VPath(path))
            //     println(" вирт система вернула: $readFile")

            // if (readFile != null) {

            //   val response = " HTTP/1.0 200 OK\\r\\n"
            //  outputStream.write(response.toByteArray())


            //   }


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