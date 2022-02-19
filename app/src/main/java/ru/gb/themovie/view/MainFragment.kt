package ru.gb.themovie.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gb.themovie.R
import ru.gb.themovie.databinding.FragmentMediaMainBinding
import ru.gb.themovie.model.*
import ru.gb.themovie.viewmodel.MainViewModel
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment(), Serializable, PopularCinemaAdapter.onItemClickListener {
    private var _binding : FragmentMediaMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller: CallbackToActivityController
    private lateinit var recyclerInCinema : RecyclerView
    private lateinit var recyclerOnTv : RecyclerView
    private val adapterInCinema: PopularCinemaAdapter = PopularCinemaAdapter()
    private val adapterForTvMovie: PopularCinemaAdapter = PopularCinemaAdapter()
    private lateinit var viewModel : MainViewModel

    override fun onAttach(context: Context) {

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.initRepo(context)
        controller = requireActivity() as MainActivity
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
        recyclerInCinema = binding.recyclerPopularInCinema
        recyclerOnTv = binding.recyclerPopularOnTv
        recyclerInCinema.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
        recyclerOnTv.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
        adapterInCinema.setOnItemClickListener(this)
        adapterForTvMovie.setOnItemClickListener(this)
        recyclerInCinema.adapter = adapterInCinema
        recyclerOnTv.adapter = adapterForTvMovie
        val observer = Observer<AppState> { render(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    private fun render(it: AppState) {
        when(it){
            is AppState.Success -> {
                adapterInCinema.setData(it.dataSet)
                adapterForTvMovie.setData(it.dataSetSerials)
                //Log.e("fd", "render")
            }
            is AppState.Error -> controller.setConnectionErrorFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun movieItemOnClick(movieId: Int): Boolean {
        val manager = requireActivity().supportFragmentManager
        manager.beginTransaction().replace(R.id.fragment_holder,
                DetailMovieFragment.getiInstance(movieId), Const.DETAIL_MOVIE_FRAGMENT)
                .addToBackStack(null).commit()
        return true
    }
}