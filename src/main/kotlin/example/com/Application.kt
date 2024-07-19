package example.com

import example.com.plugins.*
import example.com.repository.UserRepo
import example.com.service.userServ
import io.ktor.server.application.*

fun main(args: Array<String>) {
	io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
	val userRepository = UserRepo()
	val userServ = userServ(userRepository)
	configureSerialization()
	configureRouting(userServ)
	configureSecurity()
}
