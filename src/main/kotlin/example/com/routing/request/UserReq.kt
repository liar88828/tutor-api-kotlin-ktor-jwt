package example.com.routing.request

import kotlinx.serialization.Serializable

@Serializable
data class UserReq(
	val username: String,
	val password: String
) {

}