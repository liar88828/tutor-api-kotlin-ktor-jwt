package example.com.plugins

import example.com.routing.authRoute
import example.com.routing.userRoute
import example.com.service.JwtService
import example.com.service.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userService: UserService,
    jwtService: JwtService
) {
    routing{
        route("/api/auth"){
            authRoute(jwtService)

        }
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

