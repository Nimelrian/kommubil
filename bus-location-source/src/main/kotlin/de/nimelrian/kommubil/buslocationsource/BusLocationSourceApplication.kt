package de.nimelrian.kommubil.buslocationsource

import mu.KotlinLogging
import org.reactivestreams.Publisher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.function.context.PollableBean
import org.springframework.context.annotation.Bean
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.websocket.ClientWebSocketContainer
import org.springframework.integration.websocket.IntegrationWebSocketContainer
import org.springframework.integration.websocket.inbound.WebSocketInboundChannelAdapter
import org.springframework.messaging.Message
import org.springframework.web.socket.client.jetty.JettyWebSocketClient
import reactor.core.publisher.Flux
import java.util.function.Supplier


private val log = KotlinLogging.logger {}

@SpringBootApplication
class BusLocationSourceApplication {
    @Bean
    fun webSocketClient() = JettyWebSocketClient()

    @Bean
    fun clientWebSocketContainer(): IntegrationWebSocketContainer {
        return ClientWebSocketContainer(webSocketClient(), "wss://websocket.busradar.conterra.de")
    }

    @Bean
    fun payloadSupplierFlow(webSocketContainer: IntegrationWebSocketContainer): Publisher<Message<ByteArray>> =
        IntegrationFlows.from(WebSocketInboundChannelAdapter(webSocketContainer)).log().toReactivePublisher()

    @PollableBean
    fun locationSupplier(payloadSupplier: Publisher<Message<ByteArray>>) = Supplier {
        Flux.from(payloadSupplier)
    }
}

fun main(args: Array<String>) {
    runApplication<BusLocationSourceApplication>(*args)
}
