package com.redhat.demo.configuration.microservice.movie.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.redhat.demo.core.usecases.repositories.v1.MovieRepository
import com.redhat.demo.infra.dataproviders.core.domain.OutboxEventAction
import jakarta.transaction.Transactional
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

class WithBrokerUpdateMovieRepository(
    private val movieRepository: MovieRepository,
    private val movieChangedBrokerUrl: String
) : MovieRepository {
    private val client = java.net.http.HttpClient.newBuilder()
        .build()
    private val mapper = ObjectMapper()

    private fun broadcastChange(ref: UUID, action: OutboxEventAction) {
        println("trigger broadcast of update broker: $movieChangedBrokerUrl")
        val data = mapOf(
            "ref" to UUID.randomUUID().toString(),
            "action" to action.name
        )

        val request = HttpRequest.newBuilder()
            .uri(URI(movieChangedBrokerUrl))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
            .header("Ce-Id", ref.toString())
            .header("Ce-Specversion", "1.0")
            .header("Ce-Type", "movie.changed." + action.name.lowercase())
            .header("Ce-Source", "microservice-movie")
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println("response: " + response.statusCode())
        println("response: " + response.body())
    }

    @Transactional
    override fun save(movie: MovieRepository.DbMovie): String {
        val action = if (movieRepository.exists(movie.ref)) {
            OutboxEventAction.UPDATED
        } else {
            OutboxEventAction.CREATED
        }
        val result = movieRepository.save(movie)
        broadcastChange(movie.ref, action)
        return result
    }

    override fun exists(ref: UUID): Boolean {
        return movieRepository.exists(ref)
    }

    @Transactional
    override fun delete(ref: UUID) {
        movieRepository.delete(ref)
        broadcastChange(ref, OutboxEventAction.DELETED)
    }

    override fun get(ref: UUID): MovieRepository.DbMovie? {
        return movieRepository.get(ref)
    }

    override fun search(): List<MovieRepository.DbMovie> {
        return movieRepository.search()
    }


}