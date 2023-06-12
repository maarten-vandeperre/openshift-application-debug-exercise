package com.redhat.demo.core.usecases.repositories.v1

import com.redhat.demo.core.domain.v1.MovieCategoryRef

interface MovieCategoryRepository {
    fun allExist(refs: List<MovieCategoryRef>): Boolean
}