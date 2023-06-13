package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import org.bson.Document
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class MongoDbMovieRepository(
    private val collection: MongoCollection<Document>
) : MovieRepository {

    override fun save(movie: MovieRepository.DbMovie): String {
        if (exists(movie.ref)) {
            collection.updateOne(
                Filters.eq("ref", movie.ref.toString()),
                Document()
                    .append("ref", movie.ref.toString())
                    .append("name", movie.name)
                    .append("categories", movie.categories.map { it.toString() })
                    .append("actors", movie.actors.map { it.toString() })
            )
        } else {
            collection.insertOne(
                Document()
                    .append("ref", movie.ref.toString())
                    .append("name", movie.name)
                    .append("categories", movie.categories.map { it.toString() })
                    .append("actors", movie.actors.map { it.toString() })
            )
        }
        return movie.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        return collection.find(Filters.eq("ref", ref.toString())).count() > 0
    }

    override fun delete(ref: UUID) {
        collection.deleteOne(Filters.eq("ref", ref.toString()))
    }

    override fun get(ref: UUID): MovieRepository.DbMovie? {
        val data = collection.find(Filters.eq("ref", ref.toString())).first()
        return data?.let {
            MovieRepository.DbMovie(
                ref = UUID.fromString(it.getString("ref")),
                name = it.getString("movieLine1"),
                actors = it.getList("actors", String::class.java).map { UUID.fromString(it) },
                categories = it.getList("categories", String::class.java).map { UUID.fromString(it) },
            )
        }
    }

    override fun search(): List<MovieRepository.DbMovie> {
        val result = collection.find().toList().map {
            MovieRepository.DbMovie(
                ref = UUID.fromString(it.getString("ref")),
                name = it.getString("name"),
                actors = it.getList("actors", String::class.java).map { UUID.fromString(it) },
                categories = it.getList("categories", String::class.java).map { UUID.fromString(it) },
            )
        }
        println("DB: found ${result.size} movies")
        return result
    }

    companion object {
        private val db: MutableMap<UUID, MovieRepository.DbMovie> = ConcurrentHashMap<UUID, MovieRepository.DbMovie>()
    }
}