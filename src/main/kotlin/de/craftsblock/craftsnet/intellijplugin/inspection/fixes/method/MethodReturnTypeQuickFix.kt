package de.craftsblock.craftsnet.intellijplugin.inspection.fixes.method

import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.CustomQuickFix

class MethodReturnTypeQuickFix(
    private val type: String
) : CustomQuickFix() {

    override fun getFamilyName(): String = "Change return type to $type"

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val psiElement: PsiElement = descriptor.psiElement
        val psiMethod: PsiMethod = psiElement.parent as PsiMethod? ?: return

        val factory: PsiElementFactory = JavaPsiFacade.getElementFactory(project)
        val newType = factory.createTypeFromText(type, psiMethod)

        import(factory, psiMethod.parent as PsiClass, newType)

        val typeElement = factory.createTypeElement(newType)
        psiMethod.returnTypeElement?.replace(typeElement)
    }

}