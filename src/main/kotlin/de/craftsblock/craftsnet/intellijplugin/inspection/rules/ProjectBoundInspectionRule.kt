package de.craftsblock.craftsnet.intellijplugin.inspection.rules

import com.intellij.openapi.project.Project
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule

abstract class ProjectBoundInspectionRule(val project: Project) : CustomInspectionRule()