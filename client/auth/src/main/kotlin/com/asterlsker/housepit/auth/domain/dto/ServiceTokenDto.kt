package com.asterlsker.housepit.auth.domain.dto

data class ServiceTokenDto(
    val accessToken: String?,
    val refreshToken: String?
)
