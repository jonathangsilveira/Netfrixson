package br.edu.jgsilveira.portfolio.netfrixson.api

object Api {

    const val BASE_URL = "https://api.themoviedb.org/"

    const val GUEST_SESSION = "/3/authentication/guest_session/new"

    const val NEW_TOKEN = "/3/authentication/token/new"

    const val KEY = "b5608c7fcd15576edfe8564a93c3c451"

    const val RATED_MOVIES = "/3/guest_session/{guest_session_id}/rated/movies"

    const val MOVIE_DISCOVER = "/3/discover/movie"

    const val RATE_MOVIE = "/3/movie/{movie_id}/rating"

    const val NEW_SESSION = "/3/authentication/session/new"

    const val MOVIE_GENRES = "/3/genre/movie/list"

    const val MOVIE_DETAIL = "/3/movie/{movie_id}"

}