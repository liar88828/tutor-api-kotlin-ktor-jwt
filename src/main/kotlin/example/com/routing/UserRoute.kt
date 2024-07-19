package example.com.routing

import example.com.model.User
import example.com.routing.request.UserReq
import example.com.routing.response.UserRes
import example.com.service.userServ
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoute(userServ: userServ) {
	post {
		val userRequest = call.receive<UserReq>()
		val createdUser = userServ.save(
			user = userRequest.toModel()
		) ?: return@post call.respond(HttpStatusCode.BadRequest)

		call.response.header(name = "id", value = createdUser.id.toString())
		call.respond(message = HttpStatusCode.Created)
	}


	get {
		val users = userServ.findAll()
		call.respond(message = users.map(User::toResponse))
	}

	get("/{id}") {
		val id = call.parameters["id"]
			?: return@get call.respond(HttpStatusCode.BadRequest)
		val foundUser = userServ.findById(id)
			?: return@get call.respond(HttpStatusCode.NotFound)

		call.respond(message = foundUser.toResponse())
	}

}

private fun User.toResponse(): UserRes =
	UserRes(
		id = this.id,
		username = this.username,
	)

private fun UserReq.toModel(): User =
	User(
		id = UUID.randomUUID(),
		username = this.username,
		password = this.password,
	)


