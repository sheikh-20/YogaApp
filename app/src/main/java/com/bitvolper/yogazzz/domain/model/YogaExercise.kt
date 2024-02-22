package com.bitvolper.yogazzz.domain.model

import com.google.gson.annotations.SerializedName

class YogaExercise(
    val data: List<Data?>? = null
) {
    data class Data(
        @SerializedName("_id")
        val id: Int? = null,
        val yogaTitle: String? = null,
        val backdropImage: String? = null,
        val yogaDescription: String? = null,
        val bookmark: Boolean? = null)
}