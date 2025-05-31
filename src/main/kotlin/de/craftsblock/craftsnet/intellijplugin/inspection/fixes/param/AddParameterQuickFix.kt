package de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param

import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.*

abstract class AddParameterQuickFix(
    override val paramName: String,
    override val paramType: String
) : ParamQuickFix(paramName, paramType) {

    override fun getFamilyName(): String = "Add $paramName as argument"

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        if (descriptor.psiElement is PsiParameter) {
            val param: PsiParameter = descriptor.psiElement as PsiParameter
            val method: PsiMethod = param.parent.parent as? PsiMethod ?: return
            createParam(project, method)
            return
        }

        val method: PsiMethod = descriptor.psiElement.parent as? PsiMethod ?: return
        createParam(project, method)
    }

    protected abstract fun postionParam(paramList: PsiParameterList, param: PsiParameter)

    private fun createParam(project: Project, method: PsiMethod) {
        val factory: PsiElementFactory = JavaPsiFacade.getElementFactory(project)
        val type: PsiType = PsiType.getTypeByName(paramType, project, method.resolveScope)
        val param: PsiParameter = factory.createParameter(paramName, type)

        import(factory, method.parent as PsiClass, type)
        postionParam(method.parameterList, param)
    }

}