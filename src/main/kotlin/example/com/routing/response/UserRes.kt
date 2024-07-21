package example.com.routing.response

import example.com.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class UserRes(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	val username: String,
)