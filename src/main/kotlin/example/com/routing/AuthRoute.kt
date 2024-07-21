package example.com.routing

import example.com.routing.request.LoginRequest
import example.com.service.JwtService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoute(jwtService: JwtService) {

	post {
		val loginRequest = call.receive<LoginRequest>()
		val token = jwtService.createJwtToken(loginRequest)

		token?.let {
			call.respond(hashMapOf("token" to token))
		} ?: call.respond(HttpStatusCode.Unauthorized)

	}

}