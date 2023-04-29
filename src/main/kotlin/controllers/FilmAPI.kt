package controllers

import models.Actor
import models.Film
import persistence.Serializer

class FilmAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var films = ArrayList<Film>()

    private fun formatListString(notesToFormat: List<Film>): String =
        notesToFormat
            .joinToString(separator = "\n") { film ->
                films.indexOf(film).toString() + ": " + film.toString()
            }

    @Throws(Exception::class)
    fun load() {
        films = serializer.read() as ArrayList<Film>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(films)
    }

    fun add(film: Film): Boolean {
        return films.add(film)
    }

    fun listAllFilms(): String =
        if (films.isEmpty()) "No films stored"
        else formatListString(films)

    fun numberOfFilms() = films.size

    fun findFilm(index: Int): Film? {
        return if (isValidListIndex(index, films)) {
            films[index]
        } else null
    }

    // utility method to determine if an index is valid in a list.
    fun listActiveFilms(): String =
        if (numberOfActiveFilms() == 0) "No active films stored"
        else formatListString(films.filter { film -> !film.isFilmArchived })

    fun listArchivedFilms(): String =
        if (numberOfArchivedFilms() == 0) "No archived films stored"
        else formatListString(films.filter { film -> !film.isFilmArchived })

    fun numberOfArchivedFilms(): Int = films.count { film: Film -> film.isFilmArchived }

    fun numberOfActiveFilms(): Int = films.count { film: Film -> film.isFilmArchived }

    fun numberOfFilmsByRating(rating: Int): Int = films.count { p: Film -> p.filmRating == rating }
    fun listFilmsBySelectedRating(rating: Int): String =
        if (films.isEmpty()) "no films stored"
        else {
            val listOfFilms = formatListString(films.filter { film -> film.filmRating == rating })
            if (listOfFilms.equals("")) "No films with rating: $rating"
            else "${numberOfFilmsByRating(rating)} films with rating $rating: $listOfFilms"
        }

    fun deleteFilm(indexToDelete: Int): Film? {
        return if (isValidListIndex(indexToDelete, films)) {
            films.removeAt(indexToDelete)
        } else null
    }

    fun updateFilm(indexToUpdate: Int, film: Film?): Boolean {
        // find the film object by the index number
        val foundFilm = findFilm(indexToUpdate)
        // if the film exists, use the film details passed as parameters to update the found film in the ArrayList.
        if ((foundFilm != null) && (film != null)) {
            foundFilm.filmTitle = film.filmTitle
            foundFilm.filmRating = film.filmRating
            foundFilm.filmGenre = film.filmGenre
            return true
        }
        // if the film was not found, return false, indicating that the update was not successful
        return false
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, films)
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun archiveFilm(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val filmtoArchive = films[indexToArchive]
            if (!filmtoArchive.isFilmArchived) {
                filmtoArchive.isFilmArchived = true
                return true
            }
        }
        return false
    }

    fun searchByTitle(searchString: String) =
        formatListString(
            films.filter { film -> film.filmTitle.contains(searchString, ignoreCase = true) }
        )

    // ******************************** ACTORS ************************

    fun searchActorByName(searchString: String): String {
        val filteredFilms = films.flatMap { film ->
            film.actors.filter { it.name.contains(searchString, ignoreCase = true) }
                .map { actor -> "${film.filmId}: ${film.filmTitle} \n\t$actor\n" }
        }
        return if (filteredFilms.isEmpty()) "No items found for: $searchString"
        else filteredFilms.joinToString("")
    }

}
