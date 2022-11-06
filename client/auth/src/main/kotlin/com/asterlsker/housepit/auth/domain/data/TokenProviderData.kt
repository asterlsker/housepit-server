package com.asterlsker.housepit.auth.domain.data

import com.asterlsker.housepit.core.enums.OAuth2Provider

data class TokenProviderData(
    val provider: OAuth2Provider,
    val idToken: String,
    val accessToken: String?
)
