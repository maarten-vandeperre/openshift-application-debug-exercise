package com.redhat.demo.infra.dataproviders.inmemory.repositories

import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryMovieRepository : MovieRepository {
    override fun save(movie: MovieRepository.DbMovie): String {
        db[movie.ref] = movie
        return movie.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        return db.contains(ref)
    }

    override fun delete(ref: UUID) {
        db.remove(ref)
    }

    override fun get(ref: UUID): MovieRepository.DbMovie {
        return db.get(ref)!!
    }

    override fun search(): List<MovieRepository.DbMovie> {
        return db.values.toList()
    }

    companion object {
        private val db: MutableMap<UUID, MovieRepository.DbMovie> = ConcurrentHashMap<UUID, MovieRepository.DbMovie>()
    }
}