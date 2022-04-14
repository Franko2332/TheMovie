package ru.gb.themovie.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.gb.themovie.R
import ru.gb.themovie.databinding.FragmentPersonDetailBinding
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.Const
import ru.gb.themovie.view.MainActivity
import ru.gb.themovie.view.callbacks.FragmentsCallbacks
import ru.gb.themovie.viewmodel.DetailPersonViewModel


class DetailPersonFragment : Fragment() {
    private lateinit var fragmentsCallbacksController: FragmentsCallbacks
    private var _binding: FragmentPersonDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailPersonViewModel by lazy {
        ViewModelProvider(requireActivity()).get(DetailPersonViewModel::class.java)
    }
    private val observer: Observer<AppState> by lazy { Observer<AppState> { state -> render(state) } }

    companion object {
        fun getInstance(person_id: Int): DetailPersonFragment {
            val bundle = Bundle().apply {
                putInt(Const.PERSON_ID, person_id)
            }
            return DetailPersonFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        fragmentsCallbacksController = requireActivity() as MainActivity
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonDetailBinding.inflate(inflater, container, false)
        arguments?.let {
            viewModel.getData(it.getInt(Const.PERSON_ID)).observe(viewLifecycleOwner, observer)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearLiveData()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.openLocationInMapButton.setOnClickListener {
            fragmentsCallbacksController.setPersonBirthInMapFragment(
                binding.textViewPlaceOfBirthFragmentPersonDetail.text.toString())
        }
    }

    private fun render(state: AppState) {
        when (state) {
            is AppState.SuccessPersonDetail -> {
                Picasso.get().load(Const.IMAGE_URL + state.personDetailModel.profile_path)
                    .placeholder(R.drawable.ic_download_placeholder)
                    .error(R.drawable.ic_error_placeholder)
                    .into(binding.imageViewPosterFragmentPersonDetail)
                binding.apply {
                    textViewNameFragmentPersonDetail.text = state.personDetailModel.name
                    textViewBiographyFragmentPersonDetail.text = state.personDetailModel.biography
                    textViewPlaceOfBirthFragmentPersonDetail.text =
                        state.personDetailModel.place_of_birth
                }

            }
        }
    }

}



