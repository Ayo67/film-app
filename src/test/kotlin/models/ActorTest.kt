package models

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.*

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

        @Test
        fun `test addActor sets the actorId of the added actor`() {
            val actor = Actor(
                1, "Chris Evans", 43, "American",
                50.00, "M", 5
            )
            film.addActor(actor)
            assertEquals(0, actor.actorId)
        }
    }

    @Nested
    inner class ListActors {
        @Test
        fun ` This should return an empty list if there are no actors in the film`() {

            val film = Film(1,"Inception",1,"Action",false)
            val actors = film.listActors()
            //assertTrue(actors.isEmpty())
        }

        @Test
        fun `should return a list of actors in the film`() {
            val film = Film(1,"Inception",1,"Action",false)
            val actor1 = Actor(
                1, "Chris Evans", 43, "American",
                50.00, "M", 5
            )
            val actor2 = Actor(
                2, "Robert Downey Jr.", 56, "American",
                75.00, "M", 9
            )
            film.addActor(actor1)
            film.addActor(actor2)

            val actors = film.listActors()
            assertEquals(2, film.numberOfActors())
        }
    }

        @Test
        fun `this should return null if actor with given ID does not exist`() {

            val film = Film(1,"Inception",1,"Action",false)
            val actor = film.findOne(1)
            assertNull(actor)
        }

        @Test
        fun `this should return the actor with given ID`() {
            val film = Film(1,"Inception",1,"Action",false)
            val actor1 = Actor(
                1, "Chris Evans", 43, "American",
                50.00, "M", 5
            )
            val actor2 = Actor(
                2, "Robert Downey Jr.", 56, "American",
                75.00, "M", 9
            )
            val actor3 = Actor(
                0, "Robert Downey Jr.", 56, "American",
                75.00, "M", 9
            )
            film.addActor(actor1)
            film.addActor(actor2)
            film.addActor(actor3)
            val actor = film.findOne(0)
            assertNotNull(actor)
        }

    @Test
    fun `test delete removes actor with given ID`() {
        val film = Film(1,"Inception",1,"Action",false)
        val actor1 = Actor(
            1, "Chris Evans", 43, "American",
            50.00, "M", 5
        )
        val actor2 = Actor(
            2, "Robert Downey Jr.", 56, "American",
            75.00, "M", 9
        )
        film.addActor(actor1)
        film.addActor(actor2)
        assertTrue(film.delete(1))
        assertFalse(film.actors.contains(actor2))
        assertFalse(film.delete(3)) // Should return false if actor with ID 3 is not in the list
    }

    @Test
    fun `test update updates an actor in the film`() {
        val film = Film(1,"Inception",1,"Action",false)
        val actor1 = Actor(
            1, "Chris Evans", 43, "American",
            50.00, "M", 5
        )
        val actor2 = Actor(
            2, "Robert Downey Jr.", 56, "American",
            75.00, "M", 9
        )
        film.addActor(actor1)
        film.addActor(actor2)

        // create a new actor with updated details
        val updatedActor = Actor(
            2, "Robert Downey Jr.", 56, "American",
            100.00, "M", 11
        )

        val foundActor = film.findOne(2)

    }

    @Test
    fun `markActorStatus should return false if any actor has not completed their role`() {
        val film = Film(1, "Inception", 1, "Action", false)
        val actor1 = Actor(
            1, "Chris Evans", 43, "American",
            50.00, "M", 5
        )
        val actor2 = Actor(
            2, "Robert Downey Jr.", 56, "American",
            75.00, "M", 9
        )
        film.addActor(actor1)
        film.addActor(actor2)
        actor1.actorStatus = true
        actor2.actorStatus = false
        assertFalse(film.markActorStatus())
    }

    @Test
    fun `markActorStatus should return true if all actors have completed their roles`() {
        val film = Film(1, "Inception", 1, "Action", false)
        val actor1 = Actor(
            1, "Chris Evans", 43, "American",
            50.00, "M", 5
        )
        val actor2 = Actor(
            2, "Robert Downey Jr.", 56, "American",
            75.00, "M", 9
        )
        film.addActor(actor1)
        film.addActor(actor2)
        actor1.actorStatus = true
        actor2.actorStatus = true
        assertTrue(film.markActorStatus())
    }

    @Test
    fun `markFilmStatus should return true if there are no actors in the film`() {
        val film = Film(1, "Inception", 1, "Action", false)
        assertTrue(film.markActorStatus())
    }





}




