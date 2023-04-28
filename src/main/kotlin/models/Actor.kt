package models

data class Actor(
    var actorId: Int = 0,
    var name: String,
    var age: Int?,
    var nationality: String,
    var salary: Double?,
    var gender: String,
    var experience: Int,
    var actorStatus: Boolean = false
) {

    override fun toString() =
        if (actorStatus)
            "$actorId: $actorStatus (Complete)"
        else
            "$actorId: $actorStatus (TODO)"
}
