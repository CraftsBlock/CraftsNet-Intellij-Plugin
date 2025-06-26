package de.craftsblock.craftsnet.intellijplugin.uitls.versioning

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import de.craftsblock.craftsnet.intellijplugin.uitls.ModuleUtils
import kotlin.collections.get

object CraftsNetVersionUtils {

    private val stateListeners: MutableList<StateListener> = mutableListOf()
    private val states: MutableMap<Project, State> = mutableMapOf()

    private fun getState(project: Project, updateOnMissing: Boolean = true): State? {
        if (!states.containsKey(project)) {
            if (updateOnMissing) updateAvailable(project)
            else states[project] = State()
        }

        return states[project]
    }

    fun addStateListener(listener: StateListener) {
        this.stateListeners.add(listener)
    }

    fun removeStateListener(listener: StateListener) {
        this.stateListeners.remove(listener)
    }

    internal fun discardState(project: Project) {
        stateListeners.forEach { it.discardingState(project) }

        states.remove(project)

        stateListeners.forEach { it.discardedState(project) }
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

    internal fun isFeatureFlagAvailable(project: Project, flag: FeatureFlag): Boolean {
        val state: State? = getState(project)

        if (state == null)
            return false

        return state.version.isFeatureFlagAvailable(flag)
    }

    internal fun updateAvailable(project: Project) {
        stateListeners.forEach { it.updatingState(project) }

        val state: State = getState(project, false)!!
        state.use use@{

            state.reset()

            val result = ModuleUtils.findDependenciesWithVersions(project, "de.craftsblock/craftsnet")
            if (result !is MutableMap<*, *>) {
                state.available = false
                state.hideActions = true
                return@use
            }

            val versionRaw = (result["de.craftsblock/craftsnet"] as? String)
            val versionTiles = versionRaw
                ?.split("-")?.firstOrNull()
                ?.split(".")
                ?.mapNotNull { it.toIntOrNull() }

            if (versionTiles == null || versionTiles.size != 3) {
                state.notAvailableDue = "Invalid CraftsNet version ($versionRaw) recognised!"
                state.available = false
                return@use
            }

            val version = Version(versionTiles)
            state.version = version
            if (!version.isFeatureFlagAvailable(FeatureFlag.BASE)) {
                state.notAvailableDue = "The plugin requires at least CraftsNet version " +
                        "${FeatureFlag.BASE.leastVersion()}-SNAPSHOT! (Version currently in use: ${versionRaw})"
                state.available = false
                return@use
            }

            state.available = true

        }

        stateListeners.forEach { it.updatedState(project, state) }
    }

}