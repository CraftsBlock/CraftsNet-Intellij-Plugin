package de.craftsblock.craftsnet.intellijplugin.inspection.rules.grouping

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiMethod
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule

class GroupedInspectionRule(
    private vararg val rules: CustomInspectionRule
) : CustomInspectionRule() {

    override fun adopt(parent: CustomInspection) {
        super.adopt(parent)
        rules.forEach { it.adopt(parent) }
    }

    override fun checkMethod(holder: ProblemsHolder, method: PsiMethod) {
        rules.forEach { it.checkMethod(holder, method) }
    }

}