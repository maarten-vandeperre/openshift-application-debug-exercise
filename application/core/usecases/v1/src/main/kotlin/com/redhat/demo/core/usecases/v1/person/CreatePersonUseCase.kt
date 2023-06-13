package com.redhat.demo.core.usecases.v1.person

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.v1.person.CreatePersonUseCase.ValidationException
import java.util.*

interface CreatePersonUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val firstName: String?,
        val lastName: String?,
        val birthDate: String?,
    )

    data class Response(
        val ref: String
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultCreatePersonUseCase(
    private val personRepository: PersonRepository
) : CreatePersonUseCase {
    override fun execute(requestData: CreatePersonUseCase.Request): CreatePersonUseCase.Response {
        if (requestData.firstName == null) {
            throw ValidationException("First name should not be null")
        }
        if (requestData.lastName == null) {
            throw ValidationException("Last name should not be null")
        }
        if(requestData.birthDate != null && !requestData.birthDate.contains("-")){
            throw ValidationException("Birth date format is not valid")
        }
        return CreatePersonUseCase.Response(
            personRepository.save(
                PersonRepository.DbPerson(
                    ref = UUID.randomUUID(),
                    firstName = requestData.firstName,
                    lastName = requestData.lastName,
                    birthDate = requestData.birthDate,
                )
            )
        )
    }

}