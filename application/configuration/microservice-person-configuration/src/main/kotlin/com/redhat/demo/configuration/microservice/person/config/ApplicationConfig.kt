package com.redhat.demo.configuration.microservice.person.config

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Qualifier
import jakarta.ws.rs.Produces
import java.util.*


@ApplicationScoped
class ApplicationConfig {

    @Produces
//    @ApplicationRef
    @Default
    fun applicationRef(): UUID {
        return applicationRef
    }

    companion object {
        val applicationRef: UUID = UUID.randomUUID()
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class ApplicationRef