package example.com.routing.response

import example.com.utils.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
class UserRes(
	@Serializable(with = UUIDSerializer::class)
	val id: UUID,
	val username: String,
) {
}