package de.craftsblock.craftsnet.intellijplugin.uitls

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

object CraftsNetVersionUtils {

    private val states: MutableMap<Project, State> = mutableMapOf()

    private fun getState(project: Project, updateOnMissing: Boolean = true): State? {
        if (!states.containsKey(project)) {
            if (updateOnMissing) updateAvailable(project)
            else states[project] = State()
        }

        return states[project]
    }

    internal fun discardState(project: Project) {
        states.remove(project)
    }

    internal fun isAvailable(project: Project, e: AnActionEvent? = null): Boolean {
        val state: State? = getState(project)

        if (state == null) {
            e?.presentation?.isEnabledAndVisible = true
            e?.presentation?.description = "State not found for this project"
            return false
        }

        e?.presentation?.isEnabledAndVisible = !state.hideActions
        e?.presentation?.description = state.notAvailableDue
        return state.available
    }

    internal fun updateAvailable(project: Project) {
        val state: State = getState(project, false)!!
        state.reset()

        val result = ModuleUtils.findDependenciesWithVersions(project, "de.craftsblock/craftsnet")
        if (result !is MutableMap<*, *>) {
            state.available = false
            state.hideActions = true
            return
        }

        val versionRaw = (result["de.craftsblock/craftsnet"] as? String)
        val version = versionRaw
            ?.split("-")?.firstOrNull()
            ?.split(".")
            ?.mapNotNull { it.toIntOrNull() }

        if (version == null || version.size != 3) {
            state.notAvailableDue = "Invalid CraftsNet version ($versionRaw) recognised!"
            state.available = false
            return
        }

        val (major, minor, patch) = version
        if (major < 3 || minor < 4 || patch < 0) {
            state.notAvailableDue = "The plugin requires at least CraftsNet version 3.4.0-SNAPSHOT!" +
                    " (Version currently in use: ${versionRaw})"
            state.available = false
            return
        }

        state.available = true
    }

    private data class State(
        var available: Boolean = false,
        var hideActions: Boolean = false,
        var notAvailableDue: String? = null
    ) {

        fun reset() {
            available = false
            hideActions = false
            notAvailableDue = null
        }

    }

}