package com.bitvolper.yogazzz.domain.model

data class YogaCategory(
    val data: List<Data?>? = null
) {
    data class Data(val image: String? = null, val title: String? = null)
}