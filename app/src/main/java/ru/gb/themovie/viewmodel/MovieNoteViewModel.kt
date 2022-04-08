package ru.gb.themovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.themovie.App
import ru.gb.themovie.model.room.MovieDataBase

import ru.gb.themovie.model.room.MovieNoteEntity

class MovieNoteViewModel(): ViewModel() {
    private var dataBase: MovieDataBase = App.getMovieDatabase()
    private var movieNoteData: MutableLiveData<MovieNoteEntity> = MutableLiveData()

    public fun getData(movieId: Int): MutableLiveData<MovieNoteEntity>{
        loadNote(movieId)
        return movieNoteData
    }

    public fun saveData(movieNoteEntity: MovieNoteEntity){
        var runnable = Runnable {
            if(dataBase.movieNoteDao().isExists(movieNoteEntity.movieId)){
                dataBase.movieNoteDao().update(movieNoteEntity)
            } else dataBase.movieNoteDao().insert(movieNoteEntity)
        }
        var thread = Thread(runnable).apply { start() }


    }

    public fun clearLiveData(){
        movieNoteData.postValue(MovieNoteEntity(0, ""))
    }

    private fun loadNote(movieId: Int){
        var runnable = Runnable {
            movieNoteData.postValue(dataBase.movieNoteDao().getNoteByMovieId(movieId))
        }
        var thread = Thread(runnable).apply { start() }
    }



}