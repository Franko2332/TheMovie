package ru.gb.themovie.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationBarView
import ru.gb.themovie.R
import ru.gb.themovie.databinding.ActivityMainBinding
import ru.gb.themovie.model.Const
import ru.gb.themovie.view.callbacks.FragmentController
import ru.gb.themovie.view.fragments.*


class MainActivity() : AppCompatActivity(), NavigationBarView.OnItemSelectedListener,
    FragmentController {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var fragmentManager: FragmentManager
    private val fragmentsMap: HashMap<String, Fragment> = HashMap()
    private val mainBroadcastReceiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)
    }

    private fun init() {

        registerReceiver(
            mainBroadcastReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        binding.root.bottom
        binding.bottomNavigationBar.setOnItemSelectedListener(this)
        fragmentsMap.apply {
            put(Const.MAIN_FRAGMENT, MainFragment())
            put(Const.SEARCH_FRAGMENT, SearchFragment())
            put(Const.PROFILE_FRAGMENT, ProfileFragment())
            put(Const.CONNECTION_ERROR_FRAGMENT, ConnectionErrorFragment())
        }

        fragmentManager = supportFragmentManager
        fragmentsMap.get(Const.MAIN_FRAGMENT)?.let {
            fragmentManager.beginTransaction()
                .add(R.id.fragment_holder, it, null).commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_main -> setMainFragment()
            R.id.action_search -> setSearchFragment()
            R.id.action_profile -> setProfileFragment()
        }
        return true
    }

    private fun setMainFragment() {
        fragmentsMap.get(Const.MAIN_FRAGMENT)?.let {
            fragmentManager.beginTransaction().replace(
                R.id.fragment_holder,
                it, Const.MAIN_FRAGMENT
            )
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setSearchFragment() {
        var map: MutableMap<String, Int>? = HashMap<String, Int>()
        fragmentsMap.get(Const.SEARCH_FRAGMENT)?.let {
            fragmentManager.beginTransaction().replace(
                R.id.fragment_holder,
                it, null
            )
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setProfileFragment() {
        fragmentsMap.get(Const.PROFILE_FRAGMENT)?.let {
            fragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_holder,
                    it, null
                )
                .addToBackStack(null)
                .commit()
        }
    }

    override fun setDetailFragment(id: Int) {
        fragmentManager.beginTransaction()
            .replace(
                R.id.fragment_holder,
                DetailMovieFragment.getiInstance(id), Const.DETAIL_MOVIE_FRAGMENT
            )
            .addToBackStack(null).commit()
    }

    override fun setConnectionErrorFragment() {
        fragmentsMap.get(Const.CONNECTION_ERROR_FRAGMENT)?.let {
            fragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_holder,
                    it, Const.CONNECTION_ERROR_FRAGMENT
                )
                .addToBackStack(null)
                .commit()
        }
    }

    override fun setFragmentAfterRefreshConnection() {
        setMainFragment()
    }

    override fun setMovieNoteFragment(title: String, movieId: Int) {
        fragmentManager.beginTransaction().hide(
            fragmentManager.findFragmentByTag(Const.DETAIL_MOVIE_FRAGMENT)!!
        )
            .add(R.id.fragment_holder, MovieNoteFragment.getInstance(title, movieId),
                Const.MOVIE_NOTE_FRAGMENT)
            .addToBackStack(null)
            .commit()
    }

    override fun closeMovieNoteFragment() {
        fragmentManager.beginTransaction()
            .remove(fragmentManager.findFragmentByTag(Const.MOVIE_NOTE_FRAGMENT)!!)
            .commit()
        fragmentManager.popBackStack()
        fragmentManager.beginTransaction()
            .show(fragmentManager.findFragmentByTag(Const.DETAIL_MOVIE_FRAGMENT)!!)
            .commit()
    }


}