package com.redhat.demo.core.usecases.v1.movietrackingrecord

import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingActionRepository
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingRecordRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.utils.UuidUtils
import com.redhat.demo.core.usecases.v1.movietrackingrecord.SaveMovieTrackingRecordUseCase.ValidationException
import java.util.*

interface SaveMovieTrackingRecordUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?,
        val movie: String?,
        val person: String?,
        val action: String?
    )

    data class Response(
        val ref: String
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultCreateMovieTrackingRecordUseCase(
    private val movieTrackingRecordRepository: MovieTrackingRecordRepository,
    private val personRepository: PersonRepository,
    private val movieRepository: MovieRepository,
    private val movieTrackingActionRepository: MovieTrackingActionRepository
) : SaveMovieTrackingRecordUseCase {
    override fun execute(requestData: SaveMovieTrackingRecordUseCase.Request): SaveMovieTrackingRecordUseCase.Response {
        if (requestData.ref != null && !UuidUtils.isValidUuid(requestData.ref)) {
            throw ValidationException("Ref '${requestData.ref}' is not a valid UUID")
        }
        requestData.person?.let {
            if (!UuidUtils.isValidUuid(it)) {
                throw ValidationException("Person id '$it' is not a valid UUID")
            }
            if (!personRepository.exists(UUID.fromString(it))) {
                throw ValidationException("Person doesn't exist: $it")
            }
        } ?: throw ValidationException("No person was given")
        requestData.movie?.let {
            if (!UuidUtils.isValidUuid(it)) {
                throw ValidationException("Movie id '$it' is not a valid UUID")
            }
            if (!movieRepository.exists(UUID.fromString(it))) {
                throw ValidationException("Movie doesn't exist: $it")
            }
        } ?: throw ValidationException("No movie was given")
        requestData.action?.let {
            if (!UuidUtils.isValidUuid(it)) {
                throw ValidationException("Movie id '$it' is not a valid UUID")
            }
            if (!movieTrackingActionRepository.allExist(listOf(UUID.fromString(it)))) {
                throw ValidationException("Movie tracking action doesn't exist: $it")
            }
        } ?: throw ValidationException("No movie tracking action was given")
        return SaveMovieTrackingRecordUseCase.Response(
            movieTrackingRecordRepository.save(
                MovieTrackingRecordRepository.DbMovieTrackingRecord(
                    ref = requestData.ref?.let { UUID.fromString(it) } ?: UUID.randomUUID(),
                    movie = UUID.fromString(requestData.movie!!),
                    person = UUID.fromString(requestData.person!!),
                    action = UUID.fromString(requestData.action)
                )
            )
        )
    }

}