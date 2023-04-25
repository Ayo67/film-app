package controllers

import models.Film
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FilmAPITest {

    private var firstFilm: Film? = null
    private var secondFilm: Film? = null
    private var thirdFilm: Film? = null
    private var fourthFilm: Film? = null
    private var fifthFilm: Film? = null
    private var populatedFilms: FilmAPI? = FilmAPI()
    private var emptyFilms: FilmAPI? = FilmAPI()

    @BeforeEach
    fun setup(){
        firstFilm = Film("The Shawshank Redemption", 5, "Drama", false)
        secondFilm = Film("The Godfather", 5, "Drama", false)
        thirdFilm = Film("The Dark Knight", 4, "Action", false)
        fourthFilm = Film("Forrest Gump", 4, "Drama", false)
        fifthFilm = Film("Inception", 3, "Sci-Fi", false)

        //adding 5 Films to the film api
        populatedFilms!!.add(firstFilm!!)
        populatedFilms!!.add(secondFilm!!)
        populatedFilms!!.add(thirdFilm!!)
        populatedFilms!!.add(fourthFilm!!)
        populatedFilms!!.add(fifthFilm!!)
    }

    @AfterEach
    fun tearDown(){
        firstFilm = null
        secondFilm = null
        thirdFilm = null
        fourthFilm = null
        fifthFilm = null
        populatedFilms = null
        emptyFilms = null
    }

    @Test
    fun `adding a Film to a populated list adds to ArrayList`(){
        val newFilm = Film("Interstellar", 4, "Sci-Fi", false)
        assertEquals(5, populatedFilms!!.numberOfFilms())
        assertTrue(populatedFilms!!.add(newFilm))
        assertEquals(6, populatedFilms!!.numberOfFilms())
        assertEquals(newFilm, populatedFilms!!.findFilm(populatedFilms!!.numberOfFilms() - 1))
    }

    @Test
    fun `adding a Film to an empty list adds to ArrayList`(){
        val newFilm = Film("The Matrix", 5, "Sci-Fi", false)
        assertEquals(0, emptyFilms!!.numberOfFilms())
        assertTrue(emptyFilms!!.add(newFilm))
        assertEquals(1, emptyFilms!!.numberOfFilms())
        assertEquals(newFilm, emptyFilms!!.findFilm(emptyFilms!!.numberOfFilms() - 1))
    }

}

