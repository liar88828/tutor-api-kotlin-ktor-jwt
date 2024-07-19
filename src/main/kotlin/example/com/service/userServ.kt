package example.com.service

import example.com.model.User
import example.com.repository.UserRepo
import java.util.*

class userServ(
	private val userRepo: UserRepo
) {
	fun findAll(): List<User> = userRepo.findAll()
	fun findById(id: String): User? = userRepo.findById(id = UUID.fromString(id))
	fun findByUsername(username: String): User? = userRepo.findByUsername(username)
	fun save(user: User): User? {
		val userFound = this.findByUsername(username = user.username)
		return if (userFound == null) {
			userRepo.save(user)
			user
		} else
			null
	}
}