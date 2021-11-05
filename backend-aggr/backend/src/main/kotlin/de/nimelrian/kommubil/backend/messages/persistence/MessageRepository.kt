package de.nimelrian.kommubil.backend.messages.persistence

import de.nimelrian.kommubil.backend.MessagesTable
import de.nimelrian.kommubil.backend.common.persistence.CrudRepository
import de.nimelrian.kommubil.backend.messages.model.Message
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface MessageRepository : CrudRepository<Message, UUID>

@Repository
@Transactional
class ExposedMessageRepository : MessageRepository {
    override fun create(m: Message): Message {
        val id = MessagesTable.insertAndGetId {
            it[id] = m.id
            it[author] = m.author
            it[content] = m.content
            it[location] = m.location
        }
        assert(id.value == m.id)
        return m
    }

    override fun findAll(): Iterable<Message> = MessagesTable
        .selectAll()
        .map {
            Message(
                id = it[MessagesTable.id].value,
                content = it[MessagesTable.content],
                author = it[MessagesTable.author],
                location = it[MessagesTable.location]
            )
        }

    override fun deleteAll(): Int = MessagesTable.deleteAll()
}
