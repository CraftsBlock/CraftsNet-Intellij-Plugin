package de.craftsblock.craftsnet.intellijplugin.uitls.versioning

import java.io.Closeable

data class State(
    var available: Boolean = false,
    var hideActions: Boolean = false,
    var version: Version = Version(0, 0, 0),
    var notAvailableDue: String? = null
) : Closeable {

    fun reset() {
        available = false
        hideActions = false
        notAvailableDue = null
        version.reset()
    }

    override fun close() {}

}
