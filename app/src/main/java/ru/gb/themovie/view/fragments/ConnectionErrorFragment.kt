package ru.gb.themovie.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ru.gb.themovie.databinding.FragmentConnectionErrorBinding
import ru.gb.themovie.model.AppState
import ru.gb.themovie.view.MainActivity
import ru.gb.themovie.view.callbacks.FragmentAfterRefreshConnectionCallback
import ru.gb.themovie.view.callbacks.PersonBirthInMapFragmentCallback
import ru.gb.themovie.viewmodel.databinding.PopularMovieViewModel


class ConnectionErrorFragment : Fragment() {
    private lateinit var personBirthInMapFragmentCallback: PersonBirthInMapFragmentCallback
    private lateinit var afterRefreshConnectionCallback: FragmentAfterRefreshConnectionCallback
    private var viewModel: PopularMovieViewModel? = null
    private var _binding: FragmentConnectionErrorBinding? = null
    private val binding get() = _binding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        personBirthInMapFragmentCallback = requireActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConnectionErrorBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val observer = Observer<AppState> { ok(it) }
        viewModel = PopularMovieViewModel().apply {
            getAppStateLiveData().observe(viewLifecycleOwner, observer)
            binding.refreshButton.setOnClickListener { getAppStateLiveData() } }
        }

    private fun ok(it: AppState) {
        when (it) {
            is AppState.Success -> {
                afterRefreshConnectionCallback.setFragmentAfterRefreshConnection()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}