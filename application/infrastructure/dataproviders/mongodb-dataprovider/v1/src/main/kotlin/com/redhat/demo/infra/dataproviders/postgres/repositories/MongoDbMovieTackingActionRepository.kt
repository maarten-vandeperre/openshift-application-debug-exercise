package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.`in`
import com.redhat.demo.core.domain.v1.MovieTrackingActionRef
import com.redhat.demo.core.domain.v1.ReadMovieTrackingAction
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingActionRepository
import org.bson.Document


class MongoDbMovieTrackingActionRepository(
    private val collection: MongoCollection<Document>
) : MovieTrackingActionRepository {

    fun search(): List<ReadMovieTrackingAction> {
        return collection.find().toList().map {
            ReadMovieTrackingAction(
                ref = it.getString("ref"),
                name = it.getString("name")
            )
        }
    }

    override fun allExist(refs: List<MovieTrackingActionRef>): Boolean {
        return collection.find(`in`("ref", refs.map { it.toString() })).toList().size == refs.size
    }
}