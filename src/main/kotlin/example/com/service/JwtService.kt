package example.com.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import example.com.routing.request.LoginRequest
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import java.util.*

class JwtService(
	private val application: Application,
	private val userService: UserService
) {
	private val secret = getConfigProperties("jwt.secret")
	private val issuer = getConfigProperties("jwt.issuer")
	private val audience = getConfigProperties("jwt.audience")

	val realm = getConfigProperties("jwt.realm")
	val jwtVerifier: JWTVerifier = JWT
		.require(Algorithm.HMAC256(secret))
		.withAudience(audience)
		.withIssuer(issuer).build()

	fun createJwtToken(loginRequest: LoginRequest): String? {
		val foundUser = userService.findByUsername(loginRequest.username)
		return if (foundUser != null && foundUser.password == loginRequest.password) {
			JWT.create().withAudience(audience).withIssuer(issuer).withClaim("username", foundUser.username)
				.withExpiresAt(Date(System.currentTimeMillis() + 3_600_000))
				.sign(Algorithm.HMAC256(secret))
		} else null
	}

	fun customValidator(credential: JWTCredential): JWTPrincipal? {
		val username = extractUsername(credential)
		val foundUser = username?.let(userService::findByUsername)
		return foundUser?.let { user ->
			if (audienceMatch(credential)) JWTPrincipal(credential.payload)
			else null
		}
	}

	private fun audienceMatch(credential: JWTCredential): Boolean =
		credential.payload.audience.contains(audience)

	private fun extractUsername(credential: JWTCredential): String? {
		return credential.payload.getClaim("username").asString()
	}

	private fun getConfigProperties(path: String) =
		application.environment.config.property(path).getString()

}