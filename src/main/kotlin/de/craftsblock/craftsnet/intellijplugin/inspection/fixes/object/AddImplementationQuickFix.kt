package de.craftsblock.craftsnet.intellijplugin.inspection.fixes.`object`

import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.CustomQuickFix

class AddImplementationQuickFix(
    private val type: String
) : CustomQuickFix() {

    override fun getFamilyName(): String = "Add $type to implementations"

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val psiElement: PsiElement = descriptor.psiElement
        val psiClass: PsiClass = if (psiElement is PsiMethod) psiElement.parent as PsiClass
        else psiElement as PsiClass

        val implementsList = psiClass.implementsList ?: return

        val factory: PsiElementFactory = JavaPsiFacade.getElementFactory(project)
        val newType = factory.createReferenceFromText(type, psiClass)

        import(factory, psiClass, newType)
        implementsList.add(newType)
    }

}