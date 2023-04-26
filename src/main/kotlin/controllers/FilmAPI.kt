package controllers

import models.Film

class FilmAPI {
    private var films = ArrayList<Film>()

    fun add(note: Film): Boolean {
        return films.add(note)
    }

    fun listAllFilms(): String {
        return if (films.isEmpty()) {
            "No films stored"
        } else {
            var listOfFilms = ""
            for (i in films.indices) {
                listOfFilms += "${i}: ${films[i]} \n"
            }
            listOfFilms
        }
    }

    fun numberOfFilms() = films.size

    fun findFilm(index: Int): Film? {
        return if (isValidListIndex(index, films)) {
            films[index]
        } else null
    }

    // utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<*>) = index >= 0 && index < list.size

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, films);
    }


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
            for (note in films) {
                if (note.isFilmArchived) {
                    listOfArchivedFilms += "${films.indexOf(note)}: $note \n"
                }
            }
            listOfArchivedFilms
        }
    }


    fun numberOfArchivedFilms(): Int {
        //return films.stream().filter { obj: Film -> obj.isFilmArchived }.count().toInt()
        var counter = 0
        for (film in films) {
            if (film.isFilmArchived) {
                counter++
            }
        }
        return counter
    }

    fun numberOfActiveFilms():  Int {
        //return films.stream().filter { p: Film -> !p.isFilmArchived }.count().toInt()
        var counter = 0
        for (film in films) {
            if (!film.isFilmArchived) {
                counter++
            }
        }
        return counter
    }

    fun listFilmsBySelectedRating(rating: Int): String {
        return if (films.isEmpty()) {
            "No films stored"
        } else {
            var listOfFilms = ""
            for (i in films.indices) {
                if (films[i].filmRating == rating) {
                    listOfFilms +=
                        """$i: ${films[i]}
                        """.trimIndent()
                }
            }
            if (listOfFilms.equals("")) {
                "No notes with priority: $rating"
            } else {
                "${numberOfFilmsByRating(rating)} notes with priority $rating: $listOfFilms"
            }
        }
    }

    fun numberOfFilmsByRating(rating: Int): Int {
        //return films.stream().filter { p: Film -> p.filmRating == rating }.count().toInt()
        var counter = 0
        for (film in films) {
            if (film.filmRating == rating) {
                counter++
            }
        }
        return counter
    }

    fun deleteFilm(indexToDelete: Int): Film? {
        return if (isValidListIndex(indexToDelete, films)) {
            films.removeAt(indexToDelete)
        } else null
    }

    fun updateFilm(indexToUpdate: Int, film: Film?): Boolean {
        //find the note object by the index number
        val foundFilm = findFilm(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundFilm != null) && (film != null)) {
            foundFilm.filmTitle = film.filmTitle
            foundFilm.filmRating = film.filmRating
            foundFilm.filmGenre = film.filmGenre
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }



}

