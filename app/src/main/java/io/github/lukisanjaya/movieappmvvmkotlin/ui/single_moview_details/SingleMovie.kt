package io.github.lukisanjaya.movieappmvvmkotlin.ui.single_moview_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import io.github.lukisanjaya.movieappmvvmkotlin.R
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.POSTER_BASE_URL
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.TheMovieDBClient
import io.github.lukisanjaya.movieappmvvmkotlin.data.api.TheMovieDBInterface
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.NetworkState
import io.github.lukisanjaya.movieappmvvmkotlin.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {
    private lateinit var viewModel: SingeMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            buildUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    fun buildUI(it: MovieDetails) {
        tv_movie_title.text = it.title
        tv_movie_tagline.text = it.tagline
        tv_release_date.text = it.releaseDate
        tv_rating.text = it.voteAverage.toString()
        tv_runtime.text = it.runtime.toString()
        tv_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        tv_budget.text = formatCurrency.format(it.budget)
        tv_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId: Int): SingeMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingeMovieViewModel(movieRepository, movieId) as T
            }
        })[SingeMovieViewModel::class.java]
    }
}
