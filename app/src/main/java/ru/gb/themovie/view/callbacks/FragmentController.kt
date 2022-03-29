package ru.gb.themovie.view.callbacks

interface FragmentController {
    fun setConnectionErrorFragment()
    fun setFragmentAfterRefreshConnection()
    fun setDetailFragment(id: Int)
    fun setMovieNoteFragment(title: String, movieId: Int)
    fun closeMovieNoteFragment()
}