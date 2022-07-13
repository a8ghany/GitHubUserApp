package com.gnacoding.submissionbfaa.data.remote.response

import com.gnacoding.submissionbfaa.data.model.UserEntity

data class SearchResponse(
    var items: ArrayList<UserEntity>
)
