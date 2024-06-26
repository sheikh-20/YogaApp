package com.bitvolper.yogazzz.domain.model

data class Meditation(
    val data: List<Data?>? = null
) {
    data class Data(
        val id: String? = null,
        val title: String? = null,
        val image: String? = null,
        val duration: String? = null,
        val category: String? = null,
        val description: String? = null,
        val file: String? = null,
        val type: String? = null
    )
}