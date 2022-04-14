package ru.gb.themovie.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.gb.themovie.databinding.FragmentProfileBinding
import ru.gb.themovie.model.Const

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().let {
            binding.switchAdult.isChecked =
                it.getPreferences(Context.MODE_PRIVATE).getBoolean(Const.ADULT, false)
        }
        binding.switchAdult.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                requireActivity().getPreferences(Context.MODE_PRIVATE).edit().putBoolean(Const.ADULT, true)
                        .apply()
            } else {
                requireActivity().getPreferences(Context.MODE_PRIVATE).edit().putBoolean(Const.ADULT, false)
                        .apply()
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


