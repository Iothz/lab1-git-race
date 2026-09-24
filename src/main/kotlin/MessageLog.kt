package es.unizar.webeng.hello

import org.springframework.stereotype.Service
import java.util.Queue
import java.util.ArrayDeque
import java.time.LocalDateTime

data class LoggedMessage(
    val text: String,
    val sentAt: LocalDateTime
)

@Service
class MessageLog(
    //Queue of the last 5 timed messages
    private val messages: Queue<LoggedMessage> = java.util.ArrayDeque(5)
){

    fun addMessage(message: String) {
        if (messages.size >= 5) {
            messages.poll()
        }
        messages.offer(LoggedMessage(message, LocalDateTime.now()))
    }

    fun getMessages(): List<LoggedMessage> {
        return messages.toList()
    }
}