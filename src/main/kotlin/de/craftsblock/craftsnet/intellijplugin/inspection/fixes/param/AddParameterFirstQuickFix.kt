package de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param

import com.intellij.psi.PsiParameter
import com.intellij.psi.PsiParameterList

class AddParameterFirstQuickFix(
    override val paramName: String,
    paramType: String
) : AddParameterQuickFix(paramName, paramType) {

    override fun getFamilyName(): String = "Add $paramName as first parameter"

    override fun postionParam(paramList: PsiParameterList, param: PsiParameter) {
        paramList.addBefore(param, paramList.parameters.firstOrNull())
    }

}