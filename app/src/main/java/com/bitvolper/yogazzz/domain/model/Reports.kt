package com.bitvolper.yogazzz.domain.model

data class Reports(
    val data: List<Data?>? = null
) {
    data class Data(
        val id: String? = null,
        val duration: String? = null,
        val kcal: String? = null
    )
}