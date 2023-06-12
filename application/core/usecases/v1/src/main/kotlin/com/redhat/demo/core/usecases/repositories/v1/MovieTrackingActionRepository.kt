package com.redhat.demo.core.usecases.repositories.v1

import com.redhat.demo.core.domain.v1.MovieTrackingActionRef

interface MovieTrackingActionRepository {
    fun allExist(refs: List<MovieTrackingActionRef>): Boolean
}