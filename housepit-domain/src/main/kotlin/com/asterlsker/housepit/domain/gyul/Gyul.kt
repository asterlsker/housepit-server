package com.asterlsker.housepit.domain.gyul

data class Gyul(
    val blocks: List<Block>,
    val timestamp: Long,
    var version: String
)
