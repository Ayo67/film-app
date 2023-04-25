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
}

