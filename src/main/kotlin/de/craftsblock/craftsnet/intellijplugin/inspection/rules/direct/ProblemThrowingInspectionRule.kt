package de.craftsblock.craftsnet.intellijplugin.inspection.rules.direct

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.CustomQuickFix

class ProblemThrowingInspectionRule(
    private val description: String,
    private val highlightType: ProblemHighlightType = ProblemHighlightType.ERROR,
    private val psiElement: PsiElement? = null,
    private vararg val quickFixes: CustomQuickFix?,
) : CustomInspectionRule() {

    override fun checkMethod(holder: ProblemsHolder, method: PsiMethod) {
        registerProblem(holder, psiElement ?: method, description, *quickFixes, highlightType = highlightType)
    }

}