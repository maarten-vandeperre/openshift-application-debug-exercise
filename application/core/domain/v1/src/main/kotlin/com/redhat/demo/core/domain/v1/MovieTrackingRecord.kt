package com.redhat.demo.core.domain.v1

data class MovieTrackingRecord(
    val movie: MovieRef,
    val person: PersonRef,
    val action: MovieTrackingActionRef
)

data class MovieTrackingAction(
    val name: String
)