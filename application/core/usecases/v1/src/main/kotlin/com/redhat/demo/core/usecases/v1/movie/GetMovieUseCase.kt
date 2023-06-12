package com.redhat.demo.core.usecases.v1.movie

import com.redhat.demo.core.domain.v1.ReadMovie
import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import java.util.*

interface GetMovieUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?
    )

    data class Response(
        val movie: ReadMovie
    )

    class ValidationException(message: String) : Exception(message)
    class NotFoundException(message: String) : Exception(message)
}

class DefaultGetMovieUseCase(
    private val movieRepository: MovieRepository
) : GetMovieUseCase {
    override fun execute(requestData: GetMovieUseCase.Request): GetMovieUseCase.Response {
        if (requestData.ref == null) {
            throw GetMovieUseCase.ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw GetMovieUseCase.ValidationException("Ref is not a UUID format")
            }
        }
        if (!movieRepository.exists(UUID.fromString(requestData.ref))) {
            throw GetMovieUseCase.NotFoundException("No movie with ref is found")
        }
        val dbMovie = movieRepository.get(UUID.fromString(requestData.ref))!!
        return GetMovieUseCase.Response(
            ReadMovie(
                ref = dbMovie.ref.toString(),
                name = dbMovie.name,
                actors = dbMovie.actors.map { it.toString() },
                categories = dbMovie.categories.map { it.toString() }
            )
        )
    }

}