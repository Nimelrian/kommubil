package de.nimelrian.kommubil.frontend.map

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MapController {

    @GetMapping("/")
    fun index(): String {
        return "index"
    }
}
