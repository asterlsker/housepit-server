package com.asterlsker.housepit.domain.gyul

data class Text(
    val fields: List<TextField>
)

data class TextField(
    val text: String,
    val color: String,
    val backgroundColor: String,
    val style: List<TextStyle>
)

enum class TextStyle {
    BOLD, ITALIC, UNDERLINE
}