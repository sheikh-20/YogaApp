package com.bitvolper.yogazzz.domain.model

data class SerenityData(
    val data: List<Data?>? = null
) {
    data class Data(
        val id: String? = null,
        val title: String? = null,
        val description: String? = null,
        val duration: String? = null,
        val category: String? = null,
        val image: String? = null,
        val level: String? = null,
        val moments: String? = null,
        val kcal: String? = null,
        val language: String? = null,
        val type: String? = null,
        val pose: List<Pose?>? = null
    ) {
        data class Pose(
            val title: String? = null,
            val duration: String? = null,
            val image: String? = null,
            val file: String? = null
        )
    }
}