package com.asterlsker.housepit.auth.domain

import com.asterlsker.housepit.auth.domain.dto.MemberDto
import com.asterlsker.housepit.auth.domain.dto.ProviderTokenDto
import com.asterlsker.housepit.auth.domain.dto.ServiceTokenDto
import com.asterlsker.housepit.auth.domain.interfaces.IAuthAction
import org.springframework.stereotype.Service

@Service
class AuthAction: IAuthAction {
    override fun signIn(providerToken: ProviderTokenDto): ServiceTokenDto {
        TODO("Not yet implemented")
    }

    override fun signOut(serviceToken: ServiceTokenDto) {
        TODO("Not yet implemented")
    }

    override fun decode(serviceToken: ServiceTokenDto): MemberDto {
        TODO("Not yet implemented")
    }

    override fun refresh(serviceToken: ServiceTokenDto): ServiceTokenDto {
        TODO("Not yet implemented")
    }
}