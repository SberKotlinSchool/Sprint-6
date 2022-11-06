package data

import java.time.LocalDateTime
import java.time.LocalDateTime.now

data class ResponseHeader (
      var protocol : String,
      var status : String,
      var statusCode : Int,
      var contentType : String,
      var connection: String
) {


    val httpHeader = """
        $protocol $statusCode $status
        Date: ${LocalDateTime.now()}
        Last-Modified: ${LocalDateTime.now()}
        Content-Type: $contentType
        Connection: $connection  
    """.trimIndent()

     fun getHeader() : String {
        return httpHeader
    }


}




//HTTP/1.1 200 OK
//Date: Mon, 27 Jul 2009 12:28:53 GMT
//Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT
//Content-Type: text/html
//Connection: Closed