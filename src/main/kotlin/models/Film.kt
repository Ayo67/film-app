package models

import utils.Utilities

// @Serializable

data class Film(
    var filmId: Int = 0,
    var filmTitle: String,
    var filmRating: Int,
    var filmGenre: String,
    var isFilmArchived: Boolean,
    var actors: MutableSet<Actor> = mutableSetOf()
) {

    private var lastActorId = 0
    private fun getActorId() = lastActorId++

    fun addActor(actor: Actor): Boolean {
        actor.actorId = getActorId()
        return actors.add(actor)
    }

    fun numberOfActors() = actors.size

    fun findOne(id: Int): Actor? {
        return actors.find { actor -> actor.actorId == id }
    }

    fun delete(id: Int): Boolean {
        return actors.removeIf { actor -> actor.actorId == id }
    }

    fun update(id: Int, newActor: Actor): Boolean {
        val foundActor = findOne(id)
        // if the object exists, use the details passed in the newActor parameter to
        // update the found object in the Set
        if (foundActor != null) {
            foundActor.actorId = newActor.actorId
            foundActor.name = newActor.name
            foundActor.age = newActor.age
            foundActor.nationality = newActor.nationality
            foundActor.salary = newActor.salary
            foundActor.gender = newActor.gender
            foundActor.experience = newActor.experience
            return true
        }
        // if the object was not found, return false, indicating that the update was not successful
        return false
    }

    fun listActors() =
        if (actors.isEmpty()) "\tNO Actors ADDED"
        else Utilities.formatSetString(actors)

    override fun toString(): String {
        val archived = if (isFilmArchived) 'Y' else 'N'
        return "$filmId: $filmTitle, Rating($filmRating), Genre($filmGenre), Archived($archived) \n${listActors()}"
    }

    fun markActorStatus(): Boolean {
        // Check if the list of actors is not empty
        if (actors.isNotEmpty()) {
            // Iterate through each actor in the list
            for (actor in actors) {
                // Check if the actor's status is false
                if (!actor.actorStatus) {
                    // If the actor's status is false, return false immediately
                    return false
                } } }
        // If all actors in the list have their status set to true, return true
        return true
    }




}
