package com.redhat.demo.core.usecases.v1.movietrackingrecord

import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingRecordRepository
import com.redhat.demo.core.usecases.v1.movietrackingrecord.DeleteMovieTrackingRecordUseCase.*
import java.util.*

interface DeleteMovieTrackingRecordUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?
    )

    class Response

    class ValidationException(message: String) : Exception(message)
}

class DefaultDeleteMovieTrackingRecordUseCase(
    private val movieTrackingRecordRepository: MovieTrackingRecordRepository
) : DeleteMovieTrackingRecordUseCase {
    override fun execute(requestData: Request): Response {
        if (requestData.ref == null) {
            throw ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw ValidationException("Ref is not a UUID format")
            }
        }
        movieTrackingRecordRepository.delete(UUID.fromString(requestData.ref))
        return Response()
    }

}