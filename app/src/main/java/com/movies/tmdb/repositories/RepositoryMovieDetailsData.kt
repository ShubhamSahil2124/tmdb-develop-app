package com.movies.tmdb.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.movies.tmdb.AppConstants
import com.movies.tmdb.remoteSource.APIClient
import com.movies.tmdb.remoteSource.ApiInterface
import com.movies.tmdb.remoteSource.MovieResponseDetails
import retrofit2.Call
import retrofit2.Response

object RepositoryMovieDetailsData {

    private val apiInterface = APIClient.getRetrofit().create(ApiInterface::class.java)

    val movieDetails : MutableLiveData<MovieResponseDetails> by lazy { MutableLiveData<MovieResponseDetails>() }

    fun getMovieDetail(movieId : String) : MutableLiveData<MovieResponseDetails> {

        val call = apiInterface.getMovieDetails(movieId, AppConstants.API_KEY)

        call.enqueue(object : retrofit2.Callback<MovieResponseDetails> {

            override fun onResponse(
                call: Call<MovieResponseDetails>, response: Response<MovieResponseDetails>) {

                if(response.isSuccessful){

                    movieDetails.postValue(response.body()!!)

                }
            }

            override fun onFailure(call: Call<MovieResponseDetails>, t: Throwable) {

                Log.e("Failure11",t.message.toString())
            }

        })
        return movieDetails
    }
}