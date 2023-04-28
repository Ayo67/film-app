package persistence

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import models.Film
class CBORSerializer(private val file: File) : Serializer {
    @OptIn(ExperimentalSerializationApi::class)
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val byteArray = Cbor.encodeToByteArray(obj as ArrayList<Film>)
        val file = File("films.cbor")
        val outputStream = FileOutputStream(file)
        outputStream.write(byteArray)
        outputStream.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Throws(Exception::class)
    override fun read(): ArrayList<Film> {
        val fileToLoad = File("films.cbor")
        val inputStream = FileInputStream(fileToLoad)
        val byteArray = inputStream.readBytes()
        inputStream.close()
        return Cbor.decodeFromByteArray(byteArray)
    }
}
