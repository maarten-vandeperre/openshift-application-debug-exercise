package com.redhat.demo.core.usecases.v1.movie

import com.redhat.demo.core.usecases.repositories.v1.MovieCategoryRepository
import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.utils.UuidUtils
import com.redhat.demo.core.usecases.v1.movie.SaveMovieUseCase.ValidationException
import java.util.*

interface SaveMovieUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String? = null,
        val name: String?,
        val categories: List<String>,
        val actors: List<String>
    )

    data class Response(
        val ref: String
    )

    class ValidationException(message: String) : Exception(message)
    class NotFoundException(message: String) : Exception(message)
}

class DefaultSaveMovieUseCase(
    private val movieRepository: MovieRepository,
    private val movieCategoryRepository: MovieCategoryRepository,
    private val personRepository: PersonRepository
) : SaveMovieUseCase {
    override fun execute(requestData: SaveMovieUseCase.Request): SaveMovieUseCase.Response {
        if (requestData.ref != null && !UuidUtils.isValidUuid(requestData.ref)) {
            throw ValidationException("Ref '${requestData.ref}' is not a valid UUID")
        }
        if (requestData.name == null) {
            throw ValidationException("Name should not be null")
        }
        requestData.categories.forEach {
            if (!UuidUtils.isValidUuid(it)) {
                throw ValidationException("Category '$it' is not a valid UUID")
            }
        }
        if (movieCategoryRepository.allExist(requestData.categories.map { UUID.fromString(it) })) {
            throw ValidationException("Not all categories exist: ${requestData.categories.joinToString(",")}")
        }
        requestData.actors.forEach {
            if (!UuidUtils.isValidUuid(it)) {
                throw ValidationException("Actor id '$it' is not a valid UUID")
            }
            if (!personRepository.exists(UUID.fromString(it))) {
                throw ValidationException("Actor doesn't exist: $it")
            }
        }
        return SaveMovieUseCase.Response(
            movieRepository.save(
                MovieRepository.DbMovie(
                    ref = requestData.ref?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
                    name = requestData.name,
                    categories = requestData.categories.map { UUID.fromString(it) },
                    actors = requestData.actors.map { UUID.fromString(it) }
                )
            )
        )
    }

}