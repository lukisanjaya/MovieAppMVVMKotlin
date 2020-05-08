package io.github.lukisanjaya.movieappmvvmkotlin.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.TheMovieDBInterface
import io.github.lukisanjaya.movieappmvvmkotlin.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Luki Sanjaya on 08/05/20.
 * ukykazuki@gmail.com
 */
class MovieDataSourceFactory(private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Movie>() {

    val  movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}