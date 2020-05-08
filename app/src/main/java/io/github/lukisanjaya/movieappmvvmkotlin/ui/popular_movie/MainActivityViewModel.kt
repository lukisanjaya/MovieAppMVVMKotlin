package io.github.lukisanjaya.movieappmvvmkotlin.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.NetworkState
import io.github.lukisanjaya.movieappmvvmkotlin.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Luki Sanjaya on 08/05/20.
 * ukykazuki@gmail.com
 */
class MainActivityViewModel(private val movieRepository : MoviePagedListRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val moviePageList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty() : Boolean {
        return moviePageList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}