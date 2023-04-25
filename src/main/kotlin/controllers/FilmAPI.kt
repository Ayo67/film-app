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



}