package com.teodyulgerov.breakingbadcharacters.repository.utils

sealed class RepoResult<out T> {
    data class Success<out T>(val data: T) : RepoResult<T>()

    data class Error(val error: RepoError) : RepoResult<Nothing>()
}

enum class RepoError {
    NO_INTERNET,
    API_UNSUCCESSFUL_REQUEST,
    API_EMPTY_RESPONSE_BODY
}