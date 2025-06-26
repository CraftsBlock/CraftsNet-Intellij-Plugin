package de.craftsblock.craftsnet.intellijplugin.inspection

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiMethod
import de.craftsblock.craftsnet.intellijplugin.uitls.Updatable
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.CraftsNetVersionUtils

abstract class CustomInspection(
    private val name: String,
    vararg val rules: CustomInspectionRule,
    skipAdopt: Boolean = false
) : AbstractBaseJavaLocalInspectionTool(), Updatable {

    init {
        if (!skipAdopt) rules.forEach { it.adopt(this) }
    }

    override fun update() {
        rules.forEach { it.update() }
        super.update()
    }

    override fun getDisplayName(): String = "$name inspection"

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : JavaElementVisitor() {

            override fun visitMethod(method: PsiMethod) {
                if (!CraftsNetVersionUtils.isAvailable(method.project)) return

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

    open fun dynamicRules(method: PsiMethod): Set<CustomInspectionRule> = setOf()

}