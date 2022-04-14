package ru.gb.themovie.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.gb.themovie.R
import ru.gb.themovie.model.Const
import ru.gb.themovie.model.pojo.MovieCreditsCastModel

class MoviePersonsAdapter: RecyclerView.Adapter<MoviePersonsAdapter.MovieActorsViewHolder>(){
    private var items: ArrayList<MovieCreditsCastModel>? = null
    private lateinit var listener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(actorId: Int)
    }

    fun setListener(_listener: onItemClickListener){
        listener = _listener
    }

    class MovieActorsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val posterView = itemView.findViewById<ImageView>(R.id.image_view_movie_actor)
        val actorName = itemView.findViewById<TextView>(R.id.text_view_actor_name)
        val character = itemView.findViewById<TextView>(R.id.text_view_character)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(_items: ArrayList<MovieCreditsCastModel>){
        items = _items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieActorsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_actors, parent, false)
        return MovieActorsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieActorsViewHolder, position: Int) {
        items?.let {actors->
            holder.posterView.setOnClickListener { listener.onItemClick(actors[position].id) }
            Picasso.get()
                .load(Const.IMAGE_URL+actors[position].profile_path)
                .placeholder(R.drawable.ic_download_placeholder)
                .error(R.drawable.ic_error_placeholder)
                .into(holder.posterView)
            holder.character.text = actors[position].character
            holder.actorName.text = actors[position].name
        }
    }

    override fun getItemCount(): Int {
        items?.let { return it.size }
        return 0
    }
}