package com.bitvolper.yogazzz.domain.model

class FaqQuestion(
    val data: List<Data?>? = null
) {
    data class Data(
        val question: String? = null,
        val ans: String? = null,
        val category: Int? = null)
}