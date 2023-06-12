package com.redhat.demo.core.domain.v1

data class ReadMovieTrackingRecord(
    val ref: String,
    val movie: String,
    val person: String,
    val action: String
)