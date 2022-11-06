package com.asterlsker.housepit.auth.domain.data

data class TokenData(
    var accessToken: String,
    val refreshToken: String
)