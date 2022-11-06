package com.asterlsker.housepit.auth.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "auth")
class AuthProperties(
    val grpc: Grpc
) {

    @ConstructorBinding
    data class Grpc(
        val url: String,
        val port: Int
    )
}