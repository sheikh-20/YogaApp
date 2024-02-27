package com.bitvolper.yogazzz.domain.model

data class StressRelief(
    val data: List<Data?>? = null
) {
    data class Data(
        val category: String? = null,
        val title: String? = null,
        val image: String? = null,
        val duration: String? = null,
        val level: String? = null
    )
}