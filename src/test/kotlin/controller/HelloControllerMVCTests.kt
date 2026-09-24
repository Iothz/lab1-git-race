package es.unizar.webeng.hello.controller

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.context.annotation.Import
import es.unizar.webeng.hello.MessageLog
import es.unizar.webeng.hello.RequestCounter

@WebMvcTest(HelloController::class, HelloApiController::class)
@Import(RequestCounter::class, MessageLog::class)
class HelloControllerMVCTests {
    @Value("\${app.message:Welcome to the Modern Web App!}")
    private lateinit var message: String

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return home page with default message`() {
        mockMvc.perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", equalTo(message)))
            .andExpect(model().attribute("name", equalTo("")))
    }
    
    @Test
    fun `should return home page with personalized message`() {
        mockMvc.perform(get("/").param("name", "Developer"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(view().name("welcome"))
            .andExpect(model().attribute("message", startsWith("Good ")))
            .andExpect(model().attribute("name", equalTo("Developer")))
    }

    @Test
    fun `should increment petition counter only when requested`() {
        val firstCountedRequest = mockMvc.perform(get("/").param("count", "true"))
            .andExpect(status().isOk)
            .andReturn()
            .modelAndView!!
            .model["petitionCount"] as Int

        val nonCountedRequest = mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andReturn()
            .modelAndView!!
            .model["petitionCount"] as Int

        val secondCountedRequest = mockMvc.perform(get("/").param("count", "true"))
            .andExpect(status().isOk)
            .andReturn()
            .modelAndView!!
            .model["petitionCount"] as Int

        assertThat(nonCountedRequest, equalTo(firstCountedRequest))
        assertThat(secondCountedRequest, equalTo(firstCountedRequest + 1))
    }
    
    @Test
    fun `should return API response as JSON`() {
        mockMvc.perform(get("/api/hello").param("name", "Test"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", startsWith("Good ")))
            .andExpect(jsonPath("$.timestamp").exists())
    }
}
