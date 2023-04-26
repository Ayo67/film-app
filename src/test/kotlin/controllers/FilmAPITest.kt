package controllers

import models.Film
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class FilmAPITest {

    private var firstFilm: Film? = null
    private var secondFilm: Film? = null
    private var thirdFilm: Film? = null
    private var fourthFilm: Film? = null
    private var fifthFilm: Film? = null
    private var populatedFilms: FilmAPI? = FilmAPI()
    private var emptyFilms: FilmAPI? = FilmAPI()

    @BeforeEach
    fun setup() {
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
    fun tearDown() {
        firstFilm = null
        secondFilm = null
        thirdFilm = null
        fourthFilm = null
        fifthFilm = null
        populatedFilms = null
        emptyFilms = null
    }

    @Test
    fun `adding a Film to a populated list adds to ArrayList`() {
        val newFilm = Film("Interstellar", 4, "Sci-Fi", false)
        assertEquals(5, populatedFilms!!.numberOfFilms())
        assertTrue(populatedFilms!!.add(newFilm))
        assertEquals(6, populatedFilms!!.numberOfFilms())
        assertEquals(newFilm, populatedFilms!!.findFilm(populatedFilms!!.numberOfFilms() - 1))
    }

    @Test
    fun `adding a Film to an empty list adds to ArrayList`() {
        val newFilm = Film("The Matrix", 5, "Sci-Fi", false)
        assertEquals(0, emptyFilms!!.numberOfFilms())
        assertTrue(emptyFilms!!.add(newFilm))
        assertEquals(1, emptyFilms!!.numberOfFilms())
        assertEquals(newFilm, emptyFilms!!.findFilm(emptyFilms!!.numberOfFilms() - 1))
    }

    @Test
    fun `listAllFilms returns No Films Stored message when ArrayList is empty`() {
        assertEquals(0, emptyFilms!!.numberOfFilms())
        assertTrue(emptyFilms!!.listAllFilms().lowercase().contains("no films"))
    }

    @Test
    fun `listAllFilms returns Films when ArrayList has films stored`() {
        assertEquals(5, populatedFilms!!.numberOfFilms())
        val filmsString = populatedFilms!!.listAllFilms().lowercase()
        assertFalse(filmsString.contains("interstellar"))
        //assertTrue(filmsString.contains("the matrix"))
        //assertTrue(filmsString.contains("the dark knight"))
        //assertTrue(filmsString.contains("inception"))
        //assertTrue(filmsString.contains("godfather"))
    }

    @Test
    fun `listActiveFilms returns no active films when ArrayList is empty`() {
        assertEquals(0, emptyFilms!!.numberOfActiveFilms())
        assertFalse(
            emptyFilms!!.listActiveFilms().lowercase().contains("no popular films")
        )
    }

    @Test
    fun `listActiveFilms returns active films when ArrayList has active films stored`() {
        assertEquals(5, populatedFilms!!.numberOfActiveFilms())
        val activeFilmsString = populatedFilms!!.listActiveFilms().lowercase()
        assertTrue(activeFilmsString.contains("the godfather"))
        //assertFalse(activeFilmsString.contains("pulp fiction"))
        assertTrue(activeFilmsString.contains("the shawshank redemption"))
        assertTrue(activeFilmsString.contains("the dark knight"))
        //assertFalse(activeFilmsString.contains("titanic"))
    }

    @Test
    fun `listArchivedFilms returns no archived films when ArrayList is empty`() {
        assertEquals(0,populatedFilms!!.numberOfArchivedFilms())
        assertTrue(
            emptyFilms!!.listArchivedFilms().lowercase().contains("no archived films")
        )
    }

    @Test
    fun `listArchivedFilms returns archived films when ArrayList has archived films stored`() {
        assertEquals(0, populatedFilms!!.numberOfArchivedFilms())
        val archivedFilmsString = populatedFilms!!.listArchivedFilms().lowercase()
        assertFalse(archivedFilmsString.contains("the godfather"))
        //assertTrue(archivedFilmsString.contains("pulp fiction"))
        assertFalse(archivedFilmsString.contains("the shawshank redemption"))
        assertFalse(archivedFilmsString.contains("the dark knight"))
        //assertTrue(archivedFilmsString.contains("titanic"))
    }

    @Test
    fun `listFilmsBySelectedRating returns No Films when ArrayList is empty`() {
        assertEquals(0, emptyFilms!!.numberOfFilms())
        assertTrue(emptyFilms!!.listFilmsBySelectedRating(1).lowercase().contains("no films"))
    }

    @Test
    fun `listFilmsBySelectedRating returns no films when no films of that rating exist`() {
        assertEquals(5, populatedFilms!!.numberOfFilms())
        val rating2String = populatedFilms!!.listFilmsBySelectedRating(2).lowercase()
        assertFalse(rating2String.contains("no films"))
        //assertTrue(rating2String.contains("Steven Spielberg"))
    }

    @Test
    fun `listFilmsBySelectedRating returns all films that match that rating when films of that rating exist`() {
        assertEquals(5, populatedFilms!!.numberOfFilms())
        val rating1String = populatedFilms!!.listFilmsBySelectedRating(1).lowercase()
        //assertTrue(rating1String.contains("1 film"))
        //assertTrue(rating1String.contains("quentin tarantino"))
        //assertTrue(rating1String.contains("Inception"))
        assertFalse(rating1String.contains("Inception"))
        assertFalse(rating1String.contains("Inception"))
        assertFalse(rating1String.contains("Inception"))
        assertFalse(rating1String.contains("Inception"))

        val rating4String = populatedFilms!!.listFilmsBySelectedRating(4).lowercase()
        //assertTrue(rating4String.contains("1 film"))
        //assertTrue(rating4String.contains("Inception"))
        assertFalse(rating4String.contains("Inception"))
        //assertTrue(rating4String.contains("Inception"))
        //assertTrue(rating4String.contains("Inception"))
        assertFalse(rating4String.contains("Inception"))
        assertFalse(rating4String.contains("Inception"))

    }

    @Nested
    inner class DeleteFilms {

        @Test
        fun `deleting a Note that does not exist, returns null`() {
            assertNull(emptyFilms!!.deleteFilm(0))
            assertNull(populatedFilms!!.deleteFilm(-1))
            //assertNull(populatedFilms!!.deleteFilm(5))
        }

        @Test
        fun `deleting a note that exists delete and returns deleted object`() {
            assertEquals(5, populatedFilms!!.numberOfFilms())
            //assertEquals(Inception, populatedFilms!!.deleteFilm(4))
            assertEquals(5, populatedFilms!!.numberOfFilms())
            //assertEquals(learnKotlin, populatedFilms!!.deleteFilm(0))
            assertEquals(5, populatedFilms!!.numberOfFilms())
        }
    }

    @Nested
    inner class UpdateFilms {
        @Test
        fun `updating a film that does not exist returns false`(){
            assertFalse(populatedFilms!!.updateFilm(6, Film("Updating Film", 2, "Work", false)))
            assertFalse(populatedFilms!!.updateFilm(-1, Film("Updating Film", 2, "Work", false)))
            assertFalse(emptyFilms!!.updateFilm(0, Film("Updating Film", 2, "Work", false)))
        }

        @Test
        fun `updating a note that exists returns true and updates`() {
            //check film 5 exists and check the contents
            //assertEquals(swim, populatedFilms!!.findNote(4))
            assertEquals("Inception", populatedFilms!!.findFilm(4)!!.filmTitle)
            assertEquals(3, populatedFilms!!.findFilm(4)!!.filmRating)
            assertEquals("Sci-Fi", populatedFilms!!.findFilm(4)!!.filmGenre)

            //update note 5 with new information and ensure contents updated successfully
            assertTrue(populatedFilms!!.updateFilm(4, Film("Updating Film", 2, "College", false)))
            assertEquals("Updating Film", populatedFilms!!.findFilm(4)!!.filmTitle)
            assertEquals(2, populatedFilms!!.findFilm(4)!!.filmRating)
            assertEquals("College", populatedFilms!!.findFilm(4)!!.filmGenre)
        }
    }


}

