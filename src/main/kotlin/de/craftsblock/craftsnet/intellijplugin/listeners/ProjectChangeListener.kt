package de.craftsblock.craftsnet.intellijplugin.listeners

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.CraftsNetVersionUtils

class ProjectChangeListener : ProjectManagerListener {

    override fun projectClosingBeforeSave(project: Project) = CraftsNetVersionUtils.discardState(project)

}