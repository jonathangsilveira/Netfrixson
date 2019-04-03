package br.edu.jgsilveira.portfolio.netfrixson.common

sealed class Result<out T> {

    data class Success<out T>(val value: T?): Result<T>()

    sealed class Failure: Result<Nothing>() {

        data class Response(val code: Int, val message: String): Failure()

        data class Undefined(val cause: Throwable): Failure()

    }

}