package ru.gb.themovie.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ru.gb.themovie.BR
import ru.gb.themovie.R
import ru.gb.themovie.view.MainFragment
import ru.gb.themovie.viewmodel.databinding.ItemViewModel

class BindableRecyclerViewAdapter: RecyclerView.Adapter<BindableRecyclerViewAdapter.BindableViewHolder>() {
    private var listener: onItemClickListener? = null
    var itemViewModelData: List<ItemViewModel> = emptyList()
    private val viewTypeToLayoutId: MutableMap<Int, Int> = mutableMapOf()

    interface onItemClickListener {
        fun movieItemOnClick(movieId: Int): Boolean
    }

    public fun setOnItemClickListener(_listener: onItemClickListener){
        listener = _listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            viewTypeToLayoutId[viewType]?:0, parent, false)
        return BindableViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = itemViewModelData[position]
        if (!viewTypeToLayoutId.containsKey(item.viewType)){
            viewTypeToLayoutId[item.viewType]=item.layoutId
        }
        return item.viewType
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.bind(itemViewModelData[position])
        val imageView: ImageView = holder.binding.root.findViewById(R.id.image_view_popular_movie)
                as ImageView
        imageView.setOnClickListener({listener?.let {it.movieItemOnClick(itemViewModelData[position].movieId)}})
    }

    override fun getItemCount(): Int = itemViewModelData.size


    public fun updateItems(data: List<ItemViewModel>){
        itemViewModelData = data ?: emptyList()
        notifyDataSetChanged()
    }

    class BindableViewHolder( val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemViewModel: ItemViewModel){
            binding.setVariable(BR.itemViewModel, itemViewModel)
        }
    }
}