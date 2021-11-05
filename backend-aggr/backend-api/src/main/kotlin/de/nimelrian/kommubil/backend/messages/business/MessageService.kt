package de.nimelrian.kommubil.backend.messages.business

import de.nimelrian.kommubil.backend.messages.model.Message

interface MessageService {
    fun getCurrentMessages(): Set<Message>
}
