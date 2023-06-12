package com.redhat.demo.core.usecases.v1.movietrackingrecord

import com.redhat.demo.core.domain.v1.ReadMovieTrackingRecord
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingRecordRepository
import com.redhat.demo.core.usecases.v1.movietrackingrecord.GetMovieTrackingRecordUseCase.*
import java.util.*

interface GetMovieTrackingRecordUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?
    )

    data class Response(
        val movieTrackingRecord: ReadMovieTrackingRecord
    )

    class ValidationException(message: String) : Exception(message)
    class NotFoundException(message: String) : Exception(message)
}

class DefaultGetMovieTrackingRecordUseCase(
    private val movieTrackingRecordRepository: MovieTrackingRecordRepository
) : GetMovieTrackingRecordUseCase {
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
        if (!movieTrackingRecordRepository.exists(UUID.fromString(requestData.ref))) {
            throw NotFoundException("No movie tracking record with ref is found")
        }
        val dbMovie = movieTrackingRecordRepository.get(UUID.fromString(requestData.ref))!!
        return Response(
            ReadMovieTrackingRecord(
                ref = dbMovie.ref.toString(),
                person = dbMovie.person.toString(),
                movie = dbMovie.movie.toString(),
                action = dbMovie.action.toString()
            )
        )
    }

}