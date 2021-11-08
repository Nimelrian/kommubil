package de.nimelrian.kommubil.buslocationsource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.function.context.PollableBean
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import java.net.URI
import java.util.function.Supplier

@SpringBootApplication
class BusLocationSourceApplication {
    val messages: Flux<String> = run {
        val sink = Sinks.many().unicast().onBackpressureBuffer<String>()
        val client = ReactorNettyWebSocketClient()
        val webSocketUri = URI.create("wss://websocket.busradar.conterra.de")
        client.execute(webSocketUri) { session ->
            session.receive()
                .doOnEach { message -> sink.tryEmitNext(message.get()!!.payloadAsText) }
                .then()
        }.subscribe()
        sink.asFlux()
    }



    @PollableBean
    fun locationSupplier() = Supplier<Flux<String>> {
        messages
    }
}

fun main(args: Array<String>) {
    runApplication<BusLocationSourceApplication>(*args)
}
