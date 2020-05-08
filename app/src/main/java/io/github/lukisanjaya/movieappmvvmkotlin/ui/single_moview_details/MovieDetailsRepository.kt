package io.github.lukisanjaya.movieappmvvmkotlin.ui.single_moview_details

import androidx.lifecycle.LiveData
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.TheMovieDBInterface
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.MovieDetailsDataSource
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.NetworkState
import io.github.lukisanjaya.movieappmvvmkotlin.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Luki Sanjaya on 07/05/20.
 * ukykazuki@gmail.com
 */
class MovieDetailsRepository(private val apiService: TheMovieDBInterface) {
    lateinit var movieDetailsDataSource: MovieDetailsDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {
        movieDetailsDataSource = MovieDetailsDataSource(apiService, compositeDisposable)
        movieDetailsDataSource.fetchMovieDetails(movieId)
        return movieDetailsDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsDataSource.networkState
    }
}