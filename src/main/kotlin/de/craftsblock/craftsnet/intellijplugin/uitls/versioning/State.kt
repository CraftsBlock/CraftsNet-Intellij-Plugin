package de.craftsblock.craftsnet.intellijplugin.uitls.versioning

data class State(
    var available: Boolean = false,
    var hideActions: Boolean = false,
    var version: Version = Version(0, 0, 0),
    var notAvailableDue: String? = null
) {

    fun reset() {
        available = false
        hideActions = false
        notAvailableDue = null
        version.reset()
    }

}
