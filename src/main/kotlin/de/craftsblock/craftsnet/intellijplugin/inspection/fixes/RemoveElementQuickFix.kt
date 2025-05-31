package de.craftsblock.craftsnet.intellijplugin.inspection.fixes

import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project

class RemoveElementQuickFix(
    private val familyName: String = "Remove"
) : CustomQuickFix() {

    override fun getFamilyName(): String = familyName
    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        descriptor.psiElement.delete()
    }

}