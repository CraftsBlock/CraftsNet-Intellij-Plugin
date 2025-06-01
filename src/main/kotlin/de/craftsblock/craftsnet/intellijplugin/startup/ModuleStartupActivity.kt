package de.craftsblock.craftsnet.intellijplugin.startup

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.CraftsNetVersionUtils

class ModuleStartupActivity : ProjectActivity {

    override suspend fun execute(project: Project) = CraftsNetVersionUtils.updateAvailable(project)

}