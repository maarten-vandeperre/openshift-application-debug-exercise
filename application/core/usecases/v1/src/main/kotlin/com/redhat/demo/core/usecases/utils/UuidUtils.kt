package com.redhat.demo.core.usecases.utils

import java.util.UUID

object UuidUtils {
    fun isValidUuid(data: String?): Boolean {
        return try {
            data
                ?.let { UUID.fromString(data) }
                ?.let { true }
                ?: true
        } catch (e: Exception){
            false
        }
    }
}