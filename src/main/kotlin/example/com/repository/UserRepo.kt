package example.com.repository

import example.com.model.User
import java.util.UUID

class UserRepo {
	private val users = mutableListOf<User>()

	fun findAll(): List<User> = users
	fun findById(id: UUID): User? = users.firstOrNull { it.id == id }
	fun findByUsername(username: String): User? = users.firstOrNull { it.username == username }
	fun save(user: User): Boolean = users.add(user)
	fun delete(id: UUID): Boolean = users.removeIf { it.id == id }
	fun edit(user: User): Boolean = users.any { it.id == user.id }

}