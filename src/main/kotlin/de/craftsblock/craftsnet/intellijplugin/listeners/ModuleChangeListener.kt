package de.craftsblock.craftsnet.intellijplugin.listeners

import com.intellij.openapi.roots.ModuleRootEvent
import com.intellij.openapi.roots.ModuleRootListener
import de.craftsblock.craftsnet.intellijplugin.utils.versioning.CraftsNetVersionUtils

class ModuleChangeListener : ModuleRootListener {

    override fun rootsChanged(event: ModuleRootEvent) = CraftsNetVersionUtils.updateAvailable(event.project)

}