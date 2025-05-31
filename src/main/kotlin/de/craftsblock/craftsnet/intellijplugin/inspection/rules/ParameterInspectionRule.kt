package de.craftsblock.craftsnet.intellijplugin.inspection.rules

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElementFactory
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param.AddParameterAtIndexQuickFix
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param.AddParameterQuickFix
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param.ChangeParameterAtIndexQuickFix
import de.craftsblock.craftsnet.intellijplugin.inspection.fixes.param.ChangeParameterQuickFix
import de.craftsblock.craftsnet.intellijplugin.uitls.Utils

class ParameterInspectionRule(
    private val index: Int,
    private val defaultName: String,
    private vararg val expectedTypes: String?,
    private var errorDescription: String? = null
) : CustomInspectionRule() {

    private var expectedTypesRef: List<PsiType>? = null

    private var addParamFix: AddParameterQuickFix? = null
    private var changeParamFix: ChangeParameterQuickFix? = null

    override fun adopt(parent: CustomInspection) {
        addParamFix = AddParameterAtIndexQuickFix(index, defaultName, expectedTypes.filterNotNull().first())
        changeParamFix = ChangeParameterAtIndexQuickFix(index, expectedTypes.filterNotNull().first())

        super.adopt(parent)

        if (errorDescription != null) return
        errorDescription = "@${getAnnotationName()} methods require argument ${index + 1} to be one of: " +
                expectedTypes.filterNotNull().joinToString(", ") { Utils.stripClass(it) }
    }

    override fun checkMethod(holder: ProblemsHolder, method: PsiMethod) {
        if (expectedTypes.filterNotNull().isEmpty()) return

        val project: Project = method.project
        if (expectedTypesRef == null) {
            val factory: PsiElementFactory = JavaPsiFacade.getElementFactory(project)
            expectedTypesRef = expectedTypes.filterNotNull().map { factory.createTypeFromText(it, null) }
        }

        val parameters = method.parameterList.parameters
        if (parameters.size <= index) {
            registerProblem(holder, method.nameIdentifier ?: method, errorDescription!!, addParamFix)
            return
        }

        val param = parameters[index]
        val type = param.type

        for (typeRef in expectedTypesRef!!)
            if (typeRef.isAssignableFrom(type)) return

        registerProblem(holder, param, errorDescription!!, changeParamFix, addParamFix)
    }

}