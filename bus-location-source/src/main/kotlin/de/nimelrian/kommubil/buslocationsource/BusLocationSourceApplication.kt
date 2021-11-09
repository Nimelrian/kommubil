package de.nimelrian.kommubil.buslocationsource

import mu.KotlinLogging
import org.springframework.beans.factory.DisposableBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.function.context.PollableBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.net.URI
import java.util.function.Supplier


private val log = KotlinLogging.logger {}

@Component
class WebSocketSession(val websocketPayloads: Sinks.Many<String>) : DisposableBean {
    private val socketSubscription: Disposable

    init {
        val client = ReactorNettyWebSocketClient()
        val webSocketUri = URI.create("wss://websocket.busradar.conterra.de")

        socketSubscription = client.execute(webSocketUri) { session ->
            session.receive()
                .doOnEach { message ->
                    message.get()?.let { websocketPayloads.tryEmitNext(it.payloadAsText) }
                }
                .then()
        }.subscribe()
    }

    override fun destroy() {
        log.info { "Closing websocket" }
        socketSubscription.dispose()
    }
}

@SpringBootApplication
class BusLocationSourceApplication {
    @Bean
    fun websocketPayloads(): Sinks.Many<String> = Sinks.many().unicast().onBackpressureBuffer()

    @PollableBean
    fun locationSupplier(websocketPayloads: Sinks.Many<String>) = Supplier<Flux<String>> {
        websocketPayloads.asFlux()
    }
}

fun main(args: Array<String>) {
    runApplication<BusLocationSourceApplication>(*args)
}
