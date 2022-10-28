package com.asterlsker.housepit.auth.domain

import com.asterlsker.housepit.auth.domain.data.*

interface IAuthService {
    fun signIn(request: TokenProviderData): TokenData
    fun link(request: TokenProviderData)
    fun signOut(request: TokenAccessData)
    fun decode(request: TokenAccessData): MemberData
    fun refresh(request: TokenRefreshData): TokenAccessData
}