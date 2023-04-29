package models

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ActorTest {

  private lateinit var film: Film

    @BeforeEach
    fun setup() {
        film = Film(
            filmId = 1,
            filmRating = 1,
            filmGenre = "Action",
            filmTitle = "Inception",
            isFilmArchived = false,
        )
    }

    @Nested
    inner class addActor {


        @Test
        fun `test addActor adds an actor to film`() {
            val actor = Actor(
                1, "Chris Evans", 43, "American",
                50.00, "M", 5
            )

            assertTrue(film.addActor(actor))
            assertEquals(1, film.numberOfActors())
        }

        /*@Test
        fun testAddDuplicateActor() {
            val actorManagement = ActorManagement()
            val actor = Actor("John Doe")
            actorManagement.addActor(actor)
            val result: Boolean = actorManagement.addActor(actor)
            assertFalse(result)
        }
    }*/
    }}