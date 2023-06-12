package com.redhat.demo.configuration.microservice.person.config

import com.redhat.demo.configuration.microservice.person.repositories.WithOutboxPersonRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.core.repositories.JdbcTemplate
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryPersonRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresJdbcTemplate
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresOutboxRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresPersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.enterprise.inject.Produces
import org.eclipse.microprofile.config.inject.ConfigProperty


@ApplicationScoped
class RepositoryConfig(
    @ConfigProperty(name = "db.type") dbType: String,
    @ConfigProperty(name = "db.connection_string", defaultValue = "not-set") connectionUrl: String,
    @ConfigProperty(name = "db.user", defaultValue = "not-set") user: String,
    @ConfigProperty(name = "db.password", defaultValue = "not-set") password: String?
) {
    private val postgresJdbcTemplate: JdbcTemplate?
    private val databaseType: DatabaseType

    init {
        this.databaseType = when (dbType) {
            "IN_MEMORY" -> DatabaseType.IN_MEMORY
            "PHYSICAL" -> DatabaseType.PHYSICAL
            else -> throw IllegalStateException("$dbType is not yet supported")
        }
        if (databaseType == DatabaseType.PHYSICAL) {
            this.postgresJdbcTemplate = PostgresJdbcTemplate(connectionUrl!!, user!!, password!!)
        } else {
            this.postgresJdbcTemplate = null
        }
    }

    @Produces
    @Default
    fun personRepository(): PersonRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryPersonRepository()
            DatabaseType.PHYSICAL -> WithOutboxPersonRepository(
                PostgresPersonRepository(postgresJdbcTemplate!!),
                PostgresOutboxRepository(postgresJdbcTemplate, "people_changed")
            )
        }
    }

    enum class DatabaseType {
        IN_MEMORY, PHYSICAL
    }
}