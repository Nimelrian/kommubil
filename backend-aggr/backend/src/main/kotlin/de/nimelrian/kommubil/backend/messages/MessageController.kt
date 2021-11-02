package de.nimelrian.kommubil.backend.messages

import org.postgis.Point
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["http://localhost:8080"])
class MessageController : MessageEndpoint {
    @GetMapping("/messages")
    override fun getMessages(): Message {
        return Message(
            content = "Hello World",
            author = "Technologiepark Münster",
            location = Point(51.97604, 7.59637)
        )
    }
}
