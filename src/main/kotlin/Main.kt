import controllers.FilmAPI
import models.Actor
import models.Film
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit

private val logger = KotlinLogging.logger {}
private val filmAPI = FilmAPI(XMLSerializer(File("films.xml")))
// private val filmAPI = FilmAPI(JSONSerializer(File("films.json")))
// private val filmAPI = FilmAPI(CBORSerializer(File("films.cbor")))

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu(): Int {
    return ScannerInput.readNextInt(
        """ 
         > ----------------------------------------
         > |        FILM KEEPER APP               |
         > ----------------------------------------
         > | FILM MENU                            |
         > |   1) Add a Film                      |
         > |   2) List all Films                  |
         > |   3) Update a Film                   |
         > |   4) Delete a Film                   |
         > |   5) Archive a Film                  |
         > |   6) Search a Film  by title         |
         > ----------------------------------------
         > | ACTOR MENU                           | 
         > |   7) Add Actor to a film             |
         > |   8) Update actor details on a film  |
         > |   9) Delete actor from a film        |
         > |   10) Mark Actor Status              | 
         > |   11) Search Actor by name           |
         > ----------------------------------------
         > |   20) Save Film                      |
         > |   21) Load Film                      |
         > |   0) Exit                            |
         > ----------------------------------------
         > ==>> """.trimMargin(">")
    )
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addFilm()
            2 -> listFilms()
            3 -> updateFilm()
            4 -> deleteFilm()
            5 -> archiveFilm()
            6 -> searchByTitle()
            7 -> addActorToFilm()
            8 -> updateActorsDetailsInFilm()
            9 -> deleteAnActor()
            10 -> markFilmStatus()
            11 -> searchActorByName()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

fun addFilm() {
    val filmTitle = readNextLine("Enter the title of the film: ")
    val filmRating = readNextInt("Enter the rating of the film (1-low, 2, 3, 4, 5-high): ")
    val filmGenre = readNextLine("Enter the genre of the film: ")
    val isFilmArchived = false // By default, a newly added film is not archived
    val isAdded = filmAPI.add(Film(filmId = 0, filmTitle, filmRating, filmGenre, isFilmArchived))

    if (isAdded) {
        println("Film added successfully.")
    } else {
        println("Failed to add film.")
    }
}

fun listFilms() {
    if (filmAPI.numberOfFilms() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL films          |
                  > |   2) View ACTIVE films       |
                  > |   3) View ARCHIVED films     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllFilms()
            2 -> listActiveFilms()
            3 -> listArchivedFilms()
            else -> println("Invalid option entered: " + option)
        }
    } else {
        println("Option Invalid - No films stored")
    }
}

