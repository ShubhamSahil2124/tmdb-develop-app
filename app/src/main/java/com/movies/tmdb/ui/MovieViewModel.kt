package com.movies.tmdb.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movies.tmdb.repositories.RepositoryData
import kotlinx.coroutines.Dispatchers

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val titleMovie = MutableLiveData<String>()
    val movie: MutableLiveData<List<Movies>> by lazy { MutableLiveData<List<Movies>>() }

    init {
        RepositoryData.initDatabase(application)
    }

    fun setMovie(title: String) {
        titleMovie.value = title
    }

    fun getMovies() : LiveData<List<Movies>>{
        return RepositoryData.getMovies()
    }


    fun getFilteredMovies(titleQuery: String,movies:List<Movies>) : List<Movies> {
        val filteredList= movies.filter { it.original_title.contains(titleQuery) }
        return filteredList
    }

}