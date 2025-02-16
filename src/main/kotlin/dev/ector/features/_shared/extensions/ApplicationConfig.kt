import io.ktor.server.application.*
import io.ktor.server.config.*


val ApplicationConfig.address
    get() = "${this.host}:${this.port}"