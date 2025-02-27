package com.movies.tmdb.localSource

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.movies.tmdb.R
import com.movies.tmdb.ui.Movies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_movies_content.view.*

class MoviesDBAdapter(
    val moviesList: List<Movies>,
    private val itemClickListener: OnMovieClickListener
) :
    RecyclerView.Adapter<MoviesDBAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_movies_content, parent, false)
        return MovieViewHolder(movieView)

    }

    interface OnMovieClickListener {
        fun onMovieCLick(movie: View, position: Int)
        fun onMovieFavCLick(movie: Movies, position: Int)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.movieName.text = moviesList[position].original_title
        val releaseDate: ArrayList<String> =
            moviesList[position].release_date.split('-') as ArrayList<String>
        holder.movieYear.text = releaseDate[0]
        holder.id.text = moviesList[position].id.toString()
        holder.ratingBar.rating = moviesList[position].vote_average / 2

        Picasso.get().load(BASE_IMAGE_URL + moviesList[position].poster_path).into(holder.poster)

        holder.fav.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val poster: ImageView = itemView.poster
        val fav: ImageView = itemView.fav
        val movieName: TextView = itemView.movieName
        val movieYear: TextView = itemView.movieYear
        val id: TextView = itemView.movieId
        val ratingBar: RatingBar = itemView.rating_bar

        init {
            itemView.setOnClickListener {
                itemClickListener.onMovieCLick(itemView, position)

            }


        }

    }

    companion object {
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w300"
    }
}