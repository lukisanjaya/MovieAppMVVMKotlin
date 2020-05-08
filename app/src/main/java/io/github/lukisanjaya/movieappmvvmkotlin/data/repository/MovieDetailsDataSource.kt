package io.github.lukisanjaya.movieappmvvmkotlin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.TheMovieDBInterface
import io.github.lukisanjaya.movieappmvvmkotlin.data.vo.MovieDetails
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import java.lang.Exception

/**
 * Created by Luki Sanjaya on 07/05/20.
 * ukykazuki@gmail.com
 */
class MovieDetailsDataSource (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieResponse: LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message)
                        }
                    )
            )
        }
        catch (e: Exception){
            Log.e("MovieDetailDataSource", e.message)
        }
    }
}