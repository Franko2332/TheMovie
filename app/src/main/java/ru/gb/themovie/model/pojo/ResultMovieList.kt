package ru.gb.themovie.model.pojo

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class ResultMovieList(
    val page: Int,
    val results: ArrayList<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)  : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        TODO("results"),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(page)
        parcel.writeInt(total_pages)
        parcel.writeInt(total_results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultMovieList> {
        override fun createFromParcel(parcel: Parcel): ResultMovieList {
            return ResultMovieList(parcel)
        }

        override fun newArray(size: Int): Array<ResultMovieList?> {
            return arrayOfNulls(size)
        }
    }
}