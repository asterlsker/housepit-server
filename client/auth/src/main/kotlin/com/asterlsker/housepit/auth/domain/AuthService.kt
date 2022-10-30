package com.asterlsker.housepit.auth.domain

import com.asterlsker.grpc.auth.Provider
import com.asterlsker.grpc.auth.SignInRequest
import com.asterlsker.housepit.auth.domain.data.*
import com.asterlsker.housepit.auth.infrastructure.AuthClientImpl
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class AuthService() {
    lateinit var authClient: AuthClient

    init {
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build()
        authClient = AuthClientImpl(channel)
    }

    fun signIn(request: TokenProviderData): TokenData {
        val signInRequest = SignInRequest.newBuilder()
            .setProvider(Provider.valueOf(request.provider.name))
            .setIdToken(request.idToken)
            .build()
        val response: TokenData
        runBlocking {
            val signInResponse = authClient.signIn(signInRequest)
            response = TokenData(signInResponse.accessToken, signInResponse.refreshToken)
        }
        return response
    }

    fun link(request: TokenProviderData) {
        TODO("Not yet implemented")
    }

    fun signOut(request: TokenAccessData) {
        TODO("Not yet implemented")
    }

    fun decode(request: TokenAccessData): MemberData {
        TODO("Not yet implemented")
    }

    fun refresh(request: TokenRefreshData): TokenAccessData {
        TODO("Not yet implemented")
    }
}