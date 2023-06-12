package com.redhat.demo.core.usecases.repositories.v1

import com.redhat.demo.core.domain.v1.MovieCategoryRef
import com.redhat.demo.core.domain.v1.MovieRef
import com.redhat.demo.core.domain.v1.PersonRef
import java.util.*

interface MovieRepository {

    fun save(movie: DbMovie): String
    fun exists(ref: MovieRef): Boolean
    fun delete(ref: MovieRef)
    fun get(ref: MovieRef): DbMovie?
    fun search(): List<DbMovie>

    data class DbMovie(
        val ref: MovieRef,
        val name: String,
        val categories: List<MovieCategoryRef>,
        val actors: List<PersonRef>
    )
}