package ru.gb.themovie.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.gb.themovie.R
import ru.gb.themovie.model.Movie

class PopularCinemaAdapter() : RecyclerView.Adapter<PopularCinemaAdapter.CinemaHolder>() {
    private lateinit var dataSet: ArrayList<Movie>
    private var listener: onItemClickListener? = null

    interface onItemClickListener {
        fun movieItemOnClick(movieId: Int): Boolean
    }

    class CinemaHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view_popular_movie)
        val textView: TextView = view.findViewById(R.id.text_view_movie_rating)
    }

    init {
        dataSet = ArrayList<Movie>()
    }

    public fun setData(dataSets: ArrayList<Movie>) {
        dataSet = dataSets
        notifyDataSetChanged()
    }

    public fun setOnItemClickListener(_listener: onItemClickListener) {
        listener = _listener
    }

    public fun getData(): ArrayList<Movie> {
        return dataSet
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_popular_movie,
            parent, false
        )
        return CinemaHolder(view)
    }

    override fun onBindViewHolder(holder: CinemaHolder, position: Int) {
        holder.imageView.setBackgroundDrawable(dataSet.get(position).movieIcon)
        holder.textView.setText(dataSet.get(position).rating.toString())
        if (dataSet[position].rating > 70) {
            holder.textView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )
        } else if (dataSet[position].rating <= 70 && dataSet.get(position).rating >= 60) {
            holder.textView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.orange
                )
            )
        } else holder.textView.setBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                R.color.gray
            )
        )
        if (listener != null) {
            holder.imageView.setOnClickListener {
                listener!!.movieItemOnClick(dataSet.get(position).movieId) }
        }
    }

    override fun getItemCount(): Int = dataSet.size
}