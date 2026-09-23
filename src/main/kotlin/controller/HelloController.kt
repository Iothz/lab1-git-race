package es.unizar.webeng.hello.controller

import es.unizar.webeng.hello.MessageLog
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.ZoneId

import es.unizar.webeng.hello.RequestCounter

internal fun timeGreeting(
    name: String,
    instant: Instant = Instant.now(),
    zone: ZoneId = ZoneId.systemDefault()
): String {
    val hour = instant.atZone(zone).hour
    val greeting = when (hour) {
        in 6..11 -> "Good morning"
        in 12..17 -> "Good afternoon"
        in 18..21 -> "Good evening"
        else -> "Good night"
    }

    return "$greeting, $name!"
}

@Controller
class HelloController(
    @param:Value("\${app.message:Hello World}") 
    private val message: String,
    private val requestCounter: RequestCounter,
    private val messageLog: MessageLog
) {

    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String,
        @RequestParam(defaultValue = "false") count: Boolean = false
    ): String {
        val petitionCount = if (count) requestCounter.increment() else requestCounter.getCurrentCount()

        val greeting = if (name.isNotBlank()) timeGreeting(name) else message
        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        model.addAttribute("petitionCount", petitionCount)

        if (count) {
            messageLog.addMessage(greeting)
        }
        model.addAttribute("messages", messageLog.getMessages())

        return "welcome"
    }
}

@RestController
class HelloApiController(
    private val requestCounter: RequestCounter,
    private val messageLog: MessageLog
) {

    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, Any> {
        val petitionCount = requestCounter.increment()
        val greeting = timeGreeting(name)
        messageLog.addMessage(greeting)

        return mapOf(
            "message" to greeting,
            "timestamp" to Instant.now().toString(),
            "petitionCount" to petitionCount.toString(),
            "messages" to messageLog.getMessages()
        )
    }
}
