package com.asterlsker.housepit.auth.domain.interfaces

import com.asterlsker.housepit.auth.domain.dto.MemberDto
import com.asterlsker.housepit.auth.domain.dto.ProviderTokenDto
import com.asterlsker.housepit.auth.domain.dto.ServiceTokenDto

interface IAuthAction {
    fun signIn(providerToken: ProviderTokenDto): ServiceTokenDto
    fun signOut(serviceToken: ServiceTokenDto)
    fun decode(serviceToken: ServiceTokenDto): MemberDto
    fun refresh(serviceToken: ServiceTokenDto): ServiceTokenDto
}