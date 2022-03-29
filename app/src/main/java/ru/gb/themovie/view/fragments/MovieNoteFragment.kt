package ru.gb.themovie.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.gb.themovie.databinding.FragmentMovieNoteBinding
import ru.gb.themovie.model.Const
import ru.gb.themovie.model.room.MovieNoteEntity
import ru.gb.themovie.view.MainActivity
import ru.gb.themovie.view.callbacks.FragmentController
import ru.gb.themovie.viewmodel.MovieNoteViewModel

class MovieNoteFragment : Fragment() {
    private var _id: Int? = null
    private lateinit var closeMovieNoteFragmentController: FragmentController
    private var _binding: FragmentMovieNoteBinding? = null
    private val binding get() = _binding!!
    private val observer: Observer<MovieNoteEntity> by lazy {
        Observer<MovieNoteEntity> { x -> render(x) }
    }
    private val viewModel: MovieNoteViewModel by lazy {
        ViewModelProvider(requireActivity())
            .get(MovieNoteViewModel::class.java)
    }

    companion object {
        fun getInstance(movieTitle: String, movieId: Int): MovieNoteFragment {
            val bundle = Bundle().apply {
                putString(Const.MOVIE_TITLE, movieTitle)
                putInt(Const.MOVIE_ID, movieId)
            }
            return MovieNoteFragment().apply { this.arguments = bundle }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        closeMovieNoteFragmentController = requireActivity() as MainActivity
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding?.titleMovieNote.let {
            it?.text = requireArguments().getString(Const.MOVIE_TITLE).toString()
        }
        requireArguments().get(Const.MOVIE_ID).let {
            _id = it as Int
            viewModel.getData(it).observe(viewLifecycleOwner, observer)
        }
        _binding!!.movieNoteSaveImageView.setOnClickListener {
            viewModel.saveData(
                MovieNoteEntity(
                    _id!!,
                    _binding!!.movieNoteEditText.text.toString()
                )
            )
            closeMovieNoteFragmentController.closeMovieNoteFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearLiveData()
        _binding = null
    }

    private fun render(entity: MovieNoteEntity?) {
        if (entity != null) {
            binding!!.movieNoteEditText.setText(entity.note)
        }
    }

}


