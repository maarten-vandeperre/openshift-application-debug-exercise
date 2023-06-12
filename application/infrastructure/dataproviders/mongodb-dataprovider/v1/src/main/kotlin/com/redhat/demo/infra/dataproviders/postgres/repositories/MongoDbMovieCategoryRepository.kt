package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.`in`
import com.redhat.demo.core.domain.v1.MovieCategoryRef
import com.redhat.demo.core.domain.v1.ReadMovieCategory
import com.redhat.demo.core.usecases.repositories.v1.MovieCategoryRepository
import org.bson.Document


class MongoDbMovieCategoryRepository(
    private val collection: MongoCollection<Document>
) : MovieCategoryRepository {

    fun search(): List<ReadMovieCategory> {
        return collection.find().toList().map {
            ReadMovieCategory(
                ref = it.getString("ref"),
                name = it.getString("name")
            )
        }
    }

    override fun allExist(refs: List<MovieCategoryRef>): Boolean {
        return collection.find(`in`("ref", refs.map { it.toString() })).toList().size == refs.size
    }
}