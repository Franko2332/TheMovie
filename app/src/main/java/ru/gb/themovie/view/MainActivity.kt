package ru.gb.themovie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationBarView
import ru.gb.themovie.R
import ru.gb.themovie.databinding.ActivityMainBinding
import ru.gb.themovie.model.Const

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener,
    CallbackToActivityController {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private val fragmentsMap: HashMap<String, Fragment> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)
    }

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.root.bottom
        binding.bottomNavigationBar.setOnItemSelectedListener(this)
        fragmentsMap.put(Const.MAIN_FRAGMENT, MainFragment())
        fragmentsMap.put(Const.SEARCH_FRAGMENT, SearchFragment())
        fragmentsMap.put(Const.PROFILE_FRAGMENT, ProfileFragment())
        fragmentsMap.put(Const.CONNECTION_ERROR_FRAGMENT, ConnectionErrorFragment())
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(R.id.fragment_holder, fragmentsMap.get(Const.MAIN_FRAGMENT)!!, null).commit()
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

            fragmentManager.beginTransaction().replace(
                R.id.fragment_holder,
                fragmentsMap.get(Const.MAIN_FRAGMENT)!!, Const.MAIN_FRAGMENT
            )
                .addToBackStack(null)
                .commit()
    }

    private fun setSearchFragment() {
        fragmentManager.beginTransaction().replace(
            R.id.fragment_holder,
            fragmentsMap.get(Const.SEARCH_FRAGMENT)!!, null
        )
            .addToBackStack(null)
            .commit()
    }

    private fun setProfileFragment() {
        fragmentManager.beginTransaction()
            .replace(
                R.id.fragment_holder,
                fragmentsMap.get(Const.PROFILE_FRAGMENT)!!, null
            )
            .addToBackStack(null)
            .commit()
    }

    override fun setConnectionErrorFragment() {
        fragmentManager.beginTransaction()
            .remove(fragmentsMap.get(Const.MAIN_FRAGMENT)!!)
            .replace(
                R.id.fragment_holder,
                fragmentsMap.get(Const.CONNECTION_ERROR_FRAGMENT)!!, Const.CONNECTION_ERROR_FRAGMENT
            )
            .addToBackStack(null)
            .commit()
    }

    override fun setFragmentAfterRefreshConnection() {
//        fragmentManager.beginTransaction()
//            .remove(fragmentsMap.get(Const.CONNECTION_ERROR_FRAGMENT)!!).commit()
        setMainFragment()
    }


}