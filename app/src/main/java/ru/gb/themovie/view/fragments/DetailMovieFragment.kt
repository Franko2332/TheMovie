package ru.gb.themovie.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.gb.themovie.BR
import ru.gb.themovie.R
import ru.gb.themovie.databinding.FragmentMovieDetailBinding
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.Const
import ru.gb.themovie.view.MainActivity
import ru.gb.themovie.view.callbacks.FragmentController
import ru.gb.themovie.viewmodel.DetailMovieViewModel

class DetailMovieFragment : Fragment() {
    private var id: Int? = null
    private  lateinit var movieNoteController: FragmentController
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        movieNoteController = requireActivity() as MainActivity
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        id = arguments?.getInt(Const.MOVIE_ID)
        id?.let {
            viewModel.getMovie(it).observe(viewLifecycleOwner, observer)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding?.fabMovieNote?.setOnClickListener { movieNoteController.setMovieNoteFragment(
            _binding?.textViewMovieName?.text.toString(), id!!) }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearLiveData()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun render(it: AppState) {
        when (it) {
            is AppState.SuccessDetailMovie -> {
                binding.setVariable(BR.movieDetail, it.movieDetail)
                Picasso.get().load(Const.IMAGE_URL+it.movieDetail.poster_path)
                    .placeholder(R.drawable.ic_download_placeholder)
                    .into(binding.fragmentDetailMovieIconImageView)
            }
        }
    }
}
