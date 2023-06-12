package com.redhat.demo.core.domain.v1

data class ReadMovie(
    val ref: String,
    val name: String,
    val categories: List<String>,
    val actors: List<String>
)