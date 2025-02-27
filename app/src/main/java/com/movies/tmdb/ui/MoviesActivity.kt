package com.movies.tmdb.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.tmdb.adapters.MoviesAdapter
import com.movies.tmdb.R
import com.movies.tmdb.localSource.AppDatabase
import com.movies.tmdb.localSource.MoviesDBActivity
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.item_list_movies_content.view.*

class MoviesActivity : AppCompatActivity(), MoviesAdapter.OnMovieClickListener {

    private lateinit var moviesViewModel: MovieViewModel
    private lateinit var appDatabase: AppDatabase
    private var moviesList = emptyList<Movies>()
    private var remoteMoviesList = emptyList<Movies>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        moviesViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        movieRecyclerView.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        getRecyclerViewData()
        initDatabase(this)
        setupSearchView()

        imageView2.setOnClickListener {
            val intent = Intent(baseContext, MoviesDBActivity::class.java)
            startActivity(intent)
        }
    }

    fun initDatabase(context: Context) {
        if (!this::appDatabase.isInitialized)
            appDatabase = AppDatabase.getDatabase(context)
    }

    private fun getRecyclerViewData() {

        moviesViewModel.getMovies().observe(this, Observer {
            populateMovieRecycler(it)
            remoteMoviesList = it
        })
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


                populateMovieRecycler(
                    moviesViewModel.getFilteredMovies(
                        query ?: "",
                        remoteMoviesList
                    )
                )

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText.takeIf { it?.length == 0 }.let {
                    populateMovieRecycler(remoteMoviesList)
                }
                return false
            }


        }

        )

    }

    private fun populateMovieRecycler(moviesList: List<Movies>) {
        Log.e("MOVIE_LIST", moviesList.toString())
        movieRecyclerView.adapter =
            MoviesAdapter(moviesList, this)

    }

    override fun onMovieCLick(movie: View, position: Int) {
        val movieDetailsIntent = Intent(this, MovieDetailsActivity::class.java)
        movieDetailsIntent.putExtra("id", movie.movieId.text)
        startActivity(movieDetailsIntent)
    }

    override fun onMovieFavCLick(movie: Movies, position: Int) {
        moviesList.toMutableList().add(movie)
        appDatabase.getMoviesDao().insertAllMovies(moviesList)
        Toast.makeText(this, "Saved to favorite", Toast.LENGTH_LONG).show()
    }


}
