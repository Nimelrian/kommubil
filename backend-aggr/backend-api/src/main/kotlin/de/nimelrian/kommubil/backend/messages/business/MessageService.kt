package de.nimelrian.kommubil.backend.messages.business

import de.nimelrian.kommubil.backend.messages.model.Message
import org.postgis.Point

interface MessageService {
    fun getCurrentMessages(): Set<Message>
    fun createMessage(command: CreateMessageCommand): Message
}

data class CreateMessageCommand(
    var content: String,
    var author: String,
    var location: Point,
)
