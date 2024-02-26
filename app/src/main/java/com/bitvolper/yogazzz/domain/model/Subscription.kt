package com.bitvolper.yogazzz.domain.model

data class Subscription(
    val data: List<Data?>? = null
)
{
    data class Data(
        val duration: String? = null,
        val title: String? = null,
        val price: String? = null,
        val description: List<String?>? = null,
        val validity: String? = null
    )
}