package com.movies.tmdb.remoteSource

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("movie/popular")
    fun getMovie(
        @Query("api_key") apiKey: String = "9e46ba7771d636cc3527c1f8729e3232",
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("/3/movie/{id}")
    fun getMovieDetails(@Path("id") id : String, @Query ("api_key") api_key: String) : Call<MovieResponseDetails>

}