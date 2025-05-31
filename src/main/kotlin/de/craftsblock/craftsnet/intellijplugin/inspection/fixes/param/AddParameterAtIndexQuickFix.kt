package de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param

import com.intellij.psi.PsiParameter
import com.intellij.psi.PsiParameterList

class AddParameterAtIndexQuickFix(
    private val index: Int,
    override val paramName: String,
    paramType: String
) : AddParameterQuickFix(paramName, paramType) {

    override fun getFamilyName(): String = "Add $paramName as the ${index + 1} argument"

    override fun postionParam(paramList: PsiParameterList, param: PsiParameter) {
        val parameters = paramList.parameters
        if (parameters.isEmpty() || index >= parameters.size) {
            paramList.add(param)
            return
        }

        if ((index - 1) < 0) {
            paramList.addBefore(param, parameters.firstOrNull())
            return
        }

        paramList.addAfter(param, parameters.getOrNull(index - 1))
    }

}