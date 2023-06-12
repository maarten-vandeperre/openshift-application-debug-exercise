package com.redhat.demo.core.usecases.repositories.v1

import com.redhat.demo.core.domain.v1.MovieCategoryRef
import com.redhat.demo.core.domain.v1.MovieRef
import com.redhat.demo.core.domain.v1.MovieTrackingActionRef
import com.redhat.demo.core.domain.v1.PersonRef

interface PersonMovieHistoryRepository {
    fun search(): List<DBPersonMovieHistoryRecord>

    data class DBPersonMovieHistoryRecord(
        val personRef: PersonRef,
        val firstName: String,
        val lastName: String,
        val birthDate: String?,
        val history: List<DBMovieTrackingRecord>
    )

    data class DBMovieTrackingRecord(
        val movieRef: MovieRef,
        val name: String,
        val categories: List<DBMovieCategory>,
        val actors: List<DBPerson>,
        val action: DBMovieTrackingAction
    )

    data class DBMovieTrackingAction(
        val movieTrackingActionRef: MovieTrackingActionRef,
        val name: String
    )

    data class DBMovieCategory(
        val movieCategoryRef: MovieCategoryRef,
        val name: String
    )

    data class DBPerson(
        val personRef: PersonRef,
        val firstName: String,
        val lastName: String,
        val birthDate: String?
    )
}