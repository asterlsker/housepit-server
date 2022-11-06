package com.asterlsker.housepit.auth.domain

import com.asterlsker.housepit.auth.common.properties.AuthProperties
import com.asterlsker.housepit.auth.domain.data.MemberData
import com.asterlsker.housepit.auth.domain.data.TokenData
import com.asterlsker.housepit.auth.domain.data.TokenProviderData
import com.asterlsker.housepit.auth.infrastructure.AuthClientImpl
import com.asterlsker.housepit.grpc.*
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class AuthService(
    val authClient: AuthClientImpl
) {

    fun signIn(request: TokenProviderData): TokenData = runBlocking {
        val signInRequest = signInRequest {
            this.provider = Provider.valueOf(request.provider.name)
            this.idToken = request.idToken
        }
        val signInResponse = authClient.signIn(signInRequest)
        TokenData(signInResponse.accessToken, signInResponse.refreshToken)
    }


    fun link(request: TokenProviderData) = runBlocking {
        val linkRequest = linkRequest {
            this.accessToken = request.accessToken.orEmpty()
            this.provider = Provider.valueOf(request.provider.name)
            this.idToken = request.idToken;
        }
        authClient.link(linkRequest)
    }


    fun signOut(accessToken: String) = runBlocking {
        val signOutRequest = signOutRequest {
            this.accessToken = accessToken
        }
        authClient.signOut(signOutRequest)
    }


    fun decode(accessToken: String): MemberData = runBlocking {
        val decodeRequest = decodeRequest {
            this.accessToken = accessToken
        }
        val decodeResponse = authClient.decode(decodeRequest)
        MemberData(decodeResponse.memberId)
    }


    fun refresh(refreshToken: String): TokenData = runBlocking {
        val refreshRequest = refreshRequest {
            this.refreshToken = refreshToken
        }
        val refreshResponse = authClient.refresh(refreshRequest)
        TokenData(refreshResponse.accessToken, refreshResponse.refreshToken)
    }
}