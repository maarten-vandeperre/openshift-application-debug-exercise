package com.redhat.demo.configuration.microservice.movie.config

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.redhat.demo.core.usecases.repositories.v1.MovieCategoryRepository
import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryMovieCategoryRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryMovieRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryPersonRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbMovieCategoryRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbMovieRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbPersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.enterprise.inject.Produces
import org.eclipse.microprofile.config.inject.ConfigProperty


@ApplicationScoped
class RepositoryConfig(
    @ConfigProperty(name = "db.type") dbType: String,
    @ConfigProperty(name = "db.mongo.connection_string", defaultValue = "not-set") mongoConnectionUrl: String,
    @ConfigProperty(name = "channel.address_changed.url", defaultValue = "not-set") val movieChangedChannelUrl: String
) {
    private val mongoDatabase: MongoDatabase?
    private val databaseType: DatabaseType

    init {
        this.databaseType = when (dbType) {
            "IN_MEMORY" -> DatabaseType.IN_MEMORY
            "PHYSICAL" -> DatabaseType.PHYSICAL
            else -> throw IllegalStateException("$dbType is not yet supported")
        }
        if (databaseType == DatabaseType.PHYSICAL) {
//            this.mongoDatabase = MongoClients.create(mongoConnectionUrl).getDatabase("microservice-movie") FIXME no shared database instance
            this.mongoDatabase = MongoClients.create(mongoConnectionUrl).getDatabase("movie-data")
        } else {
            this.mongoDatabase = null
        }
    }

    @Produces
    @Default
    fun movieRepository(): MovieRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryMovieRepository()
//            DatabaseType.PHYSICAL -> WithBrokerUpdateMovieRepository(
//                MongoDbMovieRepository(mongoDatabase!!.getCollection("movies")),
//                movieChangedChannelUrl
//            )
            DatabaseType.PHYSICAL -> MongoDbMovieRepository(mongoDatabase!!.getCollection("movies"))
        }
    }

    @Produces
    @Default
    fun personRepository(): PersonRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryPersonRepository()
            DatabaseType.PHYSICAL -> MongoDbPersonRepository(mongoDatabase!!.getCollection("people")) //TODO should be REST call
        }
    }

    @Produces
    @Default
    fun movieCategoryRepository(): MovieCategoryRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryMovieCategoryRepository()
            DatabaseType.PHYSICAL -> MongoDbMovieCategoryRepository(mongoDatabase!!.getCollection("movie-categories"))
        }
    }

    enum class DatabaseType {
        IN_MEMORY, PHYSICAL
    }
}