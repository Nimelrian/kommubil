package de.nimelrian.kommubil.backend.messages.rest

import de.nimelrian.kommubil.backend.messages.MessageEndpoint
import de.nimelrian.kommubil.backend.messages.business.MessageService
import de.nimelrian.kommubil.backend.messages.model.Message
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["http://localhost:8080"])
class MessageController(
    private val service: MessageService
) : MessageEndpoint {
    @GetMapping("/messages")
    override fun getMessages(): Set<Message> {
        return service.getCurrentMessages()
    }
}
