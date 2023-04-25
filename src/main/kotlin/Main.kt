import mu.KotlinLogging
import utils.ScannerInput
import java.lang.System.exit


private val logger = KotlinLogging.logger {}

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
         > |   2) List all Film            |
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
            2  -> listFilm()
            3  -> updateFilm()
            4  -> deleteFilm()
            0  -> exitApp()
            else -> println("Invalid option entered: ${option}")

        }
    } while (true)
}


fun addFilm(){
    logger.info { "addNote() function invoked" }

}

fun listFilm(){
    logger.info { "addNote() function invoked" }

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