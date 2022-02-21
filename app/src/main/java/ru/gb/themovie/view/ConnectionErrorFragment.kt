package ru.gb.themovie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.gb.themovie.databinding.FragmentConnectionErrorBinding
import ru.gb.themovie.model.AppState
import ru.gb.themovie.viewmodel.MainViewModel


class ConnectionErrorFragment : Fragment() {
    private lateinit var controller: ConnectionErrorFragmentCallback
    private  val viewModel : MainViewModel by lazy { ViewModelProvider(requireActivity()).get(MainViewModel::class.java) }
    private var _binding : FragmentConnectionErrorBinding? = null
    private val binding get() = _binding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        controller = requireActivity() as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentConnectionErrorBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val observer = Observer<AppState> { ok(it) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        binding.refreshButton.setOnClickListener {viewModel.getData()}
    }


    private fun ok(it: AppState) {
        when(it){
            is AppState.Success -> {
                controller.setFragmentAfterRefreshConnection()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}