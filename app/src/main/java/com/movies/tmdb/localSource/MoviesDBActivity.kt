package com.movies.tmdb.localSource

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.tmdb.adapters.MoviesAdapter
import com.movies.tmdb.R
import com.movies.tmdb.localSource.AppDatabase
import com.movies.tmdb.repositories.RepositoryData
import com.movies.tmdb.ui.MovieDetailsActivity
import com.movies.tmdb.ui.Movies
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.item_list_movies_content.view.*

class MoviesDBActivity : AppCompatActivity(), MoviesDBAdapter.OnMovieClickListener {

    private var tTemp: String = "popular"
    private var tbool: Int = 0
    private lateinit var appDatabase: AppDatabase
    private var moviesList = emptyList<Movies>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

topLayout.visibility=View.GONE
searchView.visibility=View.GONE
        movieRecyclerView.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        initDatabase(this)
        getRecyclerViewData()

    }

    fun initDatabase(context: Context) {
        if (!this::appDatabase.isInitialized)
            appDatabase = AppDatabase.getDatabase(context)
    }

    private fun getRecyclerViewData() {


        populateMovieRecycler(getMoviesFromLocal())

    }

    private fun getMoviesFromLocal(): List<Movies> {
        return appDatabase.getMoviesDao().getAllPopularMovies()
    }

    private fun populateMovieRecycler(moviesList: List<Movies>) {

        movieRecyclerView.adapter =
            MoviesDBAdapter(moviesList, this)

    }

    override fun onMovieCLick(movie: View, position: Int) {
        val movieDetailsIntent = Intent(this, MovieDetailsActivity::class.java)
        movieDetailsIntent.putExtra("id", movie.movieId.text)
        startActivity(movieDetailsIntent)
    }

    override fun onMovieFavCLick(movie: Movies, position: Int) {

    }

}
