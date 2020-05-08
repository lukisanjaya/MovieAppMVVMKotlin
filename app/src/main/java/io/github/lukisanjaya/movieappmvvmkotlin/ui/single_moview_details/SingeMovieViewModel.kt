package io.github.lukisanjaya.movieappmvvmkotlin.ui.single_moview_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.NetworkState
import io.github.lukisanjaya.movieappmvvmkotlin.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Luki Sanjaya on 07/05/20.
 * ukykazuki@gmail.com
 */
class SingeMovieViewModel (private val movieRepository : MovieDetailsRepository, movieId: Int): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }
    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}