package es.unizar.webeng.hello

import org.springframework.stereotype.Service
import java.util.Queue
import java.util.ArrayDeque

@Service
class MessageLog(
    //Queue of the last 5 messages
    private val messages: Queue<String> = java.util.ArrayDeque(5)
){

    fun addMessage(message: String) {
        if (messages.size >= 5) {
            messages.poll()
        }
        messages.offer(message)
    }

    fun getMessages(): List<String> {
        return messages.toList()
    }
}