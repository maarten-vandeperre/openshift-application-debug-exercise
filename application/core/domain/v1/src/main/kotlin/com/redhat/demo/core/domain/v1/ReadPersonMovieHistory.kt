package com.redhat.demo.core.domain.v1

data class ReadPersonMovieHistory(
    val personRef: String,
    val personName: String,
    val history: List<ReadMovieTrackRecord>
)

data class ReadMovieTrackRecord(
    val movieRef: String,
    val movieName: String,
    val actors: List<ReadActor>,
    val action: ReadMovieTrackingAction
)

data class ReadMovieTrackingAction(
    val ref: String,
    val name: String
)

data class ReadActor(
    val personRef: String,
    val name: String
)