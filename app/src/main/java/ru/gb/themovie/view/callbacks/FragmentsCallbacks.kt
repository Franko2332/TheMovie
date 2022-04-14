package ru.gb.themovie.view.callbacks

interface FragmentsCallbacks {
    fun setConnectionErrorFragment()
    fun setFragmentAfterRefreshConnection()
    fun setDetailFragment(id: Int)
    fun setMovieNoteFragment(title: String, movieId: Int)
    fun setDetailPersonFragment(id: Int)
    fun closeMovieNoteFragment()
    fun setPersonBirthInMapFragment(location: String)
}