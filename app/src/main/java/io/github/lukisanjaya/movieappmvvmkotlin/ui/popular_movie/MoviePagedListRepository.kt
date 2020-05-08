package io.github.lukisanjaya.movieappmvvmkotlin.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.POST_PER_PAGE
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.TheMovieDBInterface
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.MovieDataSource
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.MovieDataSourceFactory
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.NetworkState
import io.github.lukisanjaya.movieappmvvmkotlin.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Luki Sanjaya on 08/05/20.
 * ukykazuki@gmail.com
 */
class MoviePagedListRepository (private val apiService : TheMovieDBInterface) {
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>>{
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState)
    }
}