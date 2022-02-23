package ru.gb.themovie.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gb.themovie.databinding.FragmentMediaMainBinding
import ru.gb.themovie.model.*
import ru.gb.themovie.view.adapters.BindableRecyclerViewAdapter
import ru.gb.themovie.view.adapters.PopularMovieAdapter
import ru.gb.themovie.view.callbacks.ConnectionErrorFragmentCallback
import ru.gb.themovie.view.callbacks.DetailMovieFragmentCallback
import ru.gb.themovie.viewmodel.MainViewModel
import ru.gb.themovie.viewmodel.databinding.ItemViewModel
import ru.gb.themovie.viewmodel.databinding.PopularMovieViewModel
import java.io.Serializable

class MainFragment : Fragment(),BindableRecyclerViewAdapter.onItemClickListener, Serializable {
    private var _binding: FragmentMediaMainBinding? = null
    private val binding get() = _binding!!
    private var popularMovieViewModel: PopularMovieViewModel? = null
    private lateinit var errorFragmentCallbackController: ConnectionErrorFragmentCallback
    private lateinit var detailMovieFragmentCallbackController: DetailMovieFragmentCallback
    private var adapterInCinema: BindableRecyclerViewAdapter = BindableRecyclerViewAdapter()
    private var adapterForTvMovie: BindableRecyclerViewAdapter = BindableRecyclerViewAdapter()

    override fun onResume() {
        super.onResume()
        adapterInCinema = binding.recyclerPopularInCinema!!.adapter as BindableRecyclerViewAdapter
        adapterInCinema.setOnItemClickListener(this)
        adapterForTvMovie = binding.recyclerPopularOnTv!!.adapter as BindableRecyclerViewAdapter
        adapterForTvMovie.setOnItemClickListener(this)
    }

    override fun onAttach(context: Context) {
        errorFragmentCallbackController = requireActivity() as MainActivity
        detailMovieFragmentCallbackController = requireActivity() as MainActivity
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMediaMainBinding.inflate(inflater, container, false)
        _binding!!.lifecycleOwner = requireActivity()
        popularMovieViewModel = PopularMovieViewModel(repo = RepositoryImpl(requireContext()))
        _binding!!.viewModel = popularMovieViewModel
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        binding.recyclerPopularInCinema.layoutManager =LinearLayoutManager(requireContext(),
                 LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerPopularOnTv.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL, false)
    }

    companion object {
        var adapter: BindableRecyclerViewAdapter? = null
        get() = field
        @JvmStatic
        @BindingAdapter("app:itemsViewModel")
        fun bindItemViewModels(recyclerView: RecyclerView, itemViewModels: List<ItemViewModel>?) {
            Log.e("bindItems", "bind")
            adapter = getOrCreateAdapter(recyclerView)
            itemViewModels?.let { adapter?.updateItems(it) }
        }

        private fun getOrCreateAdapter(recyclerView: RecyclerView): BindableRecyclerViewAdapter {
            return if (recyclerView.adapter != null && recyclerView.adapter is BindableRecyclerViewAdapter) {
                recyclerView.adapter as BindableRecyclerViewAdapter
            } else {
                val bindableRecyclerAdapter = BindableRecyclerViewAdapter()
                recyclerView.adapter = bindableRecyclerAdapter
                bindableRecyclerAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun movieItemOnClick(movieId: Int): Boolean {
        detailMovieFragmentCallbackController.setDetailFragment(movieId)
        return true
    }

}