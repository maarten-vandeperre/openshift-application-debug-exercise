package com.redhat.demo.configuration.microservice.movietracking

import jakarta.ws.rs.core.Application
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Contact
import org.eclipse.microprofile.openapi.annotations.info.Info
import org.eclipse.microprofile.openapi.annotations.info.License
import org.eclipse.microprofile.openapi.annotations.tags.Tag


@OpenAPIDefinition(
    tags = [
        Tag(name = "MOVIE_TRACKING_RECORDS_API", description = "Movie Tracking Records API documentation."),
    ],
    info = Info(
        title = "Movie Tracking Micro Service",
        version = "1.0.0",
        contact = Contact(
            name = "API Support",
            email = "mvandepe+support@redhat.com"
        ),
        license = License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html")
    )
)
class MicroServiceMovieTrackingApplication : Application()