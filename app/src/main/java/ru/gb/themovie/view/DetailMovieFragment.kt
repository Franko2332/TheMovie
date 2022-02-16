package ru.gb.themovie.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.item_popular_movie.view.*
import ru.gb.themovie.databinding.FragmentMovieDetailBinding
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.Const
import ru.gb.themovie.viewmodel.DetailMovieViewModel

class DetailMovieFragment: Fragment() {
    private lateinit var _binding: FragmentMovieDetailBinding
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailMovieViewModel

    companion object{
        fun getiInstance(movieID: Int): DetailMovieFragment{
            val detailMovieFragment: DetailMovieFragment = DetailMovieFragment();
            val bundle: Bundle = Bundle()
            bundle.putInt(Const.MOVIE_ID, movieID)
            detailMovieFragment.arguments = bundle
            return detailMovieFragment
        }
    }

    override fun onAttach(context: Context) {
        viewModel = ViewModelProvider(requireActivity()).get(DetailMovieViewModel::class.java)
        viewModel.initRepo(context)
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt(Const.MOVIE_ID)
        if (id!=null){
            Log.e("Move_id", id.toString())
            val observer = Observer<AppState>{render(it) }
            viewModel.getMovie(id).observe(viewLifecycleOwner, observer)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun render(it: AppState) {
        when(it){
            is AppState.SuccessDetailMovie -> {
                binding.fragmentDetailMovieIconImageView.setBackgroundDrawable(it.movie!!.movieIcon)
                binding.textViewMovieName.text = it.movie!!.movieName
                binding.textViewMovieShortDescription.text = it.movie!!.movieGenr+"-"+it.movie!!.movieDuration
                binding.textViewMovieDescription.text = it.movie!!.movieDescription
            }
        }
    }
}