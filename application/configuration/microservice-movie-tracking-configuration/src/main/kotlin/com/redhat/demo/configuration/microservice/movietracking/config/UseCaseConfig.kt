package com.redhat.demo.configuration.microservice.movietracking.config

import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingActionRepository
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingRecordRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.v1.movietrackingrecord.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class UseCaseConfig {

    @Produces
    fun createMovieTrackingRecordUseCase(
        personRepository: PersonRepository,
        movieRepository: MovieRepository,
        movieTrackingRecordRepository: MovieTrackingRecordRepository,
        movieTrackingActionRepository: MovieTrackingActionRepository
    ): SaveMovieTrackingRecordUseCase {
        return DefaultSaveMovieTrackingRecordUseCase(
            movieTrackingRecordRepository,
            personRepository,
            movieRepository,
            movieTrackingActionRepository
        )
    }

    @Produces
    fun deleteMovieTrackingRecordUseCase(movieTrackingRecordRepository: MovieTrackingRecordRepository): DeleteMovieTrackingRecordUseCase {
        return DefaultDeleteMovieTrackingRecordUseCase(movieTrackingRecordRepository)
    }

    @Produces
    fun getMovieTrackingRecordUseCase(movieTrackingRecordRepository: MovieTrackingRecordRepository): GetMovieTrackingRecordUseCase {
        return DefaultGetMovieTrackingRecordUseCase(movieTrackingRecordRepository)
    }

    @Produces
    fun searchMovieTrackingRecordsUseCase(movieTrackingRecordRepository: MovieTrackingRecordRepository): SearchMovieTrackingRecordsUseCase {
        return DefaultSearchMovieTrackingRecordsUseCase(movieTrackingRecordRepository)
    }

}