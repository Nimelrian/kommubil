package de.nimelrian.kommubil.backend.messages

import de.nimelrian.kommubil.backend.messages.business.CreateMessageCommand
import de.nimelrian.kommubil.backend.messages.model.Message

interface MessageEndpoint {
    fun getMessages(): Set<Message>
    fun createMessage(data: CreateMessageCommand): Message
}
