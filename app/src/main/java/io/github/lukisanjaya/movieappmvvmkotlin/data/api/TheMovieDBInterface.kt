package io.github.lukisanjaya.movieappmvvmkotlin.data.api

import io.github.lukisanjaya.movieappmvvmkotlin.data.vo.MovieDetails
import io.github.lukisanjaya.movieappmvvmkotlin.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Luki Sanjaya on 07/05/20.
 * ukykazuki@gmail.com
 */
interface TheMovieDBInterface {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int) : Single<MovieDetails>

    @GET("movie/popular")
    fun getMoviePopulars(@Query("page") id: Int) : Single<MovieResponse>
}