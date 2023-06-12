package com.redhat.demo.infra.dataproviders.inmemory.repositories

import com.redhat.demo.core.domain.v1.MovieTrackingActionRef
import com.redhat.demo.core.domain.v1.ReadMovieCategory
import com.redhat.demo.core.domain.v1.ReadMovieTrackingAction
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingActionRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryMovieTrackingActionRepository : MovieTrackingActionRepository {
    override fun allExist(refs: List<MovieTrackingActionRef>): Boolean {
        val refValues = refs.map { it.toString() }.toSet()
        return db.values.any { refValues.contains(it.ref) }
    }

    companion object {
        private val db: MutableMap<UUID, ReadMovieTrackingAction> = ConcurrentHashMap<UUID, ReadMovieTrackingAction>()
    }
}