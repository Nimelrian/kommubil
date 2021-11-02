package de.nimelrian.kommubil.backend.messages

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
}
