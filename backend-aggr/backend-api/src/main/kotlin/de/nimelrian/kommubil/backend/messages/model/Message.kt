package de.nimelrian.kommubil.backend.messages.model

import org.postgis.Point
import java.util.*

class Message(
    val id: UUID = UUID.randomUUID(),
    var content: String,
    var author: String,
    var location: Point,
) {
    override fun toString(): String {
        return "Message(id=$id, content='$content', author='$author', location=$location)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (id != other.id) return false
        if (content != other.content) return false
        if (author != other.author) return false
        if (location != other.location) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}
