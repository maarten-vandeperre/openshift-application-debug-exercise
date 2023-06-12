package com.redhat.demo.configuration.microservice.movietracking.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.redhat.demo.core.usecases.repositories.v1.MovieTrackingRecordRepository
import com.redhat.demo.infra.dataproviders.core.domain.OutboxEventAction
import jakarta.transaction.Transactional
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

class WithBrokerUpdateMovieTrackingRecordRepository(
    private val movieTrackingRecordRepository: MovieTrackingRecordRepository,
    private val movieTrackingRecordChangedBrokerUrl: String
) : MovieTrackingRecordRepository {
    private val client = java.net.http.HttpClient.newBuilder()
        .build()
    private val mapper = ObjectMapper()

    private fun broadcastChange(ref: UUID, action: OutboxEventAction) {
        println("trigger broadcast of update broker: $movieTrackingRecordChangedBrokerUrl")
        val data = mapOf(
            "ref" to UUID.randomUUID().toString(),
            "action" to action.name
        )

        val request = HttpRequest.newBuilder()
            .uri(URI(movieTrackingRecordChangedBrokerUrl))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
            .header("Ce-Id", ref.toString())
            .header("Ce-Specversion", "1.0")
            .header("Ce-Type", "movie-tracking-record.changed." + action.name.lowercase())
            .header("Ce-Source", "microservice-movie-tracking-record")
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println("response: " + response.statusCode())
        println("response: " + response.body())
    }

    @Transactional
    override fun save(movieTrackingRecord: MovieTrackingRecordRepository.DbMovieTrackingRecord): String {
        val action = if (movieTrackingRecordRepository.exists(movieTrackingRecord.ref)) {
            OutboxEventAction.UPDATED
        } else {
            OutboxEventAction.CREATED
        }
        val result = movieTrackingRecordRepository.save(movieTrackingRecord)
        broadcastChange(movieTrackingRecord.ref, action)
        return result
    }

    override fun exists(ref: UUID): Boolean {
        return movieTrackingRecordRepository.exists(ref)
    }

    @Transactional
    override fun delete(ref: UUID) {
        movieTrackingRecordRepository.delete(ref)
        broadcastChange(ref, OutboxEventAction.DELETED)
    }

    override fun get(ref: UUID): MovieTrackingRecordRepository.DbMovieTrackingRecord? {
        return movieTrackingRecordRepository.get(ref)
    }

    override fun search(): List<MovieTrackingRecordRepository.DbMovieTrackingRecord> {
        return movieTrackingRecordRepository.search()
    }


}