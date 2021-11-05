package de.nimelrian.kommubil.backend.messages.business

import de.nimelrian.kommubil.backend.messages.model.Message
import de.nimelrian.kommubil.backend.messages.persistence.MessageRepository
import org.springframework.stereotype.Service

@Service
class RepositoryMessageService(
    private val repository: MessageRepository
) : MessageService {
    override fun getCurrentMessages(): Set<Message> {
        return repository.findAll().toSet()
    }

    override fun createMessage(command: CreateMessageCommand): Message {
        val entity = Message(
            content = command.content,
            author = command.author,
            location = command.location
        )
        return repository.create(entity)
    }
}
