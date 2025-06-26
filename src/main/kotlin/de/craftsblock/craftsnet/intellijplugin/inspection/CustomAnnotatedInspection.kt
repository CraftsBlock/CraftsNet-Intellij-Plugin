package de.craftsblock.craftsnet.intellijplugin.inspection

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiMethod
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.CraftsNetVersionUtils

abstract class CustomAnnotatedInspection(
    name: String,
    val annotation: String,
    vararg rules: CustomInspectionRule,
    skipAdopt: Boolean = false
) : CustomInspection(name, *rules, skipAdopt = true) {

    init {
        if (!skipAdopt) rules.forEach { it.adopt(this) }
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : JavaElementVisitor() {

            override fun visitMethod(method: PsiMethod) {
                if (!CraftsNetVersionUtils.isAvailable(method.project)) return

                val isAnnotated = method.annotations.any { it.qualifiedName?.equals(annotation, true) == true }
                if (!isAnnotated) return

                for (rule in (rules.toSet() + dynamicRules(method))) {
                    rule.checkMethod(holder, method)

                    if (holder.hasResults() && holder.results.any {
                            it.highlightType == ProblemHighlightType.ERROR ||
                                    it.highlightType == ProblemHighlightType.GENERIC_ERROR
                        }) continue
                }
            }

        }
    }

}