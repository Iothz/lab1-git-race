package es.unizar.webeng.hello

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


/*
* A filter that counts the number of requests to the health endpoint.
* It is not optimal to use a filter for this purpose, as it checks every request, but it is a simple way to achieve the desired
* functionality without modifying the actuator's health endpoint.
*/
// @Component Spring will automatically detect this class and register it as a filter in the application context.
@Component
class HealthRequestCounterFilter(
    private val requestCounter: RequestCounter
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.method == "GET" && request.requestURI == "/actuator/health") {
            if (request.getParameter("count") == "true") {
                val count = requestCounter.increment()
                response.setHeader("X-Request-Count", count.toString())
            }
        }

        filterChain.doFilter(request, response)
    }
}
