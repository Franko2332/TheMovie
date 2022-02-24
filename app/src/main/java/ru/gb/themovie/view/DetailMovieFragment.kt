package ru.gb.themovie.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.gb.themovie.R
import ru.gb.themovie.databinding.FragmentMovieDetailBinding
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.Const
import ru.gb.themovie.viewmodel.DetailMovieViewModel

class DetailMovieFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val observer: Observer<AppState> by lazy { Observer<AppState> { state -> render(state) } }
    private val viewModel: DetailMovieViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DetailMovieViewModel::class.java)
    }

    companion object {
        fun getiInstance(movieID: Int): DetailMovieFragment {
            val bundle = Bundle().apply { this.putInt(Const.MOVIE_ID, movieID) }
            val detailMovieFragment = DetailMovieFragment().apply { arguments = bundle }
            return detailMovieFragment
        }
    }

    override fun onAttach(context: Context) {
        viewModel.initRepo(context)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val id = arguments?.getInt(Const.MOVIE_ID)
        id?.let {
            Log.e("Move_id", id.toString())
            viewModel.getMovie(it).observe(viewLifecycleOwner, observer)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(Const.DETAIL_MOVIE_FRAGMENT, "destroy")
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun render(it: AppState) {
        when (it) {
            is AppState.SuccessDetailMovie -> {
                binding.movie = it.movie
            }
        }

    }
}
