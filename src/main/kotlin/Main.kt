import controllers.FilmAPI
import models.Film
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit


private val logger = KotlinLogging.logger {}
private val filmAPI = FilmAPI()


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


fun updateFilm(){
    logger.info { "addNote() function invoked" }

}

fun deleteFilm(){
    logger.info { "addNote() function invoked" }

}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}