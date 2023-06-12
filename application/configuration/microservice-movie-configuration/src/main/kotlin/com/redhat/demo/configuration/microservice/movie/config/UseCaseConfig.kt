package com.redhat.demo.configuration.microservice.movie.config

import com.redhat.demo.core.usecases.repositories.v1.MovieCategoryRepository
import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.v1.movie.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class UseCaseConfig {

    @Produces
    fun createMovieUseCase(
        movieRepository: MovieRepository,
        movieCategoryRepository: MovieCategoryRepository,
        personRepository: PersonRepository
    ): SaveMovieUseCase {
        return DefaultSaveMovieUseCase(movieRepository, movieCategoryRepository, personRepository)
    }

    @Produces
    fun deleteMovieUseCase(movieRepository: MovieRepository): DeleteMovieUseCase {
        return DefaultDeleteMovieUseCase(movieRepository)
    }

    @Produces
    fun getMovieUseCase(movieRepository: MovieRepository): GetMovieUseCase {
        return DefaultGetMovieUseCase(movieRepository)
    }

    @Produces
    fun searchMoviesUseCase(movieRepository: MovieRepository): SearchMoviesUseCase {
        return DefaultSearchMoviesUseCase(movieRepository)
    }

}