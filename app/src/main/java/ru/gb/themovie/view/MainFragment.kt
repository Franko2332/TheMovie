package ru.gb.themovie.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gb.themovie.R
import ru.gb.themovie.databinding.FragmentMediaMainBinding
import ru.gb.themovie.model.*
import ru.gb.themovie.viewmodel.MainViewModel
import java.io.Serializable

class MainFragment : Fragment(), Serializable, PopularCinemaAdapter.onItemClickListener {
    private var _binding: FragmentMediaMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var errorFragmentCallbackController: ConnectionErrorFragmentCallback
    private lateinit var detailMovieFragmentCallbackController: DetailMovieFragmentCallback
    private lateinit var recyclerInCinema: RecyclerView
    private lateinit var recyclerOnTv: RecyclerView
    private val adapterInCinema: PopularCinemaAdapter = PopularCinemaAdapter()
    private val adapterForTvMovie: PopularCinemaAdapter = PopularCinemaAdapter()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())
                .get(MainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        viewModel.initRepo(context)
        errorFragmentCallbackController = requireActivity() as MainActivity
        detailMovieFragmentCallbackController = requireActivity() as MainActivity
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMediaMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        adapterInCinema.setOnItemClickListener(this)
        adapterForTvMovie.setOnItemClickListener(this)
        recyclerInCinema = binding.recyclerPopularInCinema
        recyclerInCinema.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterInCinema
        }
        recyclerOnTv = binding.recyclerPopularOnTv
        recyclerOnTv.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterForTvMovie
        }
        val observer = Observer<AppState> { render(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    private fun render(it: AppState) {
        when (it) {
            is AppState.Success -> {
                adapterInCinema.setData(it.dataSetMovies)
                adapterForTvMovie.setData(it.dataSetSerials)
            }
            is AppState.Error -> errorFragmentCallbackController.setConnectionErrorFragment()
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