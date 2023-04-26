import controllers.FilmAPI
import models.Film
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit


private val logger = KotlinLogging.logger {}
private val filmAPI = FilmAPI(XMLSerializer(File("films.xml")))



fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        FILM KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a Film                |
         > |   2) List all Films            |
         > |   3) Update a Film             |
         > |   4) Delete a Film             |
         > ----------------------------------
         > |   20) Save Film                |
         > |   21) Load Film                |
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}




fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addFilm()
            2  -> listFilms()
            3  -> updateFilm()
            4  -> deleteFilm()
            20  -> save()
            21  -> load()
            0  -> exitApp()
            else -> println("Invalid option entered: ${option}")

        }
    } while (true)
}


fun addFilm() {
    val filmTitle = readNextLine("Enter the title of the film: ")
    val filmRating = readNextInt("Enter the rating of the film (1-low, 2, 3, 4, 5-high): ")
    val filmGenre = readNextLine("Enter the genre of the film: ")
    val isFilmArchived = false // By default, a newly added film is not archived
    val isAdded = filmAPI.add(Film(filmTitle, filmRating, filmGenre, isFilmArchived))

    if (isAdded) {
        println("Film added successfully.")
    } else {
        println("Failed to add film.")
    }
}


fun listFilms() {
    println(filmAPI.listAllFilms())
}

fun updateFilm() {
    //logger.info { "updateFilm() function invoked" }
    listFilms()
    if (filmAPI.numberOfFilms() > 0) {
        //only ask the user to choose the film if film exist
        val indexToUpdate = readNextInt("Enter the index of the film to update: ")
        if (filmAPI.isValidIndex(indexToUpdate)) {
            val filmTitle = readNextLine("Enter a title for the film: ")
            val filmRating = readNextInt("Enter a Rating (1-low, 2, 3, 4, 5-high): ")
            val filmGenre = readNextLine("Enter a genre for the film: ")

            //pass the index of the film and the new film details to FilmAPI for updating and check for success.
            if (filmAPI.updateFilm(indexToUpdate, Film(filmTitle, filmRating, filmGenre, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no films for this index number")
        }
    }
}


fun deleteFilm(){
    //logger.info { "deleteFilm() function invoked" }
    listFilms()
    if (filmAPI.numberOfFilms() > 0) {
        //only ask the user to choose the film to delete if films exist
        val indexToDelete = readNextInt("Enter the index of the film to delete: ")
        //pass the index of the film to FilmAPI for deleting and check for success.
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



fun exitApp(){
    println("Exiting...bye")
    exit(0)
}