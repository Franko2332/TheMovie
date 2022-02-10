package ru.gb.themovie.view

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.gb.themovie.R
import ru.gb.themovie.model.Movie

class PopularCinemaAdapter() :RecyclerView.Adapter<PopularCinemaAdapter.CinemaHolder>() {
    private lateinit var dataSet: ArrayList<Movie>
    class CinemaHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view_popular_movie)
        }


    public fun setData(dataSets: ArrayList<Movie>){
        dataSet = dataSets
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie,
                parent, false)
       return CinemaHolder(view)
    }

    override fun onBindViewHolder(holder: CinemaHolder, position: Int) {
        holder.imageView.setBackgroundDrawable(dataSet.get(position).movieIcon)
    }

    override fun getItemCount(): Int = dataSet.size
}