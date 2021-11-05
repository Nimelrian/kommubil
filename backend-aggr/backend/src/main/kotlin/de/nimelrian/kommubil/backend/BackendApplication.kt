package de.nimelrian.kommubil.backend


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.nimelrian.kommubil.backend.messages.model.Message
import de.nimelrian.kommubil.backend.messages.persistence.MessageRepository
import mu.KotlinLogging
import org.postgis.Point
import org.postgis.geojson.PostGISModule
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

private val log = KotlinLogging.logger {}

@SpringBootApplication
class BackendApplication {
    @Bean
    fun runner(repository: MessageRepository) = ApplicationRunner {
        repository.deleteAll()
        repository.create(
            Message(
                content = "Hello World",
                author = "Nimelrian",
                location = Point(51.97604, 7.59637)
            )
        )
    }

    @Bean
    fun objectMapper(): ObjectMapper {

        return ObjectMapper().registerModules(
            KotlinModule(),
            PostGISModule()
        )
    }
}

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
