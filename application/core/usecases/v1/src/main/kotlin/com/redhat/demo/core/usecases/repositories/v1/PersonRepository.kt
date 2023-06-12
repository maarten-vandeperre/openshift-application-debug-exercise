package com.redhat.demo.core.usecases.repositories.v1

import com.redhat.demo.core.domain.v1.PersonRef
import java.util.*

interface PersonRepository {

    fun save(person: DbPerson): String
    fun exists(ref: PersonRef): Boolean
    fun delete(ref: PersonRef)
    fun get(ref: PersonRef): DbPerson?
    fun search(): List<DbPerson>

    data class DbPerson(
        val ref: PersonRef,
        val firstName: String,
        val lastName: String,
        val birthDate: String?,
    )
}