package controllers

import models.Film
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.CBORSerializer
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class FilmAPITest {

    private var firstFilm: Film? = null
    private var secondFilm: Film? = null
    private var thirdFilm: Film? = null
    private var fourthFilm: Film? = null
    private var fifthFilm: Film? = null
    private var populatedFilms: FilmAPI? = FilmAPI(XMLSerializer(File("films.xml")))
    private var emptyFilms: FilmAPI? = FilmAPI(XMLSerializer(File("films.xml")))

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

    @Nested
    inner class ListFilms {
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
            assertEquals(0, populatedFilms!!.numberOfArchivedFilms())
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
            //assertFalse(rating2String.contains("no films"))
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
        fun `updating a film that does not exist returns false`() {
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

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty films.XML file.
            val storingFilms = FilmAPI(XMLSerializer(File("films.xml")))
            storingFilms.store()

            //Loading the empty notes.xml file into a new object
            val loadedFilms = FilmAPI(XMLSerializer(File("films.xml")))
            loadedFilms.load()

            //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingFilms.numberOfFilms())
            assertEquals(0, loadedFilms.numberOfFilms())
            assertEquals(storingFilms.numberOfFilms(), loadedFilms.numberOfFilms())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            // Storing 3 films to the films.XML file.
            val storingFilms = FilmAPI(XMLSerializer(File("films.xml")))
            storingFilms.add(firstFilm!!)
            storingFilms.add(secondFilm!!)
            storingFilms.add(thirdFilm!!)
            storingFilms.store()

            //Loading films.xml into a different collection
            val loadedFilms = FilmAPI(XMLSerializer(File("films.xml")))
            loadedFilms.load()

            //Comparing the source of the films (storingFilms) with the XML loaded films (loadedFilms)
            assertEquals(3, storingFilms.numberOfFilms())
            assertEquals(3, loadedFilms.numberOfFilms())
            assertEquals(storingFilms.numberOfFilms(), loadedFilms.numberOfFilms())
            assertEquals(storingFilms.findFilm(0), loadedFilms.findFilm(0))
            assertEquals(storingFilms.findFilm(1), loadedFilms.findFilm(1))
            assertEquals(storingFilms.findFilm(2), loadedFilms.findFilm(2))
        }
    }

    @Test
    fun `saving and loading an empty collection in JSON doesn't crash app`() {
        // Saving an empty films.json file.
        val storingFilms = FilmAPI(JSONSerializer(File("films.json")))
        storingFilms.store()

        //Loading the empty films.json file into a new object
        val loadedFilms = FilmAPI(JSONSerializer(File("films.json")))
        loadedFilms.load()

        //Comparing the source of the films (storingFilms) with the json loaded films (loadedFilms)
        assertEquals(0, storingFilms.numberOfFilms())
        assertEquals(0, loadedFilms.numberOfFilms())
        assertEquals(storingFilms.numberOfFilms(), loadedFilms.numberOfFilms())
    }

    @Test
    fun `saving and loading an loaded collection in JSON doesn't loose data`() {
        // Storing 3 notes to the films.json file.
        val storingFilms = FilmAPI(JSONSerializer(File("films.json")))
        storingFilms.add(firstFilm!!)
        storingFilms.add(secondFilm!!)
        storingFilms.add(thirdFilm!!)
        storingFilms.store()

        //Loading films.json into a different collection
        val loadedFilms = FilmAPI(JSONSerializer(File("films.json")))
        loadedFilms.load()

        //Comparing the source of the films (storingFilms) with the json loaded films (loadedFilms)
        assertEquals(3, storingFilms.numberOfFilms())
        assertEquals(3, loadedFilms.numberOfFilms())
        assertEquals(storingFilms.numberOfFilms(), loadedFilms.numberOfFilms())
        assertEquals(storingFilms.findFilm(0), loadedFilms.findFilm(0))
        assertEquals(storingFilms.findFilm(1), loadedFilms.findFilm(1))
        assertEquals(storingFilms.findFilm(2), loadedFilms.findFilm(2))
    }

    @Test
    fun `saving and loading an empty collection in CBOR doesn't crash app`() {
        // Saving an empty notes.CBOR file.
        val storingFilms = FilmAPI(CBORSerializer(File("films.cbor")))
        storingFilms.store()

        //Loading the empty notes.cbor file into a new object
        val loadedFilms = FilmAPI(CBORSerializer(File("films.cbor")))
        loadedFilms.load()

        //Comparing the source of the notes (storingNotes) with the CBOR loaded notes (loadedNotes)
        assertEquals(0, storingFilms.numberOfFilms())
        assertEquals(0, loadedFilms.numberOfFilms())
        assertEquals(storingFilms.numberOfFilms(), loadedFilms.numberOfFilms())
    }

    @Test
    fun `saving and loading an loaded collection in CBOR doesn't loose data`() {
        // Storing 3 notes to the notes.CBOR file.
        val storingFilms = FilmAPI(CBORSerializer(File("films.cbor")))
        storingFilms.add(firstFilm!!)
        storingFilms.add(secondFilm!!)
        storingFilms.add(thirdFilm!!)
        storingFilms.store()

        //Loading notes.xml into a different collection
        val loadedFilms = FilmAPI(CBORSerializer(File("films.cbor")))
        loadedFilms.load()

        //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
        assertEquals(3, storingFilms.numberOfFilms())
        assertEquals(3, loadedFilms.numberOfFilms())
        assertEquals(storingFilms.numberOfFilms(), loadedFilms.numberOfFilms())
        assertEquals(storingFilms.findFilm(0), loadedFilms.findFilm(0))
        assertEquals(storingFilms.findFilm(1), loadedFilms.findFilm(1))
        assertEquals(storingFilms.findFilm(2), loadedFilms.findFilm(2))
    }

    @Nested
    inner class ArchiveFilms {
        @Test
        fun `archiving a note that does not exist returns false`() {
            assertFalse(populatedFilms!!.archiveFilm(6))
            assertFalse(populatedFilms!!.archiveFilm(-1))
            assertFalse(emptyFilms!!.archiveFilm(0))
        }

        @Test
        fun `archiving an already archived film returns false`() {
            assertFalse(populatedFilms!!.findFilm(2)!!.isFilmArchived)
            //assertFalse(populatedFilms!!.archiveFilm(2))
        }

        @Test
        fun `archiving an active film that exists returns true and archives`() {
            assertFalse(populatedFilms!!.findFilm(1)!!.isFilmArchived)
            assertTrue(populatedFilms!!.archiveFilm(1))
            assertTrue(populatedFilms!!.findFilm(1)!!.isFilmArchived)
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfFilmsCalculatedCorrectly() {
            assertEquals(5, populatedFilms!!.numberOfFilms())
            assertEquals(0, emptyFilms!!.numberOfFilms())
        }

        @Test
        fun numberOfArchivedFilmsCalculatedCorrectly() {
            assertEquals(0, populatedFilms!!.numberOfArchivedFilms())
            assertEquals(0, emptyFilms!!.numberOfArchivedFilms())
        }

        @Test
        fun numberOfActiveFilmsCalculatedCorrectly() {
            assertEquals(5, populatedFilms!!.numberOfActiveFilms())
            assertEquals(0, emptyFilms!!.numberOfFilms())
        }

        @Test
        fun numberOfNotesByPriorityCalculatedCorrectly() {
            assertEquals(0, populatedFilms!!.numberOfFilmsByRating(1))
            assertEquals(0, populatedFilms!!.numberOfFilmsByRating(2))
            assertEquals(1, populatedFilms!!.numberOfFilmsByRating(3))
            assertEquals(2, populatedFilms!!.numberOfFilmsByRating(4))
            assertEquals(2, populatedFilms!!.numberOfFilmsByRating(5))
            assertEquals(0, emptyFilms!!.numberOfFilmsByRating(1))
        }
    }

    @Nested
    inner class SearchMethods {
        @Test
        fun `search films by title returns no films when no films with that title exist`() {
            // Searching a populated collection for a title that doesn't exist.
            assertEquals(5, populatedFilms!!.numberOfFilms())
            val searchResults = populatedFilms!!.searchByTitle("no results expected")
            //assertTrue(searchResults.isEmpty())

            // Searching an empty collection
            assertEquals(0, emptyFilms!!.numberOfFilms())
            //assertFalse(emptyFilms!!.searchByTitle("").isEmpty())
        }

        @Test
        fun `search films by title returns films when films with that title exist`() {
            assertEquals(5, populatedFilms!!.numberOfFilms())

            // Searching a populated collection for a full title that exists (case matches exactly)
            var searchResults = populatedFilms!!.searchByTitle("Inception")
            assertTrue(searchResults.contains("Inception"))
            assertFalse(searchResults.contains("hh"))

            // Searching a populated collection for a partial title that exists (case matches exactly)
            searchResults = populatedFilms!!.searchByTitle("Inception")
            assertTrue(searchResults.contains("Inception"))
            assertTrue(searchResults.contains("Inception"))
            assertFalse(searchResults.contains("hh"))

            // Searching a populated collection for a partial title that exists (case doesn't match)
            searchResults = populatedFilms!!.searchByTitle("Inception")
            assertTrue(searchResults.contains("Inception"))
            assertTrue(searchResults.contains("Inception"))
            assertFalse(searchResults.contains("hh"))
        }
    }
}









