package com.asterlsker.housepit.auth.domain

import com.asterlsker.housepit.grpc.*

interface AuthClient {
    suspend fun signIn(request: SignInRequest): SignInResponse
    suspend fun link(request: LinkRequest)
    suspend fun signOut(request: SignOutRequest)
    suspend fun decode(request: DecodeRequest): DecodeResponse
    suspend fun refresh(request: RefreshRequest): RefreshResponse
}