package ru.gb.themovie.view.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gb.themovie.databinding.FragmentMediaMainBinding
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.Const
import ru.gb.themovie.model.pojo.ResultMovieList
import ru.gb.themovie.model.repository.RepositoryImpl
import ru.gb.themovie.view.MAIN_SERVICE_INT_EXTRA
import ru.gb.themovie.view.MainActivity
import ru.gb.themovie.view.Service
import ru.gb.themovie.view.adapters.BindableRecyclerViewAdapter
import ru.gb.themovie.view.callbacks.FragmentsCallbacks
import ru.gb.themovie.viewmodel.databinding.ItemViewModel
import ru.gb.themovie.viewmodel.databinding.PopularMovieViewModel

const val MOVIES_DATA_BROADCAST_EXTRA = "MOVIES_DATA_BROADCAST_EXTRA"
const val TEST_BROADCAST_INTENT_FILTER = "TEST_BROADCAST_INTENT_FILTER"

class MainFragment : Fragment(), BindableRecyclerViewAdapter.onItemClickListener {

    private var adult: Boolean = false
    private var _binding: FragmentMediaMainBinding? = null
    private val binding get() = _binding!!
    private val observer: Observer<AppState> by lazy { Observer<AppState> { state -> render(state) } }
    private val popularMovieViewModel = PopularMovieViewModel()
    private lateinit var fragmentsCallbacks: FragmentsCallbacks
    private var adapterInCinema: BindableRecyclerViewAdapter = BindableRecyclerViewAdapter()
    private var adapterForTvMovie: BindableRecyclerViewAdapter = BindableRecyclerViewAdapter()

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.getParcelableExtra<ResultMovieList>(MOVIES_DATA_BROADCAST_EXTRA)?.let {
                Log.e("DATA FROM BROADCAST", it.toString())
            }
        }

    }

    override fun onAttach(context: Context) {
        fragmentsCallbacks = requireActivity() as MainActivity

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(
                    broadcastReceiver,
                    IntentFilter(TEST_BROADCAST_INTENT_FILTER)
                )
        }
        initService()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMediaMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    override fun onResume() {
        binding.recyclerPopularInCinema.adapter?.let {
            adapterInCinema = it as BindableRecyclerViewAdapter
            adapterInCinema.setOnItemClickListener(this)
        }
        binding.recyclerPopularOnTv.adapter?.let {
            adapterForTvMovie = it as BindableRecyclerViewAdapter
            adapterForTvMovie.setOnItemClickListener(this)
        }
        super.onResume()
    }

    private fun init() {
        requireActivity().let {
            adult = it.getPreferences(Context.MODE_PRIVATE)
                .getBoolean(Const.ADULT, false)
        }
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = popularMovieViewModel
        }
        popularMovieViewModel.apply {
            setAdult(adult)
            getAppStateLiveData().observe(viewLifecycleOwner, observer)
        }
        binding.recyclerPopularInCinema.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        binding.recyclerPopularOnTv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    companion object {
        var adapter: BindableRecyclerViewAdapter? = null
            get() = field

        @JvmStatic
        @BindingAdapter("app:itemsViewModel")
        fun bindItemViewModels(recyclerView: RecyclerView, itemViewModels: List<ItemViewModel>?) {
            adapter = getOrCreateAdapter(recyclerView)
            itemViewModels?.let { adapter?.updateItems(it) }
        }


        private fun getOrCreateAdapter(recyclerView: RecyclerView): BindableRecyclerViewAdapter {
            return if (recyclerView.adapter != null && recyclerView.adapter is BindableRecyclerViewAdapter) {
                recyclerView.adapter as BindableRecyclerViewAdapter
            } else {
                val bindableRecyclerAdapter = BindableRecyclerViewAdapter()
                recyclerView.adapter = bindableRecyclerAdapter
                bindableRecyclerAdapter
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        context?.unregisterReceiver(broadcastReceiver)
        _binding = null
    }

    override fun movieItemOnClick(movieId: Int): Boolean {
        fragmentsCallbacks.setDetailFragment(movieId)
        return true
    }

    private fun render(it: AppState) {
        when (it) {
            is AppState.Loading -> {
                binding.recyclerPopularInCinema.visibility = View.INVISIBLE
                binding.recyclerPopularOnTv.visibility = View.INVISIBLE
                binding.progressCircularPopularInCinema.visibility = View.VISIBLE
                binding.progressCircularPopularOnTv.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.progressCircularPopularInCinema.visibility = View.GONE
                binding.progressCircularPopularOnTv.visibility = View.GONE
                binding.recyclerPopularInCinema.visibility = View.VISIBLE
                binding.recyclerPopularOnTv.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                fragmentsCallbacks.setConnectionErrorFragment()
            }
        }
    }

    private fun initService() {
        requireContext().apply {
            startService(Intent(this, Service::class.java).apply {
                putExtra(MAIN_SERVICE_INT_EXTRA, 1)
            })
        }
    }

}