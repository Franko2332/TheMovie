package ru.gb.themovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.themovie.App
import ru.gb.themovie.model.room.MovieDataBase

import ru.gb.themovie.model.room.MovieNoteEntity

class MovieNoteViewModel : ViewModel() {
    private var dataBase: MovieDataBase = App.getMovieDatabase()
    private var movieNoteData: MutableLiveData<MovieNoteEntity> = MutableLiveData()

    fun getData(movieId: Int): MutableLiveData<MovieNoteEntity> {
        loadNote(movieId)
        return movieNoteData
    }

    fun saveData(movieNoteEntity: MovieNoteEntity) {
        val runnable = Runnable {
            if (dataBase.movieNoteDao().isExists(movieNoteEntity.movieId)) {
                dataBase.movieNoteDao().update(movieNoteEntity)
            } else dataBase.movieNoteDao().insert(movieNoteEntity)
        }
        val thread = Thread(runnable)
        thread.start()


    }

    fun clearLiveData() {
        movieNoteData.postValue(MovieNoteEntity(0, ""))
    }

    private fun loadNote(movieId: Int) {
        val runnable = Runnable {
            movieNoteData.postValue(dataBase.movieNoteDao().getNoteByMovieId(movieId))
        }
        val thread = Thread(runnable)
        thread.start()
    }


}