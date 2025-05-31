package de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param

import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.*

abstract class ChangeParameterQuickFix(
    override val paramType: String
) : ParamQuickFix("null", paramType) {

    override fun getFamilyName(): String = "Change ${index() + 1} argument type to $paramType"

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        if (descriptor.psiElement is PsiParameter) {
            val param: PsiParameter = descriptor.psiElement as PsiParameter
            setTypeOfParam(project, param.parent.parent.parent as PsiClass, param)
            return
        }

        val method: PsiMethod = descriptor.psiElement.parent as? PsiMethod ?: return
        val param = method.parameterList.getParameter(index()) ?: return
        setTypeOfParam(project, method.parent as PsiClass, param)
    }

    private fun setTypeOfParam(project: Project, psiClass: PsiClass, param: PsiParameter?) {
        val factory: PsiElementFactory = JavaPsiFacade.getElementFactory(project)
        val newType = factory.createTypeElementFromText(paramType, param)

        import(factory, psiClass, newType)
        param?.typeElement?.replace(newType)
    }

    abstract fun index(): Int

}