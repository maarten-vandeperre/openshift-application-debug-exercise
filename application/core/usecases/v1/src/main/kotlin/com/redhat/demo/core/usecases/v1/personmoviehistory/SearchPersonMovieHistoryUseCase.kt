package com.redhat.demo.core.usecases.v1.personmoviehistory

import com.redhat.demo.core.domain.v1.ReadActor
import com.redhat.demo.core.domain.v1.ReadMovieTrackRecord
import com.redhat.demo.core.domain.v1.ReadMovieTrackingAction
import com.redhat.demo.core.domain.v1.ReadPersonMovieHistory
import com.redhat.demo.core.usecases.repositories.v1.PersonMovieHistoryRepository
import java.util.*

interface SearchPersonMovieHistoryUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    class Request

    data class Response(
        val accounts: List<ReadPersonMovieHistory>
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultSearchAccountsUseCase(
    private val personMovieHistoryRepository: PersonMovieHistoryRepository
) : SearchPersonMovieHistoryUseCase {
    override fun execute(requestData: SearchPersonMovieHistoryUseCase.Request): SearchPersonMovieHistoryUseCase.Response {
        return SearchPersonMovieHistoryUseCase.Response(
            personMovieHistoryRepository.search().map { p ->
                ReadPersonMovieHistory(
                    personRef = p.personRef.toString(),
                    personName = "${p.firstName} ${p.lastName}",
                    history = p.history.map { h ->
                        ReadMovieTrackRecord(
                            movieRef = h.movieRef.toString(),
                            movieName = h.name,
                            actors = h.actors.map { a ->
                                ReadActor(
                                    personRef = a.personRef.toString(),
                                    name = "${a.firstName} ${a.lastName}"
                                )
                            },
                            action = ReadMovieTrackingAction(
                                ref = h.action.movieTrackingActionRef.toString(),
                                name = h.action.name
                            )
                        )
                    }
                )
            }
        )
    }

}