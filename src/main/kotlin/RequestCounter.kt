package es.unizar.webeng.hello

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class RequestCounter {
    private val counter = AtomicInteger(0)

    fun increment(): Int = counter.incrementAndGet()

    fun getCurrentCount(): Int = counter.get()
}