package example.com.routing

import example.com.model.User
import example.com.routing.request.UserReq
import example.com.routing.response.UserRes
import example.com.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoute(userService: UserService) {
	post {
		val userRequest = call.receive<UserReq>()
		val createdUser = userService.save(
			user = userRequest.toModel()
		) ?: return@post call.respond(HttpStatusCode.BadRequest)

		call.response.header(name = "id", value = createdUser.id.toString())
		call.respond(message = HttpStatusCode.Created)
	}


	get {
		val users = userService.findAll()
		call.respond(message = users.map(User::toResponse))
	}

	authenticate("another-auth") {
		get("/{id}") {
			val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
			val foundUser = userService.findById(id) ?: return@get call.respond(HttpStatusCode.NotFound)

			call.respond(message = foundUser.toResponse())
		}
	}

}

private fun User.toResponse(): UserRes = UserRes(
	id = this.id,
	username = this.username,
)

private fun UserReq.toModel(): User = User(
	id = UUID.randomUUID(),
	username = this.username,
	password = this.password,
)


