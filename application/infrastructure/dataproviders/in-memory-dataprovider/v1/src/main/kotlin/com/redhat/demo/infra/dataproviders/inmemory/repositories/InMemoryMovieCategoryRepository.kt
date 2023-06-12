package com.redhat.demo.infra.dataproviders.inmemory.repositories

import com.redhat.demo.core.domain.v1.MovieCategoryRef
import com.redhat.demo.core.domain.v1.ReadMovieCategory
import com.redhat.demo.core.usecases.repositories.v1.MovieCategoryRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryMovieCategoryRepository : MovieCategoryRepository {
    override fun allExist(refs: List<MovieCategoryRef>): Boolean {
        val refValues = refs.map { it.toString() }.toSet()
        return db.values.any { refValues.contains(it.ref) }
    }

    companion object {
        private val db: MutableMap<UUID, ReadMovieCategory> = ConcurrentHashMap<UUID, ReadMovieCategory>()
    }
}