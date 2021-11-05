package de.nimelrian.kommubil.backend.messages.rest

import de.nimelrian.kommubil.backend.messages.MessageEndpoint
import de.nimelrian.kommubil.backend.messages.business.CreateMessageCommand
import de.nimelrian.kommubil.backend.messages.business.MessageService
import de.nimelrian.kommubil.backend.messages.model.Message
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:8080"])
class MessageController(
    private val service: MessageService
) : MessageEndpoint {
    @GetMapping("/messages")
    override fun getMessages(): Set<Message> {
        return service.getCurrentMessages()
    }

    @PostMapping("/messages")
    override fun createMessage(@RequestBody data: CreateMessageCommand): Message {
        return service.createMessage(data)
    }
}
