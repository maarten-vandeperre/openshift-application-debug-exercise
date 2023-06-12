package com.redhat.demo.core.usecases.repositories.v1

import com.redhat.demo.core.domain.v1.MovieRef
import com.redhat.demo.core.domain.v1.MovieTrackingActionRef
import com.redhat.demo.core.domain.v1.MovieTrackingRecordRef
import com.redhat.demo.core.domain.v1.PersonRef
import java.util.*

interface MovieTrackingRecordRepository {

    fun save(movieTrackingRecord: DbMovieTrackingRecord): String
    fun exists(ref: UUID): Boolean
    fun delete(ref: UUID)
    fun get(ref: UUID): DbMovieTrackingRecord?
    fun search(): List<DbMovieTrackingRecord>

    data class DbMovieTrackingRecord(
        val ref: MovieTrackingRecordRef,
        val movie: MovieRef,
        val person: PersonRef,
        val action: MovieTrackingActionRef
    )
}