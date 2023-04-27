package controllers

import models.Film
import persistence.Serializer

class FilmAPI(serializerType: Serializer){

    private var serializer: Serializer = serializerType
    private var films = ArrayList<Film>()



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

    fun listAllFilms(): String {
        if (films.isEmpty()) {
            return "No films stored"
        } else {
            return films.joinToString{ film ->
                film.toString()
            }
        }
    }

    fun numberOfFilms() = films.size

    fun findFilm(index: Int): Film? {
        return if (isValidListIndex(index, films)) {
            films[index]
        } else null
    }

    // utility method to determine if an index is valid in a list.
    fun listActiveFilms(): String {
        return if (numberOfActiveFilms() == 0) {
            "No active films stored"
        } else {
            var listOfActiveFilms = ""
            for (note in films) {
                if (!note.isFilmArchived) {
                    listOfActiveFilms += "${films.indexOf(note)}: $note \n"
                }
            }
            listOfActiveFilms
        }
    }

    fun listArchivedFilms(): String {
        return if (numberOfArchivedFilms() == 0) {
            "No archived films stored"
        } else {
            var listOfArchivedFilms = ""
            for (film in films) {
                if (film.isFilmArchived) {
                    listOfArchivedFilms += "${films.indexOf(film)}: $film \n"
                }
            }
            listOfArchivedFilms
        }
    }

    fun numberOfArchivedFilms(): Int {
        return films.stream().filter { obj: Film -> obj.isFilmArchived }.count().toInt()

    }
    fun numberOfActiveFilms():  Int {
        return films.stream()
            .filter{film: Film -> !film.isFilmArchived}
            .count()
            .toInt()
    }
    fun listFilmsBySelectedRating(rating: Int): String {
        return films.filter { it.filmRating == rating }
            .takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "\n") { "${films.indexOf(it)}: $it" }
            ?.let { "${numberOfFilmsByRating(rating)} films with rating $rating:\n$it" }
            ?: "No films with rating $rating found"
    }


    fun numberOfFilmsByRating(rating: Int): Int {
        return films.stream().filter { p: Film -> p.filmRating == rating }.count().toInt()
    }



    fun deleteFilm(indexToDelete: Int): Film? {
        return if (isValidListIndex(indexToDelete, films)) {
            films.removeAt(indexToDelete)
        } else null
    }

    fun updateFilm(indexToUpdate: Int, film: Film?): Boolean {
        //find the film object by the index number
        val foundFilm = findFilm(indexToUpdate)

        //if the film exists, use the film details passed as parameters to update the found film in the ArrayList.
        if ((foundFilm != null) && (film != null)) {
            foundFilm.filmTitle = film.filmTitle
            foundFilm.filmRating = film.filmRating
            foundFilm.filmGenre = film.filmGenre
            return true
        }
        //if the film was not found, return false, indicating that the update was not successful
        return false
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, films);
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


}