fun updateFilm() {
    // logger.info { "updateFilm() function invoked" }
    listFilms()
    if (filmAPI.numberOfFilms() > 0) {
        // only ask the user to choose the film if film exist
        val indexToUpdate = readNextInt("Enter the index of the film to update: ")
        if (filmAPI.isValidIndex(indexToUpdate)) {
            val filmTitle = readNextLine("Enter a title for the film: ")
            val filmRating = readNextInt("Enter a Rating (1-low, 2, 3, 4, 5-high): ")
            val filmGenre = readNextLine("Enter a genre for the film: ")

            // pass the index of the film and the new film details to FilmAPI for updating and check for success.
            if (filmAPI.updateFilm(indexToUpdate, Film(filmId = 0, filmTitle, filmRating, filmGenre, false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no films for this index number")
        }
    }
}

fun deleteFilm() {
    // logger.info { "deleteFilm() function invoked" }
    listFilms()
    if (filmAPI.numberOfFilms() > 0) {
        // only ask the user to choose the film to delete if films exist
        val indexToDelete = readNextInt("Enter the index of the film to delete: ")
        // pass the index of the film to FilmAPI for deleting and check for success.
        val filmToDelete = filmAPI.deleteFilm(indexToDelete)
        if (filmToDelete != null) {
            println("Delete Successful! Deleted film: ${filmToDelete.filmTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun save() {
    try {
        filmAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        filmAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun listActiveFilms() {
    println(filmAPI.listActiveFilms())
}

fun archiveFilm() {
    listActiveFilms()
    if (filmAPI.numberOfActiveFilms() > 0) {
        // only ask the user to choose the film to archive if active film exist
        val indexToArchive = readNextInt("Enter the index of the film to archive: ")
        // pass the index of the note to NoteAPI for archiving and check for success.
        if (filmAPI.archiveFilm(indexToArchive))
            println("Archive Successful!")
        else
            println("Archive NOT Successful")
    }
}

fun listAllFilms() {
    println(filmAPI.listAllFilms())
}

fun listArchivedFilms() {
    println(filmAPI.listArchivedFilms())
}

fun searchByTitle() {
    val searchName = readNextLine("Enter the title you wish to search by: ")
    val searchResults = filmAPI.searchByTitle(searchName)
    if (searchResults.isEmpty())
        println("No films with that title found")
    else
        println(searchResults)
}

// *******************  ACTOR MENU************************//

fun askUserToChooseActiveFilm(): Film? {
    listFilms()
    if (filmAPI.numberOfFilms() > 0) {
        val indexToSee = readNextInt("Enter the Index of the film you wish to see:")
        if (filmAPI.isValidIndex(indexToSee)) {
            return filmAPI.findFilm(indexToSee)
        }
    }
    return null
}

fun updateActorsDetailsInFilm() {
    val film: Film? = askUserToChooseActiveFilm()
    if (film != null) {
        val actor: Actor? = askUserToChooseActor(film)
        if (actor != null) {
            val newName = readNextLine("\t Enter new Actor Name: ")
            val newAge = readNextInt("\t  Enter new Actor Age: ")
            val newExperience = readNextInt("\t Enter new Actor Experience: ")
            val newGender = readNextLine("\t Enter new Actor Gender: ")
            val newNationality = readNextLine("\t Enter new Actor Nationality: ")
            val newSalary = readNextDouble("\t Enter new Actor Salary: ")

            if (film.update(
                    actor.actorId,
                    Actor(
                            name = newName,
                            age = newAge,
                            experience = newExperience,
                            gender = newGender,
                            nationality = newNationality,
                            salary = newSalary,
                        )
                )
            ) {
                println("Actor details updated")
            } else {
                println("Actor details NOT updated")
            }
        } else {
            println("Invalid Item Id")
        }
    }
}

fun deleteAnActor() {
    val film: Film? = askUserToChooseActiveFilm()
    if (film != null) {
        val actor: Actor? = askUserToChooseActor(film)
        if (actor != null) {
            val isDeleted = film.delete(actor.actorId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

fun markFilmStatus() {
    val film: Film? = askUserToChooseActiveFilm()
    if (film != null) {
        val actor: Actor? = askUserToChooseActor(film)
        if (actor != null) {
            var changeStatus = 'X'
            if (actor.actorStatus) {
                changeStatus = readNextChar("The actor is currently Online...do you want to mark them as Offline?")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    actor.actorStatus = false
            } else {
                changeStatus = readNextChar("The actor is currently Offline...do you want to mark them as Online?")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    actor.actorStatus = true
            }
        }
    }
}

// ************************* ACTORS REPORTS MENU *******************
fun searchActorByName() {
    val searchActor = readNextLine("Enter the Actor name to search by: ")
    val searchResults = filmAPI.searchActorByName(searchActor)
    if (searchResults.isEmpty()) {
        println("No items found")
    } else {
        println(searchResults)
    }
}

fun exitApp() {
    println("Exiting...bye")
    exit(0)
}

// ******************** HElPER FUNCTIONS ***********************

private fun askUserToChooseActor(film: Film): Actor? {
    if (film.numberOfActors() > 0) {
        print(film.listActors())
        return film.findOne(readNextInt("\nEnter the id of the Actor: "))
    } else {
        println("No actors for chosen film")
        return null
    }
}

private fun addActorToFilm() {
    val film: Film? = askUserToChooseActiveFilm()
    film?.let {
        val added = it.addActor(
            Actor(
                actorId = readNextInt("\t Actor ID: "),
                name = readNextLine("\t Actor Name: "),
                age = readNextInt("\t Actor Age: "),
                experience = readNextInt("\t Actor Experience: "),
                gender = readNextLine("\t Actor Gender: "),
                nationality = readNextLine("\t Actor Nationality: "),
                salary = readNextDouble("\t Actor Salary: ")
            )
        )
        println(if (added) "Add Actor Successful!" else "Add NOT Successful")
    }
}
