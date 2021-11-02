package de.nimelrian.kommubil.backend.messages

interface MessageEndpoint {
    fun getMessages(): Message
}
