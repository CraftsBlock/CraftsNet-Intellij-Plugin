package de.craftsblock.craftsnet.intellijplugin.uitls.versioning

import com.intellij.openapi.project.Project

interface StateListener {

    fun discardingState(project: Project) {}
    fun discardedState(project: Project) {}

    fun updatingState(project: Project) {}
    fun updatedState(project: Project, state: State) {}

}