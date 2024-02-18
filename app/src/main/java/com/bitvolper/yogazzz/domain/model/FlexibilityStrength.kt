package com.bitvolper.yogazzz.domain.model

data class FlexibilityStrength(
    val data: List<Data?>? = null
) {
    data class Data(
        val title: String? = null,
        val image: String? = null,
        val duration: String? = null,
        val level: String? = null
    )
}