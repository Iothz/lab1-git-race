package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.ui.Model
import org.springframework.ui.ExtendedModelMap
import es.unizar.webeng.hello.RequestCounter
import java.time.Instant
import java.time.ZoneOffset

class HelloControllerUnitTests {
    private lateinit var controller: HelloController
    private lateinit var model: Model
    
    @BeforeEach
    fun setup() {
        controller = HelloController("Test Message", RequestCounter())
        model = ExtendedModelMap()
    }
    
    @Test
    fun `should return welcome view with default message`() {
        val view = controller.welcome(model, "")
        
        assertThat(view).isEqualTo("welcome")
        assertThat(model.getAttribute("message")).isEqualTo("Test Message")
        assertThat(model.getAttribute("name")).isEqualTo("")
    }
    
    @Test
    fun `should return welcome view with personalized message`() {
        val view = controller.welcome(model, "Developer")
        
        assertThat(view).isEqualTo("welcome")
        val greeting = model.getAttribute("message")?.toString()
            ?: error("Expected a greeting in the model")
        assertThat(greeting).startsWith("Good ")
        assertThat(model.getAttribute("name")).isEqualTo("Developer")
    }

    @Test
    fun `should choose greeting according to the hour`() {
        val zone = ZoneOffset.UTC

        assertThat(timeGreeting("Developer", Instant.parse("2026-01-01T06:00:00Z"), zone))
            .isEqualTo("Good morning, Developer!")
        assertThat(timeGreeting("Developer", Instant.parse("2026-01-01T12:00:00Z"), zone))
            .isEqualTo("Good afternoon, Developer!")
        assertThat(timeGreeting("Developer", Instant.parse("2026-01-01T18:00:00Z"), zone))
            .isEqualTo("Good evening, Developer!")
        assertThat(timeGreeting("Developer", Instant.parse("2026-01-01T22:00:00Z"), zone))
            .isEqualTo("Good night, Developer!")
    }
    
    @Test
    fun `should return API response with timestamp`() {
        val apiController = HelloApiController(RequestCounter())
        val response = apiController.helloApi("Test")
        
        assertThat(response).containsKey("message")
        assertThat(response).containsKey("timestamp")
        assertThat(response["message"]).startsWith("Good ")
        assertThat(response["message"]).endsWith("Test!")
        assertThat(response["timestamp"]).isNotNull()
    }
}
