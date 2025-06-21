package de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomAnnotatedInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.RemoveElementQuickFix
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.ParameterInspectionRule
import de.craftsblock.craftsnet.intellijplugin.uitls.Utils

class DynamicUrlParamInspectionRule(
    private val argOffset: Int = 1
) : CustomInspectionRule() {

    override fun checkMethod(holder: ProblemsHolder, method: PsiMethod) {
        val parent = super.parent as? CustomAnnotatedInspection ?: return

        val rootClass = method.parent as? PsiClass ?: return
        val pathPattern = Utils.getMergedStringValueOfAnnotation(parent.annotation, "value", rootClass, method)
        val dynamicGroups: List<String> = Utils.getDynamicUrlParams(pathPattern)

        if (method.parameterList.parametersCount == dynamicGroups.size + argOffset) return

        if (method.parameterList.parametersCount < dynamicGroups.size + argOffset) handleToFew(holder, method, dynamicGroups)
        else handleToMany(holder, method, dynamicGroups)
    }

    private fun handleToFew(holder: ProblemsHolder, method: PsiMethod, dynamicGroups: List<String>) {
        for ((i, paramName) in dynamicGroups.withIndex()) {
            val index = i + argOffset
            if (index < method.parameterList.parametersCount) continue

            val rule = ParameterInspectionRule(index, paramName, "java.lang.String")
            rule.adopt(parent!!)
            rule.checkMethod(holder, method)
        }
    }

    private fun handleToMany(holder: ProblemsHolder, method: PsiMethod, dynamicGroups: List<String>) {
        for ((i, param) in method.parameterList.parameters.withIndex()) {
            val index = i - argOffset
            if (index < 0 || index < dynamicGroups.size) continue

            registerProblem(
                holder, param, "Found expected dynamic url parameter ${index + argOffset} while only having ${dynamicGroups.size}",
                RemoveElementQuickFix()
            )
        }
    }

}