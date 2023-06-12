package com.redhat.demo.configuration.microservice.movie.resources

import com.redhat.demo.core.domain.v1.ReadMovie
import com.redhat.demo.core.usecases.v1.movie.DeleteMovieUseCase
import com.redhat.demo.core.usecases.v1.movie.SearchMoviesUseCase
import com.redhat.demo.core.usecases.v1.movie.*
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/movies")
class MovieResource(
    private val saveMovieUseCase: SaveMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) {
    @POST
    @Consumes(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Create movie")
    @Tag(name = "MOVIES_API")
    fun createMovie(data: RequestData): Response {
        try {
            return Response.ok(
                saveMovieUseCase.execute(
                    SaveMovieUseCase.Request(
                        name = data.name,
                        categories = data.categories,
                        actors = data.actors,
                    )
                ).ref
            ).build()
        } catch (e: SaveMovieUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    @PUT
    @Path("/{ref}")
    @Consumes(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Update movie")
    @Tag(name = "MOVIES_API")
    fun updateMovie(@PathParam("ref") ref: String, data: RequestData): Response {
        try {
            return Response.ok(
                saveMovieUseCase.execute(
                    SaveMovieUseCase.Request(
                        ref = ref,
                        name = data.name,
                        categories = data.categories,
                        actors = data.actors,
                    )
                ).ref
            ).build()
        } catch (e: SaveMovieUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        } catch (e: SaveMovieUseCase.NotFoundException) {
            return Response.status(404, e.localizedMessage).build()
        }
    }

    @DELETE
    @Path("/{ref}")
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Delete movie")
    @Tag(name = "MOVIES_API")
    fun deleteMovie(@PathParam("ref") ref: String): Response {
        try {
            deleteMovieUseCase.execute(DeleteMovieUseCase.Request(ref = ref))
            return Response.ok().build()
        } catch (e: DeleteMovieUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    @GET
    @Path("/{ref}")
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Get an individual movie")
    @Tag(name = "MOVIES_API")
    @APIResponseSchema(value = ReadMovie::class)
    fun getMovie(@PathParam("ref") ref: String): Response {
        try {
            return Response.ok(getMovieUseCase.execute(GetMovieUseCase.Request(ref = ref)).movie).build()
        } catch (e: GetMovieUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        } catch (e: GetMovieUseCase.NotFoundException) {
            return Response.status(404, e.localizedMessage).build()
        }
    }

    @GET
    @Operation(summary = "Get all movies")
    @Tag(name = "MOVIES_API")
    @APIResponseSchema(value = ReadMovie::class)
    fun searchMovies(): Response {
        try {
            return Response.ok(searchMoviesUseCase.execute(SearchMoviesUseCase.Request()).movies).build()
        } catch (e: SearchMoviesUseCase.ValidationException) {
            return Response.status(422, e.localizedMessage).build()
        }
    }

    open class RequestData(
        val name: String?,
        var categories: List<String>,
        var actors: List<String>
    )
}