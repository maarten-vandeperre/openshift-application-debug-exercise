package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.core.repositories.JdbcTemplate
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class PostgresPersonRepository(
    private val jdbcTemplate: JdbcTemplate
) : PersonRepository {

    override fun save(person: PersonRepository.DbPerson): String {
        if (exists(person.ref)) {
            jdbcTemplate.execute(
                """
                update people 
                set ref = ?, 
                first_name = ?, 
                last_name = ?, 
                birth_date = ?
                where ref = ?;
            """.trimIndent(),
                listOf(
                    person.ref.toString(),
                    person.firstName,
                    person.lastName,
                    person.birthDate,
                    person.ref.toString()
                )
            )
        } else {
            jdbcTemplate.execute(
                """
                INSERT INTO people (ref, first_name, last_name, birth_date)
                VALUES (?, ?, ?, ?);
            """.trimIndent(),
                listOf(
                    person.ref.toString(),
                    person.firstName,
                    person.lastName,
                    person.birthDate
                )
            )
        }
        return person.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        val result = jdbcTemplate.query(
            "select count(*) as c from people where ref = ?",
            listOf(ref.toString())
        ) {
            1 == it.getInt("c")
        }
        return result ?: false
    }

    override fun delete(ref: UUID) {
        jdbcTemplate.execute(
            "delete from people where ref = ?",
            listOf(ref.toString())
        )
    }

    override fun get(ref: UUID): PersonRepository.DbPerson? {
        return jdbcTemplate.query(
            """
                select *
                from people p
                where p.ref = ?
            """.trimIndent(),
            listOf(ref.toString())
        ) {
            PersonRepository.DbPerson(
                ref = UUID.fromString(it.getString("ref")),
                firstName = it.getString("first_name"),
                lastName = it.getString("last_name"),
                birthDate = it.getString("birth_date"),
            )
        }
    }

    override fun search(): List<PersonRepository.DbPerson> {
        return jdbcTemplate.queryForList(
            """
                select *
                from people p
            """.trimIndent()
        ) {
            PersonRepository.DbPerson(
                ref = UUID.fromString(it.getString("ref")),
                firstName = it.getString("first_name"),
                lastName = it.getString("last_name"),
                birthDate = it.getString("birth_date"),
            )
        }
    }

    companion object {
        private val db: MutableMap<UUID, PersonRepository.DbPerson> = ConcurrentHashMap<UUID, PersonRepository.DbPerson>()
    }
}