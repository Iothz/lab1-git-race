package es.unizar.webeng.hello.controller

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
    private val requestCounter: RequestCounter
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

        return "welcome"
    }
}

@RestController
class HelloApiController(
    private val requestCounter: RequestCounter
) {

    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, String> {
        val petitionCount = requestCounter.increment()

        return mapOf(
            "message" to timeGreeting(name),
            "timestamp" to Instant.now().toString(),
            "petitionCount" to petitionCount.toString()
        )
    }
}
