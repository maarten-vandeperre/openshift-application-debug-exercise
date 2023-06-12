package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingRecordRepository
import org.bson.Document
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class MongoDbMovieTrackingRecordRepository(
    private val collection: MongoCollection<Document>
) : MovieTrackingRecordRepository {

    override fun save(movieTrackingRecord: MovieTrackingRecordRepository.DbMovieTrackingRecord): String {
        if (exists(movieTrackingRecord.ref)) {
            collection.updateOne(
                Filters.eq("ref", movieTrackingRecord.ref.toString()),
                Document()
                    .append("ref", movieTrackingRecord.ref.toString())
                    .append("person", movieTrackingRecord.person.toString())
                    .append("movie", movieTrackingRecord.movie.toString())
                    .append("action", movieTrackingRecord.action.toString())
            )
        } else {
            collection.insertOne(
                Document()
                    .append("ref", movieTrackingRecord.ref.toString())
                    .append("person", movieTrackingRecord.person.toString())
                    .append("movie", movieTrackingRecord.movie.toString())
                    .append("action", movieTrackingRecord.action.toString())
            )
        }
        return movieTrackingRecord.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        return collection.find(Filters.eq("ref", ref.toString())).count() > 0
    }

    override fun delete(ref: UUID) {
        collection.deleteOne(Filters.eq("ref", ref.toString()))
    }

    override fun get(ref: UUID): MovieTrackingRecordRepository.DbMovieTrackingRecord? {
        val data = collection.find(Filters.eq("ref", ref.toString())).first()
        return data?.let {
            MovieTrackingRecordRepository.DbMovieTrackingRecord(
                ref = UUID.fromString(it.getString("ref")),
                person = UUID.fromString(it.getString("person")),
                movie = UUID.fromString(it.getString("movie")),
                action = UUID.fromString(it.getString("action")),
            )
        }
    }

    override fun search(): List<MovieTrackingRecordRepository.DbMovieTrackingRecord> {
        return collection.find().toList().map {
            MovieTrackingRecordRepository.DbMovieTrackingRecord(
                ref = UUID.fromString(it.getString("ref")),
                person = UUID.fromString(it.getString("person")),
                movie = UUID.fromString(it.getString("movie")),
                action = UUID.fromString(it.getString("action")),
            )
        }
    }

    companion object {
        private val db: MutableMap<UUID, MovieTrackingRecordRepository.DbMovieTrackingRecord> = ConcurrentHashMap<UUID, MovieTrackingRecordRepository.DbMovieTrackingRecord>()
    }
}