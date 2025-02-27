package com.movies.tmdb.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.movies.tmdb.repositories.RepositoryData

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    init {
        RepositoryData.initDatabase(application)
    }

    fun getMovies(type : String, tBool :Int) : LiveData<List<Movies>>{
        return RepositoryData.getMovies(type, tBool)
    }

}