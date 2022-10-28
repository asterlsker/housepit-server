package com.asterlsker.housepit.auth.domain.data

data class TokenData(
    val accessToken: String,
    val refreshToken: String
)
