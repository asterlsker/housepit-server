package com.asterlsker.housepit.auth.infrastructure

import com.asterlsker.grpc.auth.*
import com.asterlsker.housepit.auth.domain.AuthClient
import io.grpc.ManagedChannel


class AuthClientImpl(
    channel: ManagedChannel
): AuthClient {
    private val stub = AuthServiceGrpcKt.AuthServiceCoroutineStub(channel)

    override suspend fun signIn(request: SignInRequest): SignInResponse {
        return stub.signIn(request)
    }

    override suspend fun link(request: LinkRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(request: SignOutRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun decode(request: DecodeRequest): DecodeResponse {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(request: RefreshRequest): RefreshResponse {
        TODO("Not yet implemented")
    }
}