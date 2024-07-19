package example.com.plugins

import example.com.routing.userRoute
import example.com.service.userServ
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userService: userServ
) {
    routing{
        route("/api/user"){
            userRoute(userService)

        }
}
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
