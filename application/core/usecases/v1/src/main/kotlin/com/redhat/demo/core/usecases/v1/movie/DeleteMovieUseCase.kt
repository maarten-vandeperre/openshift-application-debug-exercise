package com.redhat.demo.core.usecases.v1.movie

import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import java.util.*

interface DeleteMovieUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?
    )

    class Response

    class ValidationException(message: String) : Exception(message)
}

class DefaultDeleteMovieUseCase(
    private val movieRepository: MovieRepository
) : DeleteMovieUseCase {
    override fun execute(requestData: DeleteMovieUseCase.Request): DeleteMovieUseCase.Response {
        if (requestData.ref == null) {
            throw DeleteMovieUseCase.ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw DeleteMovieUseCase.ValidationException("Ref is not a UUID format")
            }
        }
        movieRepository.delete(UUID.fromString(requestData.ref))
        return DeleteMovieUseCase.Response()
    }

}