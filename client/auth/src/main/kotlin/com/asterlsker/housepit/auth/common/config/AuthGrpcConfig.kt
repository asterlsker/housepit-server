package com.asterlsker.housepit.auth.common.config

import com.asterlsker.housepit.auth.common.properties.AuthProperties
import com.asterlsker.housepit.auth.infrastructure.AuthClientImpl
import com.asterlsker.housepit.grpc.AuthServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthGrpcConfig {
    @Autowired
    lateinit var authProperties: AuthProperties


    @Bean
    fun authClientImpl(): AuthClientImpl {
        val channel = ManagedChannelBuilder
            .forAddress(authProperties.grpc.url, authProperties.grpc.port)
            .usePlaintext()
            .build()
        val stub = AuthServiceGrpcKt.AuthServiceCoroutineStub(channel)
        return AuthClientImpl(stub = stub)
    }
}