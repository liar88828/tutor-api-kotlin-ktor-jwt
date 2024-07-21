package example.com.plugins

import example.com.service.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity(
	jwtService: JwtService,
) {
	// Please read the jwt property from the config file if you are using EngineMain
	val jwtAudience = "jwt-audience"
	authentication {
		jwt {
			realm = jwtService.realm
			verifier(jwtService.jwtVerifier)
			validate { credential ->
				jwtService.customValidator(credential)
				if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload)
				else null
			}
		}
		jwt("another-auth") {
			realm = jwtService.realm
			verifier(jwtService.jwtVerifier)
			validate { credential ->
				jwtService.customValidator(credential)
				if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload)
				else null
			}
		}
	}
}
