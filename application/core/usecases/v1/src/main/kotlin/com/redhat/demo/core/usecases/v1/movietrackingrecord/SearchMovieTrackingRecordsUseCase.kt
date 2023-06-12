package com.redhat.demo.core.usecases.v1.movietrackingrecord

import com.redhat.demo.core.domain.v1.ReadMovieTrackingRecord
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingRecordRepository
import java.util.*

interface SearchMovieTrackingRecordsUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    class Request

    data class Response(
        val movieTrackingRecords: List<ReadMovieTrackingRecord>
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultSearchMoviesUseCase(
    private val movieTrackingRecordRepository: MovieTrackingRecordRepository
) : SearchMovieTrackingRecordsUseCase {
    override fun execute(requestData: SearchMovieTrackingRecordsUseCase.Request): SearchMovieTrackingRecordsUseCase.Response {
        return SearchMovieTrackingRecordsUseCase.Response(
            movieTrackingRecordRepository.search().map {
                ReadMovieTrackingRecord(
                    ref = it.ref.toString(),
                    person = it.person.toString(),
                    movie = it.movie.toString(),
                    action = it.action.toString()
                )
            }
        )
    }

}