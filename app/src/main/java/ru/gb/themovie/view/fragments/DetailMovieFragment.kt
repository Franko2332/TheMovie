package ru.gb.themovie.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import ru.gb.themovie.BR
import ru.gb.themovie.R
import ru.gb.themovie.databinding.FragmentMovieDetailBinding
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.Const
import ru.gb.themovie.view.MainActivity
import ru.gb.themovie.view.adapters.MoviePersonsAdapter
import ru.gb.themovie.view.callbacks.*
import ru.gb.themovie.viewmodel.DetailMovieViewModel

class DetailMovieFragment : Fragment(), MoviePersonsAdapter.onItemClickListener {
    private var id: Int? = null

    private val _adapter = MoviePersonsAdapter()
    private lateinit var movieNoteFragmentCallback: MovieNoteFragmentCallback
    private lateinit var detailPersonFragmentCallback: DetailPersonFragmentCallback
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val observer: Observer<AppState> by lazy { Observer<AppState> { state -> render(state) } }
    private val movieCreditsObserver: Observer<AppState> by lazy {
        Observer<AppState> { state -> rendedMovieCreditsView(state) }
    }
    private val viewModel: DetailMovieViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DetailMovieViewModel::class.java)
    }

    companion object {
        fun getiInstance(movieID: Int): DetailMovieFragment {
            val bundle = Bundle().apply { this.putInt(Const.MOVIE_ID, movieID) }
            return DetailMovieFragment().apply { arguments = bundle }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        movieNoteFragmentCallback = requireActivity() as MainActivity
        detailPersonFragmentCallback = requireActivity() as MainActivity
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        id = arguments?.getInt(Const.MOVIE_ID)
        id?.let {
            viewModel.getMovie(it).observe(viewLifecycleOwner, observer)
            viewModel.getMovieCredits(it).observe(viewLifecycleOwner, movieCreditsObserver)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabMovieNote.setOnClickListener {
            movieNoteFragmentCallback.setMovieNoteFragment(
                _binding?.textViewMovieName?.text.toString(), id!!
            )
        }
        binding.recyclerMovieActors.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            adapter = _adapter
        }
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
                Picasso.get().load(Const.IMAGE_URL + it.movieDetail.poster_path)
                    .placeholder(R.drawable.ic_download_placeholder)
                    .into(binding.fragmentDetailMovieIconImageView)
            }
        }
    }

    private fun rendedMovieCreditsView(state: AppState) {
        when (state){
            is AppState.SuccessMovieCredits -> {
                _adapter.setItems(state.movieCredits)
                _adapter.setListener(this)
            }
        }
    }

    override fun onItemClick(actorId: Int) {
        detailPersonFragmentCallback.setDetailPersonFragment(actorId)
    }
}
