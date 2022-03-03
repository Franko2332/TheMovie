package ru.gb.themovie.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gb.themovie.databinding.FragmentMediaMainBinding
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.repository.RepositoryImpl
import ru.gb.themovie.view.adapters.BindableRecyclerViewAdapter
import ru.gb.themovie.view.callbacks.ConnectionErrorFragmentCallback
import ru.gb.themovie.view.callbacks.DetailMovieFragmentCallback
import ru.gb.themovie.viewmodel.databinding.ItemViewModel
import ru.gb.themovie.viewmodel.databinding.PopularMovieViewModel
import java.io.Serializable

class MainFragment : Fragment(), BindableRecyclerViewAdapter.onItemClickListener, Serializable {
    private var _binding: FragmentMediaMainBinding? = null
    private val binding get() = _binding!!
    private val observer: Observer<AppState> by lazy { Observer<AppState> { state -> render(state) } }
    private var popularMovieViewModel: PopularMovieViewModel? = null
    private lateinit var errorFragmentCallbackController: ConnectionErrorFragmentCallback
    private lateinit var detailMovieFragmentCallbackController: DetailMovieFragmentCallback
    private var adapterInCinema: BindableRecyclerViewAdapter = BindableRecyclerViewAdapter()
    private var adapterForTvMovie: BindableRecyclerViewAdapter = BindableRecyclerViewAdapter()

    override fun onAttach(context: Context) {
        errorFragmentCallbackController = requireActivity() as MainActivity
        detailMovieFragmentCallbackController = requireActivity() as MainActivity
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMediaMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    override fun onResume() {
        binding.recyclerPopularInCinema.adapter?.let {
            adapterInCinema = it as BindableRecyclerViewAdapter
            adapterInCinema.setOnItemClickListener(this)
        }
        binding.recyclerPopularOnTv.adapter?.let {
            adapterForTvMovie = it as BindableRecyclerViewAdapter
            adapterForTvMovie.setOnItemClickListener(this)
        }
        super.onResume()
    }

    private fun init() {
        popularMovieViewModel = PopularMovieViewModel(repo = RepositoryImpl(requireContext()))
        _binding?.let {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = popularMovieViewModel
        }
        popularMovieViewModel!!.getAppStateLiveData().observe(viewLifecycleOwner, observer)
        binding.recyclerPopularInCinema.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.recyclerPopularOnTv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    companion object {
        var adapter: BindableRecyclerViewAdapter? = null
            get() = field

        @JvmStatic
        @BindingAdapter("app:itemsViewModel")
        fun bindItemViewModels(recyclerView: RecyclerView, itemViewModels: List<ItemViewModel>?) {
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

    private fun render(it: AppState) {
        when (it) {
            is AppState.Loading -> {
                binding.recyclerPopularInCinema.visibility = View.INVISIBLE
                binding.recyclerPopularOnTv.visibility = View.INVISIBLE
                binding.progressCircularPopularInCinema.visibility = View.VISIBLE
                binding.progressCircularPopularOnTv.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.progressCircularPopularInCinema.visibility = View.GONE
                binding.progressCircularPopularOnTv.visibility = View.GONE
                binding.recyclerPopularInCinema.visibility = View.VISIBLE
                binding.recyclerPopularOnTv.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                errorFragmentCallbackController.setConnectionErrorFragment()
            }
        }
    }

}