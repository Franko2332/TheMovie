package ru.gb.themovie.view

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import ru.gb.themovie.model.Movie
import ru.gb.themovie.model.Repository
import ru.gb.themovie.model.RepositoryImpl
import ru.gb.themovie.viewmodel.MainViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment() {
    private var _binding : FragmentMediaMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var recycler : RecyclerView
    private val adapter: PopularCinemaAdapter = PopularCinemaAdapter()
    private lateinit var viewModel : MainViewModel

    override fun onAttach(context: Context) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.initRepo(context)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMediaMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()

    }

    private fun init() {
        recycler = binding.recyclerPopular
        recycler.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = adapter
        val observer = Observer<ArrayList<Movie>> { render(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    private fun render(it: ArrayList<Movie>?) {
        if (it != null) {
            adapter.setData(it)
        }
    }
}