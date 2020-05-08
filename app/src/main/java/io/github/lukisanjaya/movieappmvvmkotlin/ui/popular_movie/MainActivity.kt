package io.github.lukisanjaya.movieappmvvmkotlin.ui.popular_movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import io.github.lukisanjaya.movieappmvvmkotlin.R
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.TheMovieDBClient
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.TheMovieDBInterface
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.NetworkState
import io.github.lukisanjaya.movieappmvvmkotlin.ui.single_moview_details.SingleMovie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_single_movie.*
import kotlinx.android.synthetic.main.network_state_item.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel

    lateinit var moviewRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        moviewRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()
        val movieAdapter = PopularMoviePagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType: Int = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePageList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(moviewRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
