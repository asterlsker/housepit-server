package com.asterlsker.housepit.auth.domain

import com.asterlsker.housepit.auth.domain.data.*
import org.springframework.stereotype.Service

@Service
class AuthService: IAuthService {
    override fun signIn(request: TokenProviderData): TokenData {
        TODO("Not yet implemented")
    }

    override fun link(request: TokenProviderData) {
        TODO("Not yet implemented")
    }

    override fun signOut(request: TokenAccessData) {
        TODO("Not yet implemented")
    }

    override fun decode(request: TokenAccessData): MemberData {
        TODO("Not yet implemented")
    }

    override fun refresh(request: TokenRefreshData): TokenAccessData {
        TODO("Not yet implemented")
    }
}