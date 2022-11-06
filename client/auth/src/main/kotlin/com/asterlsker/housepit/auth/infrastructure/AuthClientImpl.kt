package com.asterlsker.housepit.auth.infrastructure

import com.asterlsker.housepit.auth.domain.AuthClient
import com.asterlsker.housepit.grpc.*


class AuthClientImpl(
    val stub: AuthServiceGrpcKt.AuthServiceCoroutineStub
) : AuthClient {

    override suspend fun signIn(request: SignInRequest): SignInResponse {
        return stub.signIn(request)
    }

    override suspend fun link(request: LinkRequest) {
        stub.link(request)
    }

    override suspend fun signOut(request: SignOutRequest) {
        stub.signOut(request)
    }

    override suspend fun decode(request: DecodeRequest): DecodeResponse {
        return stub.decode(request)
    }

    override suspend fun refresh(request: RefreshRequest): RefreshResponse {
        return stub.refresh(request)
    }
}