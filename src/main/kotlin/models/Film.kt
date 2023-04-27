package models

import kotlinx.serialization.Serializable

@Serializable
data class Film(var filmTitle: String,
                var filmRating: Int,
                var filmGenre: String,
                var isFilmArchived :Boolean){
}