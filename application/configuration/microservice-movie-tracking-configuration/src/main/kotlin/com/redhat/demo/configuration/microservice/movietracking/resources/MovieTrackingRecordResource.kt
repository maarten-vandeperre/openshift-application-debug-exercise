package com.redhat.demo.configuration.microservice.movietracking.resources

import com.redhat.demo.core.domain.v1.ReadMovie
import com.redhat.demo.core.usecases.v1.movie.DeleteMovieUseCase
import com.redhat.demo.core.usecases.v1.movie.SearchMoviesUseCase
import com.redhat.demo.core.usecases.v1.movie.*
import com.redhat.demo.core.usecases.v1.movietrackingrecord.DeleteMovieTrackingRecordUseCase
import com.redhat.demo.core.usecases.v1.movietrackingrecord.GetMovieTrackingRecordUseCase
import com.redhat.demo.core.usecases.v1.movietrackingrecord.SaveMovieTrackingRecordUseCase
import com.redhat.demo.core.usecases.v1.movietrackingrecord.SearchMovieTrackingRecordsUseCase
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/movie-tracking-records")
class MovieTrackingRecordResource(
    private val saveMovieTrackingRecordUseCase: SaveMovieTrackingRecordUseCase,
    private val deleteMovieTrackingRecordUseCase: DeleteMovieTrackingRecordUseCase,
    private val getMovieTrackingRecordUseCase: GetMovieTrackingRecordUseCase,
    private val searchMovieTrackingRecordsUseCase: SearchMovieTrackingRecordsUseCase
) {
    @POST
    @Consumes(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Create movie tracking record")
    @Tag(name = "MOVIE_TRACKING_RECORDS_API")
    fun createMovieTrackingRecord(data: RequestData): Response {
        try {
            return Response.ok(
                saveMovieTrackingRecordUseCase.execute(
                    SaveMovieTrackingRecordUseCase.Request(
                        movie = data.movie,
                        person = data.movie,
                        action = data.action
                    )
                ).ref
            ).build()
        } catch (e: SaveMovieTrackingRecordUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    @PUT
    @Path("/{ref}")
    @Consumes(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Update movie tracking record")
    @Tag(name = "MOVIE_TRACKING_RECORDS_API")
    fun updateMovieTrackingRecord(@PathParam("ref") ref: String, data: RequestData): Response {
        try {
            return Response.ok(
                saveMovieTrackingRecordUseCase.execute(
                    SaveMovieTrackingRecordUseCase.Request(
                        ref = ref,
                        movie = data.movie,
                        person = data.movie,
                        action = data.action
                    )
                ).ref
            ).build()
        } catch (e: SaveMovieTrackingRecordUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        } catch (e: SaveMovieTrackingRecordUseCase.NotFoundException) {
            return Response.status(404, e.localizedMessage).build()
        }
    }

    @DELETE
    @Path("/{ref}")
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Delete movie tracking record")
    @Tag(name = "MOVIE_TRACKING_RECORDS_API")
    fun deleteMovieTrackingRecord(@PathParam("ref") ref: String): Response {
        try {
            deleteMovieTrackingRecordUseCase.execute(DeleteMovieTrackingRecordUseCase.Request(ref = ref))
            return Response.ok().build()
        } catch (e: DeleteMovieUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    @GET
    @Path("/{ref}")
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Get an individual movie tracking record")
    @Tag(name = "MOVIE_TRACKING_RECORDS_API")
    @APIResponseSchema(value = ReadMovie::class)
    fun getMovieTrackingRecord(@PathParam("ref") ref: String): Response {
        try {
            return Response.ok(getMovieTrackingRecordUseCase.execute(GetMovieTrackingRecordUseCase.Request(ref = ref)).movieTrackingRecord).build()
        } catch (e: GetMovieUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        } catch (e: GetMovieUseCase.NotFoundException) {
            return Response.status(404, e.localizedMessage).build()
        }
    }

    @GET
    @Operation(summary = "Get all movie tracking records")
    @Tag(name = "MOVIE_TRACKING_RECORDS_API")
    @APIResponseSchema(value = ReadMovie::class)
    fun searchMovieTrackingRecords(): Response {
        try {
            return Response.ok(searchMovieTrackingRecordsUseCase.execute(SearchMovieTrackingRecordsUseCase.Request()).movieTrackingRecords).build()
        } catch (e: SearchMoviesUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    open class RequestData(
        val movie: String?,
        val person: String?,
        val action: String?,
    )
}