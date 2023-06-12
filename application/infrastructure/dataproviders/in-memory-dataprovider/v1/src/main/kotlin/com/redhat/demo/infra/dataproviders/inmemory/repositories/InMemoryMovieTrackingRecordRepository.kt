package com.redhat.demo.infra.dataproviders.inmemory.repositories

import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingRecordRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryMovieTrackingRecordRepository : MovieTrackingRecordRepository {
    override fun save(movieTrackingRecord: MovieTrackingRecordRepository.DbMovieTrackingRecord): String {
        db[movieTrackingRecord.ref] = movieTrackingRecord
        return movieTrackingRecord.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        return db.contains(ref)
    }

    override fun delete(ref: UUID) {
        db.remove(ref)
    }

    override fun get(ref: UUID): MovieTrackingRecordRepository.DbMovieTrackingRecord {
        return db.get(ref)!!
    }

    override fun search(): List<MovieTrackingRecordRepository.DbMovieTrackingRecord> {
        return db.values.toList()
    }

    companion object {
        private val db: MutableMap<UUID, MovieTrackingRecordRepository.DbMovieTrackingRecord> = ConcurrentHashMap<UUID, MovieTrackingRecordRepository.DbMovieTrackingRecord>()
    }
}