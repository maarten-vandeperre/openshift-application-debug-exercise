package com.redhat.demo.core.domain.v1

data class Movie(
    val name: String,
    val categories: List<MovieCategory>,
    val actors: List<PersonRef>,
)

data class MovieCategory(
    val name: String
)