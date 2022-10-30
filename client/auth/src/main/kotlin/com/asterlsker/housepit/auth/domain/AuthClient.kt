package com.asterlsker.housepit.auth.domain

import com.asterlsker.grpc.auth.DecodeRequest
import com.asterlsker.grpc.auth.DecodeResponse
import com.asterlsker.grpc.auth.LinkRequest
import com.asterlsker.grpc.auth.RefreshRequest
import com.asterlsker.grpc.auth.RefreshResponse
import com.asterlsker.grpc.auth.SignInRequest
import com.asterlsker.grpc.auth.SignInResponse
import com.asterlsker.grpc.auth.SignOutRequest

interface AuthClient {
    suspend fun signIn(request: SignInRequest): SignInResponse
    suspend fun link(request: LinkRequest)
    suspend fun signOut(request: SignOutRequest)
    suspend fun decode(request: DecodeRequest): DecodeResponse
    suspend fun refresh(request: RefreshRequest): RefreshResponse
}