package com.bitvolper.yogazzz.domain.model

data class YogaData(
    val data: List<Data?>? = null
) {
    data class Data(
        val title: String? = null,
        val image: String? = null,
        val duration: String? = null,
        val level: String? = null,
        val category: String? = null
    )
}