package com.redhat.demo.core.usecases.v1.person

import com.redhat.demo.core.domain.v1.ReadPerson
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import java.util.*

interface SearchPeopleUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    class Request

    data class Response(
        val people: List<ReadPerson>
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultSearchPeopleUseCase(
    private val personRepository: PersonRepository
) : SearchMoviesUseCase {
    override fun execute(requestData: SearchMoviesUseCase.Request): SearchMoviesUseCase.Response {
        return SearchMoviesUseCase.Response(
            personRepository.search().map {
                ReadPerson(
                    ref = it.ref.toString(),
                    firstName = it.firstName,
                    lastName = it.lastName,
                    birthDate = it.birthDate
                )
            }
        )
    }

}