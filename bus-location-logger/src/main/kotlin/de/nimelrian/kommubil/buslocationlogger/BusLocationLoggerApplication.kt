package de.nimelrian.kommubil.buslocationlogger

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.function.Consumer
import java.util.function.Supplier

private val log = KotlinLogging.logger { }

@SpringBootApplication
class BusLocationLoggerApplication {
    @Bean
    fun locationLogger() = Consumer<String> {
        log.info { "Received: $it" }
    }
}

fun main(args: Array<String>) {
    runApplication<BusLocationLoggerApplication>(*args)
}
