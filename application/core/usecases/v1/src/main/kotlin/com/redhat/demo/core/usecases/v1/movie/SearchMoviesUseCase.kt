package com.redhat.demo.core.usecases.v1.movie

import com.redhat.demo.core.domain.v1.ReadMovie
import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import java.util.*

interface SearchMoviesUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    class Request

    data class Response(
        val movies: List<ReadMovie>
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultSearchMoviesUseCase(
    private val movieRepository: MovieRepository
) : SearchMoviesUseCase {
    override fun execute(requestData: SearchMoviesUseCase.Request): SearchMoviesUseCase.Response {
        return SearchMoviesUseCase.Response(
            movieRepository.search().map {
                ReadMovie(
                    ref = it.ref.toString(),
                    name = it.name,
                    categories = it.categories.map { it.toString() },
                    actors = it.actors.map { it.toString() }
                )
            }
        )
    }

}