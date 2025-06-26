package de.craftsblock.craftsnet.intellijplugin.inspection

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import de.craftsblock.craftsnet.intellijplugin.uitls.Updatable
import de.craftsblock.craftsnet.intellijplugin.uitls.Utils

abstract class CustomInspectionRule : Updatable {

    var parent: CustomInspection? = null

    open fun adopt(parent: CustomInspection) {
        this.parent = parent
    }

    abstract fun checkMethod(holder: ProblemsHolder, method: PsiMethod)

    internal fun registerProblem(
        holder: ProblemsHolder,
        psiElement: PsiElement,
        description: String,
        vararg quickFixes: LocalQuickFix?,
        highlightType: ProblemHighlightType = ProblemHighlightType.ERROR
    ) {
        holder.registerProblem(psiElement, description, highlightType, *quickFixes.filterNotNull().toTypedArray())
    }

    internal fun getAnnotationName(): String {
        val annotatedInspection = parent as? CustomAnnotatedInspection ?: return "null"
        return annotatedInspection.annotation.let(Utils::stripClass)
    }

    fun isAdopted(): Boolean = this.parent != null

}